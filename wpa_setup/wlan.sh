#!/bin/bash

basedir=`dirname $0`
basedir=`cd $basedir;pwd`

retfile="$basedir/.retfile"
eth_cli_retfile="$basedir/.ecli"

VERS="20180705"

IFACE=""
REPLY=""

WPA_MAX_RETRY_COUNT=60
DHCP_MAX_RETRY_COUNT=60

CODE_REGISTER_SUCCESS=3

CODE_SUCCESS=64	# success
CODE_ASSOCAITE_FAILED=65 	# associate failed
CODE_HDCP_FAILED=66 	# dhcp failed
CODE_INTERNET_FAILED=67 	# Internet can't be accessed
CODE_NO_WLAN_IF=68	# no wlan interface
CODE_WPA_TIMEOUT=69	# wpa timeout
CODE_NO_SUITABLE_SSID=70 	# no suitable ssid
CODE_NOT_ROOT=71	# root is required
CODE_PARAM_ERROR=72	# wrong parameters
CODE_BSSID_ERROR=73	# bssid error
CODE_WAP_INCOMPLETED=74	# WAP incompleted
CODE_GET_SUCCESS=75	# get successfully

CODE_SEND_RAW_SUCCESS=92	# send raw transaction successfully
CODE_KEYSTORE_ERROR=93	# read keystore error
CODE_SEND_RAW_TIMEOUT=94	# send raw transaction timeout
CODE_SEND_RAW_OTHER_ERROR=95	# send raw transaction other errors

PRIKEY=#"/home/ben/bc/wpa_setup/config/prikey.pem"  
IDFILE=#"/home/ben/bc/wpa_setup/config/profile"
CFGFILE=#"config/wpa.conf"
LOGFILE=#"/home/ben/wpa.log"

