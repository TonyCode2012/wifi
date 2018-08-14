config = require("./config")
Web3 = require("web3");
utils = require("./utils")


//reader.getBalanceToken("0x01c96e4d9be1f4aef473dc5dcf13d8bd1d4133cd")

getBalanceToken = function(account){
	var aBI = config.BalanceABI
	var addr = config.BalanceAddr
	var httpProvider = config.HttpProvider
	var web3 = new Web3(new Web3.providers.HttpProvider(httpProvider));
  	var abi = JSON.parse(aBI);
	var contractInstant = new web3.eth.Contract(abi, addr);
	web3.eth.call({
		to: addr,
		data: contractInstant.methods.balanceOf(account).encodeABI()
	}).then(balance => {
        balanceDec = utils.hexStrToInt(balance)
        console.log("Coin:" + balanceDec)
    })
}

getBalanceCoin = function(account){
	var aBI = config.CoinABI
	var addr = config.CoinAddr
	var httpProvider = config.HttpProvider
	var web3 = new Web3(new Web3.providers.HttpProvider(httpProvider));
  	var abi = JSON.parse(aBI);
	var contractInstant = new web3.eth.Contract(abi, addr);
	web3.eth.call({
		to: addr,
		data: contractInstant.methods.balanceOf(account).encodeABI()
	}).then(balance => {
        balanceDec = utils.hexStrToInt(balance)
        console.log("Coin:" +balanceDec)
        
    })
}

 
getBalanceCoin("0x01c96e4d9be1f4aef473dc5dcf13d8bd1d4133cd")

getBalanceToken("0x01c96e4d9be1f4aef473dc5dcf13d8bd1d4133cd")
