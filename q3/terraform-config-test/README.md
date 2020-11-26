# Terraform

## Prerequisite
- Terraform 0.12 or above

## Steps of applying the Terraform
1. Export the AWS access key and secret key of the AWS account where you want to apply the Terraform to.
2. Update the following variables in `terraform.tfvars` to fit your requirements before running the Terraform.
-- hosted_zone: The AWS Route53 hosted zone
-- fqdn: The full URL used to making request to the service
3. Run command `terraform apply`

## Additional/Optional Steps 
Now the Terraform state is stored locally. You can add the following codes in `provider.tf` to upload the Terraform state to S3 bucket.
```
terraform {
  backend "s3" {
    bucket         = "test-tfstate"
    key            = "test.tfstate"
    region         = "ap-southeast-1"
    encrypt        = true
    dynamodb_table = "test-tfstate"
  }
}

resource "aws_s3_bucket" "terraform_state" {
  bucket = "test-tfstate"
  region = var.region

  versioning {
    enabled = true
  }

  lifecycle {
    prevent_destroy = true
  }
}

resource "aws_dynamodb_table" "terraform_state_lock" {
  name           = "test-tfstate"
  read_capacity  = 1
  write_capacity = 1
  hash_key       = "LockID"

  attribute {
    name = "LockID"
    type = "S"
  }
}
```

## Inputs

| Name | Description | Type | Default | Required |
|------|-------------|------|---------|:--------:|
| fqdn | The full URL of the service | `any` | n/a | yes |
| hosted\_zone | The Route53 hosted zone for the service | `any` | n/a | yes |
| rds\_allocated\_storage | Allocated storage to RDS instance | `any` | n/a | yes |
| rds\_backup\_retention\_period | RDS backup retention period | `any` | n/a | yes |
| rds\_backup\_window | RDS backup window | `any` | n/a | yes |
| rds\_ca\_cert\_identifier | RDS CA cert identifier | `string` | `"rds-ca-2019"` | no |
| rds\_engine | RDS engine | `any` | n/a | yes |
| rds\_engine\_version | RDS engine version | `any` | n/a | yes |
| rds\_final\_snapshot\_identifier | RDS final snapshot identifier | `string` | `""` | no |
| rds\_identifier | RDS instance identifier | `any` | n/a | yes |
| rds\_instance\_class | RDS instance class | `any` | n/a | yes |
| rds\_name | Database name created once the instance created | `any` | n/a | yes |
| rds\_recovery\_window\_in\_days | RDS recovery window in days | `number` | `0` | no |
| rds\_skip\_final\_snapshot | rds\_skip\_final\_snapshot | `bool` | `false` | no |
| rds\_storage\_type | RDS storage type | `string` | `"gp2"` | no |
| rds\_username | RDS username | `any` | n/a | yes |
| region | AWS Region | `any` | n/a | yes |