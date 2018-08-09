config = require("./config")
Web3 = require("web3");
sendRawTransaction = require("./sendRawTransaction")

/*
* account: caller's address
* pk: caller's private key string
* others: parameters of the contract
*/
exports.sendRawTransfer = function(account, pk, transferTo, amount){
	var aBI = config.BalanceABI
	var addr = config.BalanceAddr
	var httpProvider = config.HttpProvider
	var web3 = new Web3(new Web3.providers.HttpProvider(httpProvider));

	var privateKey = new Buffer(pk, 'hex')
	console.log("coinbase:")
	web3.eth.getCoinbase((err, coinbase) => {console.log(coinbase)})
  	var abi = JSON.parse(aBI);
  	var contractInstant = new web3.eth.Contract(abi, addr);
	var encodedData = contractInstant.methods.transfer(transferTo, amount).encodeABI()
    sendRawTransaction.sendTransction(web3, privateKey, account, addr, encodedData)
}

/*
* account: caller's address
* pk: caller's private key string
* others: parameters of the contract
*/
exports.sendRawDeduction = function(account, pk, apAddr){
	var aBI = config.ControllerABI_
	var addr = config.ControllerAddr_
	var httpProvider = config.HttpProvider
	var web3 = new Web3(new Web3.providers.HttpProvider(httpProvider));

	 var privateKey = new Buffer(pk, 'hex')
	console.log("coinbase:")
	web3.eth.getCoinbase((err, coinbase) => {console.log(coinbase)})
  	var abi = JSON.parse(aBI);
  	var contractInstant = new web3.eth.Contract(abi, addr);
	var encodedData = contractInstant.methods.deduction(apAddr).encodeABI()
    sendRawTransaction.sendTransction(web3, privateKey, account, addr, encodedData)
}

/*
* account: caller's address
* pk: caller's private key string
* others: parameters of the contract
*/
exports.sendRawCommission = function(account, pk, apAddr, advAddr){
	var aBI = config.ControllerABI_
	var addr = config.ControllerAddr_
	var httpProvider = config.HttpProvider
	var web3 = new Web3(new Web3.providers.HttpProvider(httpProvider));

	var privateKey = new Buffer(pk, 'hex')
	console.log("coinbase:")
	web3.eth.getCoinbase((err, coinbase) => {console.log(coinbase)})
  	var abi = JSON.parse(aBI);
  	var contractInstant = new web3.eth.Contract(abi, addr);
	var encodedData = contractInstant.methods.commission(apAddr, advAddr).encodeABI()
    sendRawTransaction.sendTransction(web3, privateKey, account, addr, encodedData)
}

/*
* account: caller's address
* pk: caller's private key string
* others: parameters of the contract
*/
exports.sendRawWatchAdv = function(account, pk, advAddr, apAddr){
	var aBI = config.ControllerABI_
	var addr = config.ControllerAddr_
	var httpProvider = config.HttpProvider
	var web3 = new Web3(new Web3.providers.HttpProvider(httpProvider));

	var privateKey = new Buffer(pk, 'hex')
	console.log("coinbase:")
	web3.eth.getCoinbase((err, coinbase) => {console.log(coinbase)})
  	var abi = JSON.parse(aBI);
  	var contractInstant = new web3.eth.Contract(abi, addr);
	var encodedData = contractInstant.methods.watchAdv(advAddr, apAddr).encodeABI()
    sendRawTransaction.sendTransction(web3, privateKey, account, addr, encodedData)
}


/*
* account: caller's address
* pk: caller's private key string
* others: parameters of the contract
*/
exports.sendRawAdvSetPrice = function(account, pk, advAddr, price){
	var aBI = config.ControllerABI_
	var addr = config.ControllerAddr_
	var httpProvider = config.HttpProvider
	var web3 = new Web3(new Web3.providers.HttpProvider(httpProvider));

	var privateKey = new Buffer(pk, 'hex')
	console.log("coinbase:")
	web3.eth.getCoinbase((err, coinbase) => {console.log(coinbase)})
  	var abi = JSON.parse(aBI);
  	var contractInstant = new web3.eth.Contract(abi, addr);
	var encodedData = contractInstant.methods.advSetPrice(advAddr, price).encodeABI()
    sendRawTransaction.sendTransction(web3, privateKey, account, addr, encodedData)
}

/*
* account: caller's address
* pk: caller's private key string
* others: parameters of the contract
*/
exports.sendRawApSetPrice = function(account, pk, apaddr, price){
	var aBI = config.ControllerABI_
	var addr = config.ControllerAddr_
	var httpProvider = config.HttpProvider
	var web3 = new Web3(new Web3.providers.HttpProvider(httpProvider));

	var privateKey = new Buffer(pk, 'hex')
	console.log("coinbase:")
	web3.eth.getCoinbase((err, coinbase) => {console.log(coinbase)})
  	var abi = JSON.parse(aBI);
  	var contractInstant = new web3.eth.Contract(abi, addr);
	var encodedData = contractInstant.methods.aPSetPrice(apaddr, price).encodeABI()
    sendRawTransaction.sendTransction(web3, privateKey, account, addr, encodedData)
}
