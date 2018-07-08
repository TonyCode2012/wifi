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
    if [ $leftCoin -eq 0 ]; then
       echo "[ERROR] your coin has been cost over!"
       statusCode=305
    else
       leftCoin=$((leftCoin-1))
       echo "left coin $leftCoin"
       echo $leftCoin > $testLeftCoinFile
    fi
else
    statusCode=9
fi

echo "main connect return code $statusCode"
