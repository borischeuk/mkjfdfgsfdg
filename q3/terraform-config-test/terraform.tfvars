region      = "ap-southeast-1"
hosted_zone = "borischeukstudy.com"
fqdn        = "crypto-test.borischeukstudy.com"

rds_name                    = "shorten_url"
rds_identifier              = "mysql-test"
rds_allocated_storage       = "10"
rds_engine                  = "mysql"
rds_engine_version          = "5.7.23"
rds_instance_class          = "db.t2.large"
rds_username                = "dbuser"
rds_backup_retention_period = 1
rds_backup_window           = "19:00-21:00"