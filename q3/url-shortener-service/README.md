# Required Application

## Prerequisite
- Docker
- OpenJDK 11
- MySQL
 
## How to run the application locally with Docker
Run script `local-entrypoint.sh` in the root directory to build and start the application locally.

## Environment Variables:
- DB_HOST: The host of MySQL. Default: 127.0.0.1
- DB_PORT: The port of MySQL. Default: 3306
- SHORTEN_URL_DB: The database name in MySQL for storing the table and data. Default: shorten_url
- DB_USERNAME: The username to login DB. Default: root
- DB_PASSWORD: The password to login DB. Default: 123456
- SERVICE_FQDN: The FQDN to access this service. Default: http://localhost:8080