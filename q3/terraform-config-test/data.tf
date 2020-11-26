data "aws_ami" "amazon_linux_2" {
  most_recent = true

  owners = ["amazon"]

  filter {
    name   = "owner-alias"
    values = ["amazon"]
  }


  filter {
    name   = "name"
    values = ["amzn2-ami-hvm*"]
  }
}

resource "aws_default_vpc" "default" {
  tags = {
    Name = "Default VPC"
  }
}

data "aws_subnet_ids" "default_subnet_ids" {
  vpc_id = aws_default_vpc.default.id
}

data "aws_route53_zone" "zone" {
  name         = var.hosted_zone
  private_zone = false
}
