resource "aws_security_group" "db_allow_vpc_traffic" {
  name = "db-vpc-allow"
  description = "DB SG"

  ingress {
    from_port = 3306
    to_port = 3306
    protocol = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  egress {
    from_port = 0
    to_port = 65535
    protocol = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "test-db-vpc-allow"
  }
}

resource "aws_security_group" "url_shortener_ec2_sg" {
  name = "crypto-test-url-shortener"
  description = "URL Shortener EC2 SG"

  ingress {
    from_port = 80
    to_port = 80
    protocol = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  egress {
    from_port = 0
    to_port = 65535
    protocol = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "test-db-vpc-allow"
  }
}