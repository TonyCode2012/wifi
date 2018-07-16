#!/bin/bash

basedir=`dirname $0`
basedir=`cd $basedir;pwd`

statusCode=0

#sleep 6

testLoginFile=$basedir/testLogin
testLeftCoinFile=$basedir/testLeftCoin
leftCoin=0

# check if registration has been done
if [ -e $testLoginFile ]; then
    statusCode=64
    if [ ! -e $testLeftCoinFile ]; then
        echo "[ERROR] left coin file lost!"
    fi
    leftCoin=`cat $testLeftCoinFile`
    leftCoin=`echo "scale=2;$leftCoin*1-1" | bc`
    if [ "$leftCoin" = "0.00" ] || [[ "$leftCoin" =~ -.* ]]; then
       echo "[ERROR] your coin has been cost over!"
       echo "0" > $testLeftCoinFile
       statusCode=305
    else
       echo "left coin $leftCoin"
       echo $leftCoin > $testLeftCoinFile
    fi
else
    statusCode=9
fi

echo "main connect return code $statusCode"
