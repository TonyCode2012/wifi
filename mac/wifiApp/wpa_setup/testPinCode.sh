#!/bin/bash

basedir=`dirname $0`
basedir=`cd $basedir;pwd`

testLoginFile=$basedir/testLogin
testLeftCoinFile=$basedir/testLeftCoin

sleep 3

true > $testLoginFile
echo 10 > $testLeftCoinFile

echo "left coin 10"
echo "main connect return code 64"