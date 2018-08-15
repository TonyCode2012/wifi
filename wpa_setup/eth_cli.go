package main

import (
	"context"
	"fmt"
	"log"
	"math/big"
	"time"
	"flag"
	"io/ioutil"	
/*
	"go-ethereum-master/accounts/keystore"
	"go-ethereum-master/common"
	"go-ethereum-master/core/types"
	"go-ethereum-master/ethclient"
	*/
/*	"github.com/ethereum/go-ethereum/accounts/abi"
	"github.com/ethereum/go-ethereum/accounts/abi/bind"
*/
	"github.com/ethereum/go-ethereum/accounts/keystore"
	"github.com/ethereum/go-ethereum/common"
	"github.com/ethereum/go-ethereum/core/types"
	"github.com/ethereum/go-ethereum/ethclient"
	)


type rpc_param_s struct {
	rpc string
	key string
	nonce uint64 
	to string
	amount int64
	gasLimit uint64
	gasPrice int64
	data string
	password string
	ret string
}

var rpc_param rpc_param_s

func help() {
//	fmt.Println("usage: ./proxy -rpc http://120.27.237.152:8802 -key config/keystore -to 0x93f97961eb166e2d96972ca192a20fb29138e200 -amount 9999 -limit 100000 -price 2 -password yudayanx");
}

func parse_params() error {	 
	flag.StringVar(&rpc_param.rpc, "rpc", "", "rpc url")
	flag.StringVar(&rpc_param.key, "key", "", "key store path")
	flag.StringVar(&rpc_param.to, "to", "", "to address")	 
	flag.Int64Var(&rpc_param.amount, "amount", 0, "amount")
	flag.Uint64Var(&rpc_param.gasLimit, "limit", 0, "gas limit")
	flag.Int64Var(&rpc_param.gasPrice, "price", 0, "gas price")
	flag.StringVar(&rpc_param.data, "data", "", "data")	 
	flag.StringVar(&rpc_param.password, "password", "", "keystore password")	
	flag.StringVar(&rpc_param.ret, "ret", "", "ret file")	
	
	flag.Parse()
	fmt.Println(rpc_param.rpc)
	fmt.Println(rpc_param.key)
	fmt.Println(rpc_param.to)
	fmt.Println(rpc_param.amount)
	fmt.Println(rpc_param.gasLimit)
	fmt.Println(rpc_param.gasPrice)	 
	fmt.Println(rpc_param.password)	
	fmt.Println(rpc_param.ret)		
	fmt.Println(rpc_param.data)		
	
	return nil
}

//Main
func main() {
	var ret string
	
	parse_params()
	
	// Create an RPC connection
	client, err := ethclient.Dial(rpc_param.rpc)
	if err != nil {
		log.Fatalf("Failed to connect to the Ethereum client: %v", err)
	}

	ret = rawTransaction(client)
	
	if err := ioutil.WriteFile(rpc_param.ret, []byte(ret), 0644); err != nil {
		fmt.Println("Error writing new keyfile to disk: %v", err)
	}
}

func rawTransaction(client *ethclient.Client) string {
	d := time.Now().Add(4000 * time.Millisecond)
	ctx, cancel := context.WithDeadline(context.Background(), d)
	defer cancel()

	keyjson, err := ioutil.ReadFile(rpc_param.key)
	if err != nil {
		fmt.Println(err)
		return "93"
	}
	unlockedKey, err := keystore.DecryptKey(keyjson, rpc_param.password)
	fmt.Println("this is unlockedKey address:")
	fmt.Println(unlockedKey)
	fmt.Println("above unlockedKey address!")

	nonce, _ := client.NonceAt(ctx, unlockedKey.Address, nil)
	fmt.Println("none = ", nonce)
	
	if err != nil {
		fmt.Println("Wrong passcode")
	} else {
		tx := types.NewTransaction(nonce, common.HexToAddress(rpc_param.to), big.NewInt(rpc_param.amount), rpc_param.gasLimit, big.NewInt(rpc_param.gasPrice), nil)
		signTx, err := types.SignTx(tx, types.HomesteadSigner{}, unlockedKey.PrivateKey)
		err = client.SendTransaction(ctx, signTx)
		if err != nil {
			fmt.Println(err, nonce)
		} else {
			select {
			case <-time.After(1 * time.Millisecond):
				fmt.Println("overslept")
				return "94"
			case <-ctx.Done():
				fmt.Println(ctx.Err())
				return "95"
			default:
				fmt.Println("hash = ", tx.Hash().String())
				return "92"
			}
		}
	}
	
	return "4"
}
