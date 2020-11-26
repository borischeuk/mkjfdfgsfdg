variable "region"      {}
variable "hosted_zone" {}
variable "fqdn"        {}

variable "rds_name"                      {}
variable "rds_identifier"                {}
variable "rds_allocated_storage"         {}
variable "rds_storage_type"              { default = "gp2" }
variable "rds_engine"                    {}
variable "rds_engine_version"            {}
variable "rds_instance_class"            {}
variable "rds_username"                  {}
variable "rds_backup_retention_period"   {}
variable "rds_backup_window"             {}
variable "rds_skip_final_snapshot"       { default = false }
variable "rds_final_snapshot_identifier" { default = "" }
variable "rds_recovery_window_in_days"   { default = 0 }
variable "rds_ca_cert_identifier"        { default = "rds-ca-2019" }