#!/bin/bash
index=441
while read line; do
    scode=${line%% *}
    scontent=${line#*#}
    scontent=${scontent:1}
    t1="                       case $scode:\n"
    t2="                           System.out.println(\"[ERROR] Launch failed! $scontent\");\n"
    t3="                           break;"
    sed -i "$index i\ $t1 $t2 $t3" Register.java.bak
    index=$((index+3))
done < statusCode.txt
