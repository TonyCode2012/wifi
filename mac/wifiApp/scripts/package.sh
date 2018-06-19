#!/bin/bash

basedir=`dirname $0`
basedir=`cd $basedir;pwd`
appDir=$basedir/..
packageDir=$basedir/wifiApp
packageName=$appDir/wifiApp-`cat $appDir/VERSION`.zip

mkdir -p $packageDir
cp -r $appDir/config $appDir/img $appDir/out $appDir/wpa_setup $packageDir

zip -r $packageName $packageDir
if [ $? -ne 0 ]; then
    echo "[ERROR] package wifiApp failed!" >&2
fi

rm -rf $packageDir