function selectInterface {
	COUNTER=0
	IFACE=
	
	while read -r line ; do
		DEVS[$COUNTER]=$line
		COUNTER=$((COUNTER+1))
	done < <(iw dev | grep -E "Interface " | sed "s/	Interface //")


	if [[ ${#DEVS[@]} == 0 ]]; then
		echo -e "no wireless interface"		
	fi

	if [[ ${#DEVS[@]} == 1 ]]; then
		IFACE=${DEVS[0]}
		echo -e "find " $IFACE
	fi

	if [[ ${#DEVS[@]} -gt 1 ]]; then
		COUNTER=0
		echo "more than one wireless interface, use the first one"
		for i in "${DEVS[@]}";
		do
			COUNTER=$((COUNTER+1))
		done
#		read -p "select one wireless interface" INTNUM	
#		IFACE=${DEVS[$((INTNUM-1))]}	
		IFACE=${DEVS[0]}
	fi

}

ip=
broadcast=
mask=
dns=
internet_access=

function get_ip_info {
    default_route=$(ip route show)
    default_interface=$(echo $default_route | sed -e 's/^.*dev \([^ ]*\).*$/\1/' | head -n 1)
    address=$(ip addr show label $1 scope global | awk '$1 == "inet" { print $2,$4}')

    #ip address
    ip=$(echo $address | awk '{print $1 }')
    ip=${ip%%/*}
      
    #broadcast
    broadcast=$(echo $address | awk '{print $2 }')
      
    #mask address
    mask=$(route -n |grep 'U[ \t]' | head -n 1 | awk '{print $3}')
      
    #gateway address
    gateway=$(route -n | grep 'UG[ \t]' | awk '{print $2}')
      
    #dns
    dns=$(cat /etc/resolv.conf | grep -Ev "^$|^[#;]" | grep nameserver | awk '{print $2}')
	echo ip:$ip,mask:$mask,broadcast:$broadcast,gateway:$gateway,dns:$dns	
}

function kill_process {
	NAME=$1  
	ID=`ps -ef | grep "$NAME" | grep -v "grep" | awk '{print $2}'`  
	for id in $ID  
	do  
		kill -9 $id  
		echo "killed $NAME $id"  
	done  

}

function test_internet_access()   
{  
    timeout=10 
    maxtime=10
    
    target=www.baidu.com  
   
    # get response code
    ret_code=`curl --interface $1 -I -s --connect-timeout $timeout -m $maxtime $target -w %{http_code} | tail -n1`  

    if [ "x$ret_code" = "x200" ]; then  
        internet_access=1 
    else  
		internet_access=0
        echo "http return code " $ret_code
    fi  
	
	return $internet_access
}

function test_access {
	# test ip address information
	get_ip_info $1
	if [ -z "$ip" -o -z "$mask" -o -z "$dns" ]; then
		echo "aaaaaaaaaaaaaa"
		return $CODE_HDCP_FAILED
	fi

	# test accessing Internet 
	test_internet_access $1
	r=$?
	if [ $r -eq 0 ]; then
		return $CODE_INTERNET_FAILED
	fi
	

	return $CODE_SUCCESS;	
}

function terminate_supplicant {
	count=`ps -ef |grep wpa_supplicant |grep -v "grep" |wc -l`
	echo $count
	if [ 0 != $count ];then
		wpa_cli logoff
		wpa_cli terminate
	fi
}

function connect_wlan {
	# select wlan interface
	selectInterface
	if [[ x$IFACE == x ]]; then
		echo "INFO: has no wlan if"
		return $CODE_NO_WLAN_IF
	fi
	
	# bring up wlan interface
	ip link set ${IFACE} up

	terminate_supplicant
	sleep 1	
#	kill_process wpa_supplicant
#	$basedir/wpa_supplicant -Dnl80211 -i $IFACE -c $CFGFILE -B -f $LOGFILE -d
	$basedir/wpa_supplicant -i $IFACE -c $CFGFILE -B -f $LOGFILE -d
	$basedir/wpa_cli set bc_prikey $PRIKEY	
	$basedir/wpa_cli set bc_profile $IDFILE
	$basedir/wpa_cli enable_network 0
	for((i=1;i<WPA_MAX_RETRY_COUNT;i++))
#	for((i=WPA_MAX_RETRY_COUNT;i<WPA_MAX_RETRY_COUNT;i++))
	do
		wpastate=`$basedir/wpa_cli status | sed '/^wpa_state=/!d;s/.*=//'`
		echo $i $wpastate
		if [[ $wpastate == COMPLETED ]]; then
			echo "All authentication completed"
			break
		else
			index=`$basedir/wpa_cli get eap_info | sed '/^index=/!d;s/.*=//'`
			code=`$basedir/wpa_cli get eap_info | sed '/^code=/!d;s/.*=//'`
			if [ $index -ne 0 -a $code -ne 0 ]; then
				terminate_supplicant
				return $code
			fi
				
		fi	
		sleep 1
	done	
	
	if [ $i -eq $WPA_MAX_RETRY_COUNT ]; then
		echo "INFO: wpa timeout"
		return $CODE_WPA_TIMEOUT
	fi
	
	return $CODE_SUCCESS;
}

############################
# main
############################
echo "version=" $VERS $#
echo $0 $1 $2 $3 $4 $5 

PRIKEY=$2 
IDFILE=$3
CFGFILE=$4
LOGFILE=$5

if (( $EUID != 0 )); then 
   echo "This must be run as root. Try 'sudo $0 $1'." 
   exit $CODE_NOT_ROOT 
fi

if [[ $1 == "test" ]]; then
	# test network
	selectInterface
	test_access $IFACE
	r=$?
	echo "main test return code " $r
	exit $r
fi

if [[ $1 == "connect" ]]; then
	# check files
	if [ $# -lt 5 ]; then
		r=$CODE_PARAM_ERROR
		echo "main connect return code " $r
		exit $r
	fi
	
	if [ ! -f $PRIKEY -o  ! -f $IDFILE -o ! -f $CFGFILE -o -z "$LOGFILE" ];then
		r=$CODE_PARAM_ERROR
	else
		# remove log file
		if [ -f $LOGFILE ]; then
			rm $LOGFILE
		fi
		
		# connect wlan
		connect_wlan
		r=$?
		echo "connect_wlan return code" $r
		if [[ $r -eq $CODE_SUCCESS ]]; then
			# dhcp
			kill_process dhclient
			dhclient $IFACE
			# test network
			test_access $IFACE
			r=$?
		fi
	fi

	echo "main connect return code " $r
	exit $r
fi	

if [[ $1 == "disconnect" ]]; then
	# test network
	r=$CODE_SUCCESS
	terminate_supplicant
	echo "main disconnect return code " $r
	exit $r
fi

if [[ $1 == "close_account" ]]; then
	# test network
	r=$CODE_SUCCESS
	terminate_supplicant
	echo "main disconnect return code " $r
	exit $r
fi

if [[ $1 == "get" ]]; then
	if [[ $2 == "bssid" ]]; then
		wpastate=`$basedir/wpa_cli status | sed '/^wpa_state=/!d;s/.*=//'`
		echo $i $wpastate
		if [[ $wpastate == COMPLETED ]]; then
			bssid=`$basedir/wpa_cli status | sed '/^bssid=/!d;s/.*=//'`
			
			if [ "x$bssid" = "x" ]; then  
				exit $CODE_BSSID_ERROR
			else  
				echo "bssid $bssid"
				echo "{\"bssid\":\"$bssid\"}" > $retfile
				exit $CODE_GET_SUCCESS
			fi  
			
		else
			exit $CODE_WAP_INCOMPLETED
		fi	

	fi
	
	if [[ $2 == "price" ]]; then
			echo "default price 2"
			echo "{\"price\":2}" > $retfile
			exit $CODE_GET_SUCCESS
	fi	
fi

if [[ $1 == "sendrawtransaction" ]]; then
	$basedir/eth_cli -rpc $2 -key $3 -to $4 -amount $5 -limit $6 -price $7 -password $8 -ret $eth_cli_retfile
	ecli_ret=`cat $eth_cli_retfile` 
	echo $ecli_ret
	exit $ecli_ret
fi

