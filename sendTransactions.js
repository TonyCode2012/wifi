config = require("./config")
Web3 = require("web3");
sendRawTransaction = require("./utils")

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
	var aBI = config.ControllerABI
	var addr = config.ControllerAddr
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
	var aBI = config.ControllerABI
	var addr = config.ControllerAddr
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
	var aBI = config.ControllerABI
	var addr = config.ControllerAddr
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
	var aBI = config.ControllerABI
	var addr = config.ControllerAddr
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
	var aBI = config.ControllerABI
	var addr = config.ControllerAddr
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

//-----------------------   V2, coin related    -------------------------------

//-----------------------   V2, coin related    -------------------------------


/*
* account: caller's address
* pk: caller's private key string
* others: parameters of the contract
*/
exports.sendRawApSetCoin = function(account, pk, apaddr, coin){
	var aBI = config.CoinControllerABI
	var addr = config.CoinControllerAddr
	var httpProvider = config.HttpProvider
	var web3 = new Web3(new Web3.providers.HttpProvider(httpProvider));

	var privateKey = new Buffer(pk, 'hex')
	console.log("coinbase:")
	web3.eth.getCoinbase((err, coinbase) => {console.log(coinbase)})
  	var abi = JSON.parse(aBI);
  	var contractInstant = new web3.eth.Contract(abi, addr);
	var encodedData = contractInstant.methods.aPSetCoin(apaddr, coin).encodeABI()
	console.log(encodedData)
    sendRawTransaction.sendTransction(web3, privateKey, account, addr, encodedData)
}


/*
* account: caller's address
* pk: caller's private key string
* others: parameters of the contract
*/
exports.sendRawApRegister = function(account, pk, apaddr, bssid){
	var aBI = config.ControllerABI
	var addr = config.ControllerAddr
	var httpProvider = config.HttpProvider
	var web3 = new Web3(new Web3.providers.HttpProvider(httpProvider));

	var privateKey = new Buffer(pk, 'hex')
	console.log("coinbase:")
	web3.eth.getCoinbase((err, coinbase) => {console.log(coinbase)})
  	var abi = JSON.parse(aBI);
  	var contractInstant = new web3.eth.Contract(abi, addr);
	var encodedData = contractInstant.methods.aPRegister(apaddr, bssid).encodeABI()
    sendRawTransaction.sendTransction(web3, privateKey, account, addr, encodedData)
}


/*
* account: caller's address
* pk: caller's private key string
* others: parameters of the contract
*/
exports.sendRawThingRegister = function(account, pk, thingaddr, price){
	var aBI = config.ControllerABI
	var addr = config.ControllerAddr
	var httpProvider = config.HttpProvider
	var web3 = new Web3(new Web3.providers.HttpProvider(httpProvider));

	var privateKey = new Buffer(pk, 'hex')
	console.log("coinbase:")
	web3.eth.getCoinbase((err, coinbase) => {console.log(coinbase)})
  	var abi = JSON.parse(aBI);
  	var contractInstant = new web3.eth.Contract(abi, addr);
	var encodedData = contractInstant.methods.thingRegister(thingaddr, price).encodeABI()
    sendRawTransaction.sendTransction(web3, privateKey, account, addr, encodedData)
}


/*
* account: caller's address
* pk: caller's private key string
* others: parameters of the contract
*/
exports.sendRawAdvSetCoin = function(account, pk, advaddr, coin){
	var aBI = config.CoinControllerABI
	var addr = config.CoinControllerAddr
	var httpProvider = config.HttpProvider
	var web3 = new Web3(new Web3.providers.HttpProvider(httpProvider));

	var privateKey = new Buffer(pk, 'hex')
	console.log("coinbase:")
	web3.eth.getCoinbase((err, coinbase) => {console.log(coinbase)})
  	var abi = JSON.parse(aBI);
  	var contractInstant = new web3.eth.Contract(abi, addr);
	var encodedData = contractInstant.methods.advSetCoin(advaddr, coin).encodeABI()
	console.log(encodedData)
    sendRawTransaction.sendTransction(web3, privateKey, account, addr, encodedData)
}

/*
* account: caller's address
* pk: caller's private key string
* others: parameters of the contract
*/
exports.sendRawThingSetCoin = function(account, pk, thingaddr, coin){
	var aBI = config.CoinControllerABI
	var addr = config.CoinControllerAddr
	var httpProvider = config.HttpProvider
	var web3 = new Web3(new Web3.providers.HttpProvider(httpProvider));

	var privateKey = new Buffer(pk, 'hex')
	console.log("coinbase:")
	web3.eth.getCoinbase((err, coinbase) => {console.log(coinbase)})
  	var abi = JSON.parse(aBI);
  	var contractInstant = new web3.eth.Contract(abi, addr);
	var encodedData = contractInstant.methods.thingSetCoin(thingaddr, coin).encodeABI()
	console.log(encodedData)
    sendRawTransaction.sendTransction(web3, privateKey, account, addr, encodedData)
}

