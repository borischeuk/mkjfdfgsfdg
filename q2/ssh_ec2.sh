#!/bin/bash

if [ -z $1 ]; then
    echo "Usage: ssh_ec2.sh <ec2_instance_name>"
    exit 1
fi

ec2_name=$1

public_ip=`aws ec2 describe-instances --filters Name=tag:Name,Values=${ec2_name} | jq -r .Reservations[0].Instances[0].PublicIpAddress`

if [ ${public_ip} = null ]
then
    echo "Host not found"
else
    ssh ec2-user@${public_ip}
fi