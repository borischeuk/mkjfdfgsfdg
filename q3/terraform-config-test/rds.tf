resource "random_string" "db_password" {
  length = 24
  special = false
}

resource "aws_db_instance" "instance" {
  name                      = var.rds_name
  identifier                = var.rds_identifier
  allocated_storage         = var.rds_allocated_storage
  storage_type              = var.rds_storage_type
  storage_encrypted         = true
  engine                    = var.rds_engine
  engine_version            = var.rds_engine_version
  instance_class            = var.rds_instance_class
  username                  = var.rds_username
  password                  = random_string.db_password.result
  backup_retention_period   = var.rds_backup_retention_period
  backup_window             = var.rds_backup_window
  skip_final_snapshot       = var.rds_skip_final_snapshot
  vpc_security_group_ids    = [aws_security_group.db_allow_vpc_traffic.id]
  ca_cert_identifier        = var.rds_ca_cert_identifier
  final_snapshot_identifier = var.rds_final_snapshot_identifier != "" ? var.rds_final_snapshot_identifier : var.rds_identifier
}

resource "aws_secretsmanager_secret" "db_info" {
  name = "${var.rds_identifier}-info"
  recovery_window_in_days = var.rds_recovery_window_in_days
}

resource "aws_secretsmanager_secret_version" "db_info" {
  secret_id     = aws_secretsmanager_secret.db_info.id
  secret_string = jsonencode({"host"=aws_db_instance.instance.address,"username"=var.rds_username,"password"=random_string.db_password.result,"port"=tostring(aws_db_instance.instance.port)})
}