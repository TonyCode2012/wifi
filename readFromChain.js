config = require("./config")
Web3 = require("web3");
sendRawTransaction = require("./utils")

/*
* account: caller's address
* pk: caller's private key string
* others: parameters of the contract
*/
exports.getBalanceToken = function(account){
	var aBI = config.BalanceABI
	var addr = config.BalanceAddr
	var httpProvider = config.HttpProvider
	var web3 = new Web3(new Web3.providers.HttpProvider(httpProvider));
  	var abi = JSON.parse(aBI);
	var contractInstant = new web3.eth.Contract(abi, addr);
	web3.eth.call({
		to: addr,
		data: contractInstant.methods.balanceOf(account).encodeABI()
	}).then(balance => {console.log(balance)})
}

exports.getBalanceCoin = function(account){
	var aBI = config.CoinABI
	var addr = config.CoinAddr
	var httpProvider = config.HttpProvider
	var web3 = new Web3(new Web3.providers.HttpProvider(httpProvider));
  	var abi = JSON.parse(aBI);
	var contractInstant = new web3.eth.Contract(abi, addr);
	web3.eth.call({
		to: addr,
		data: contractInstant.methods.balanceOf(account).encodeABI()
	}).then(balance => {console.log(balance)})
}
