awk '$4>="[10/Jun/2019:00:00:00" && $4<="[19/Jun/2019:23:59:59" { print $1 }' access.log | sort | uniq -c | sort -n -r | head -n 10