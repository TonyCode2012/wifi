#!/bin/bash

basedir=`dirname $0`
basedir=`cd $basedir;pwd`

#sleep 6

testLoginFile=$basedir/testLogin

sleep 3
rm $testLoginFile
echo "main connect return code 15"