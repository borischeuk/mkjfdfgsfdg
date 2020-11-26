locals {
  userdata = <<USERDATA
#!/bin/bash
sudo amazon-linux-extras install docker
sudo service docker start
sudo docker run -p 80:8080 -e DB_HOST=${aws_db_instance.instance.address} -e DB_PORT=3306 -e DB_USERNAME=${var.rds_username} -e DB_PASSWORD=${random_string.db_password.result} -e SERVICE_FQDN="http://${var.fqdn}" -d borischeuk/url-shortener-service

USERDATA
}

resource "aws_key_pair" "generated_key" {
  key_name   = "crypto-test-key"
  public_key = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQDYuwWIti2Rd7wImmvuiglnohTTnsAQ0HXx9uxfDmtLcyrJJxqlJjDGKfz4X14iApCkEkPgo/XXWEYpAZHZasrmgGPRzyza6zxjixqvHN7HPnJjOS0MZmRl21gvA1jKSE9Vjm20dSr1UHh/5bn4zgbNzsoMjlwByaxEbuguFcXo6rpArJqqu6Vk3CkG3cRDRrqjOnhjNbnHUov+TeenILTcyJGsvRip60J2+tcTSIcU7vw+14OooVEElhV/2HaBOrRdUEz9mCFswNRwB0JdsGjnyqg0Az0LKdAzgTz2waZ/VOTVDJaP0Qz63q7mOZXhlnlXwXSJ2zG3h/0kL3wJ51Cl"
}

resource "aws_launch_configuration" "url_shortener" {

  image_id                    = data.aws_ami.amazon_linux_2.id
  key_name                    = aws_key_pair.generated_key.key_name
  name_prefix                 = "crypto-test"
  instance_type               = "t2.medium"
  security_groups             = [ aws_security_group.url_shortener_ec2_sg.id ]
  user_data_base64            = base64encode(local.userdata)
  associate_public_ip_address = false

  lifecycle {
    create_before_destroy = true
  }
}

resource "aws_autoscaling_group" "url_shortener" {
  name = "crypto-test"
  max_size = "5"
  min_size = "3"
  vpc_zone_identifier = data.aws_subnet_ids.default_subnet_ids.ids
  launch_configuration = aws_launch_configuration.url_shortener.name
  target_group_arns = [aws_lb_target_group.url_shortener.arn]

  tag {
    key                 = "Name"
    value               = "url_shortener"
    propagate_at_launch = true
  }
}

resource "aws_autoscaling_policy" "url_shortener" {
  name                   = "crypto-test"
  autoscaling_group_name = aws_autoscaling_group.url_shortener.name

  policy_type = "TargetTrackingScaling"

  target_tracking_configuration {
    predefined_metric_specification {
      predefined_metric_type = "ALBRequestCountPerTarget"
      resource_label = "${aws_lb.url_shortener.arn_suffix}/${aws_lb_target_group.url_shortener.arn_suffix}"
    }

    target_value = 300.0
  }
}

resource "aws_lb" "url_shortener" {
  name               = "crypto-test"
  internal           = false
  load_balancer_type = "application"
  security_groups    = [aws_security_group.url_shortener_ec2_sg.id]
  subnets            = data.aws_subnet_ids.default_subnet_ids.ids

  enable_deletion_protection = true


  tags = {
    Name = "url_shortener"
  }
}

resource "aws_lb_target_group" "url_shortener" {
  name     = "crypto-test"
  port     = 80
  protocol = "HTTP"
  vpc_id   = aws_default_vpc.default.id
}

resource "aws_lb_listener" "url_shortener" {
  load_balancer_arn = aws_lb.url_shortener.arn
  port              = "80"
  protocol          = "HTTP"

  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.url_shortener.arn
  }
}

resource "aws_route53_record" "url_shortener" {
  zone_id        = data.aws_route53_zone.zone.zone_id
  ttl            = 300
  name           = "${var.fqdn}"
  type           = "CNAME"
  records        = [aws_lb.url_shortener.dns_name]
}