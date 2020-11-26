# Q1

## Prerequisite
Please install `geoiplookup` command in linux machine.
For Debian, it can be installed with command `yum install GeoIP GeoIP-data`

## How to use the script
Please place the `access.log` file in the same directory with the scripts.
`count_http.sh` is for counting the total number of HTTP requests in the log file.
`top_10_host.sh` is for searching for the top 10 IP with the most requests.
`find_country.sh` is for searching for the country with the most requests.