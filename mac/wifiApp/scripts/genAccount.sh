#!/bin/bash

companyId=$1
phone=$2

companySHA256=`echo $companyId | sha256sum | awk '{print $1}'`
phoneMD5=`echo $phone | md5sum | awk '{print $1}'`

account="0x"${companySHA256:1:8}${phoneMD5}

echo $account
