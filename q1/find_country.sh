#!/bin/bash

count=1
total=`awk '{print $1 ; }' access.log | uniq | wc -l`

IFS=$'\n'
for ip in $(awk '{print $1 ; }' access.log | uniq); do
    echo "progress: $count/$total"
    # country=`geoiplookup $ip | grep Country | awk '{print $NF}'`
    country=`geoiplookup $ip | grep Country | awk '{print $4}' | sed 's/,//g'`
    arr+=("$country")
    count=$((count+1))
done

sort <<<"${arr[*]}" | uniq -c | sort -n -r | head -n 1

unset IFS