/*
* account: caller's address
* pk: caller's private key string
* others: parameters of the contract
*/
exports.sendRawAdvRegister = function(account, pk, advaddr){
	var aBI = config.ControllerABI
	var addr = config.ControllerAddr
	var httpProvider = config.HttpProvider
	var web3 = new Web3(new Web3.providers.HttpProvider(httpProvider));

	var privateKey = new Buffer(pk, 'hex')
	console.log("coinbase:")
	web3.eth.getCoinbase((err, coinbase) => {console.log(coinbase)})
  	var abi = JSON.parse(aBI);
  	var contractInstant = new web3.eth.Contract(abi, addr);
	var encodedData = contractInstant.methods.advRegister(advaddr).encodeABI()
    sendRawTransaction.sendTransction(web3, privateKey, account, addr, encodedData)
}

/*
* account: caller's address
* pk: caller's private key string
* others: parameters of the contract
*/
exports.sendRawCoinTransfer = function(account, pk, transferTo, amount){
	var aBI = config.CoinABI
	var addr = config.CoinAddr
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
exports.sendRawCommissionCoin = function(account, pk, apAddr, advAddr){
	var aBI = config.CoinControllerABI
	var addr = config.CoinControllerAddr
	var httpProvider = config.HttpProvider
	var web3 = new Web3(new Web3.providers.HttpProvider(httpProvider));

	var privateKey = new Buffer(pk, 'hex')
	console.log("coinbase:")
	web3.eth.getCoinbase((err, coinbase) => {console.log(coinbase)})
  	var abi = JSON.parse(aBI);
  	var contractInstant = new web3.eth.Contract(abi, addr);
	var encodedData = contractInstant.methods.commissionCoin(apAddr, advAddr).encodeABI()
    sendRawTransaction.sendTransction(web3, privateKey, account, addr, encodedData)
}

/*
* account: caller's address
* pk: caller's private key string
* others: parameters of the contract
*/
exports.sendRawWatchAdvCoin = function(account, pk, advAddr, apAddr){
	var aBI = config.CoinControllerABI
	var addr = config.CoinControllerAddr
	var httpProvider = config.HttpProvider
	var web3 = new Web3(new Web3.providers.HttpProvider(httpProvider));

	var privateKey = new Buffer(pk, 'hex')
	console.log("coinbase:")
	web3.eth.getCoinbase((err, coinbase) => {console.log(coinbase)})
  	var abi = JSON.parse(aBI);
  	var contractInstant = new web3.eth.Contract(abi, addr);
	var encodedData = contractInstant.methods.watchAdvCoin(advAddr, apAddr).encodeABI()
    sendRawTransaction.sendTransction(web3, privateKey, account, addr, encodedData)
}


/*
* account: caller's address
* pk: caller's private key string
* others: parameters of the contract
*/
exports.sendRawBuyThingCoin = function(account, pk, thingaddr){
	var aBI = config.CoinControllerABI
	var addr = config.CoinControllerAddr
	var httpProvider = config.HttpProvider
	var web3 = new Web3(new Web3.providers.HttpProvider(httpProvider));

	var privateKey = new Buffer(pk, 'hex')
	console.log("coinbase:")
	web3.eth.getCoinbase((err, coinbase) => {console.log(coinbase)})
  	var abi = JSON.parse(aBI);
  	var contractInstant = new web3.eth.Contract(abi, addr);
	var encodedData = contractInstant.methods.buyThingCoin(thingaddr).encodeABI()
    sendRawTransaction.sendTransction(web3, privateKey, account, addr, encodedData)
}

/*
* account: caller's address
* pk: caller's private key string
* others: parameters of the contract
*/
exports.sendRawBuyThing = function(account, pk, thingaddr){
	var aBI = config.ControllerABI
	var addr = config.ControllerAddr
	var httpProvider = config.HttpProvider
	var web3 = new Web3(new Web3.providers.HttpProvider(httpProvider));

	var privateKey = new Buffer(pk, 'hex')
	console.log("coinbase:")
	web3.eth.getCoinbase((err, coinbase) => {console.log(coinbase)})
  	var abi = JSON.parse(aBI);
  	var contractInstant = new web3.eth.Contract(abi, addr);
	var encodedData = contractInstant.methods.buyThing(thingaddr).encodeABI()
    sendRawTransaction.sendTransction(web3, privateKey, account, addr, encodedData)
}