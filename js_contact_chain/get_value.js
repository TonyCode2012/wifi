config = require("./config")
Web3 = require("web3");
utils = require("./utils")


//reader.getBalanceToken("0x01c96e4d9be1f4aef473dc5dcf13d8bd1d4133cd")

getBalanceToken = function(account){
	var aBI = config.BalanceABI
	var addr = config.BalanceAddr
	console.log(addr)
	var httpProvider = config.HttpProvider
	var web3 = new Web3(new Web3.providers.HttpProvider(httpProvider))

  	var abi = JSON.parse(aBI);
	var contractInstant = new web3.eth.Contract(abi, addr);
	web3.eth.call({
		to: addr,
		data: contractInstant.methods.balanceOf(account).encodeABI()
	}).then(balance => {
        balanceDec = utils.hexStrToInt(balance)
        console.log("Token:" + balanceDec)
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


 
getApPriceToken = function(apAccount){
	var aBI = config.ApABI
	var addr = config.APAddr
	var httpProvider = config.HttpProvider
	var web3 = new Web3(new Web3.providers.HttpProvider(httpProvider));
  	var abi = JSON.parse(aBI);
	var contractInstant = new web3.eth.Contract(abi, addr);
	web3.eth.call({
		to: addr,
		data: contractInstant.methods.getPrice(apAccount).encodeABI()
	}).then(balance => {
        balanceDec = utils.hexStrToInt(balance)
        console.log("AP Price Token:" + balanceDec)
    })
}

getApPriceCoin = function(apAccount){
	var aBI = config.ApABI
	var addr = config.APAddr
	var httpProvider = config.HttpProvider
	var web3 = new Web3(new Web3.providers.HttpProvider(httpProvider));
  	var abi = JSON.parse(aBI);
	var contractInstant = new web3.eth.Contract(abi, addr);
	web3.eth.call({
		to: addr,
		data: contractInstant.methods.getCoin(apAccount).encodeABI()
	}).then(balance => {
        balanceDec = utils.hexStrToInt(balance)
        console.log("AP Price Coin:" + balanceDec)
    })
}


getAdvPriceToken = function(advAccount){
	var aBI = config.AdvABI
	var addr = config.AdvAddr
	var httpProvider = config.HttpProvider
	var web3 = new Web3(new Web3.providers.HttpProvider(httpProvider));
  	var abi = JSON.parse(aBI);
	var contractInstant = new web3.eth.Contract(abi, addr);
	web3.eth.call({
		to: addr,
		data: contractInstant.methods.getPrice(advAccount).encodeABI()
	}).then(balance => {
        balanceDec = utils.hexStrToInt(balance)
        console.log("ADV Price Token:" + balanceDec)
    })
}


getAdvPriceCoin = function(advAccount){
	var aBI = config.AdvABI
	var addr = config.AdvAddr
	var httpProvider = config.HttpProvider
	var web3 = new Web3(new Web3.providers.HttpProvider(httpProvider));
  	var abi = JSON.parse(aBI);
	var contractInstant = new web3.eth.Contract(abi, addr);
	web3.eth.call({
		to: addr,
		data: contractInstant.methods.getCoin(advAccount).encodeABI()
	}).then(balance => {
        balanceDec = utils.hexStrToInt(balance)
        console.log("ADV Price Coin:" + balanceDec)
    })
}


getThingPriceToken = function(thingAccount){
	var aBI = config.ThingABI
	var addr = config.ThingAddr
	var httpProvider = config.HttpProvider
	var web3 = new Web3(new Web3.providers.HttpProvider(httpProvider));
  	var abi = JSON.parse(aBI);
	var contractInstant = new web3.eth.Contract(abi, addr);
	web3.eth.call({
		to: addr,
		data: contractInstant.methods.getPrice(thingAccount).encodeABI()
	}).then(balance => {
        balanceDec = utils.hexStrToInt(balance)
        console.log("Thing Token:" +balanceDec)
        
    })
}
getThingPriceCoin = function(thingAccount){
	var aBI = config.ThingABI
	var addr = config.ThingAddr
	var httpProvider = config.HttpProvider
	var web3 = new Web3(new Web3.providers.HttpProvider(httpProvider));
  	var abi = JSON.parse(aBI);
	var contractInstant = new web3.eth.Contract(abi, addr);
	web3.eth.call({
		to: addr,
		data: contractInstant.methods.getCoin(thingAccount).encodeABI()
	}).then(balance => {
        balanceDec = utils.hexStrToInt(balance)
        console.log("Thing Coin:" +balanceDec)
        
    })
}

getBalanceToken("0x01c96e4d9be1f4aef473dc5dcf13d8bd1d4133cd")

getBalanceCoin("0x8580c4a0823a54539f9e14a84d7c56a636d20228")

getApPriceToken("0xf439bf68fc695b4a62f9e3322c75229ba5a0ff33")

getApPriceCoin("0xf439bf68fc695b4a62f9e3322c75229ba5a0ff44")

getAdvPriceToken("0xf439bf68fc695b4a62f9e3322c75229ba5a0ffbb")

getAdvPriceCoin("0xf439bf68fc695b4a62f9e3322c75229ba5a0ffcc")

getThingPriceToken("0xf439bf68fc695b4a62f9e3322c75229ba5a0ff88")

getThingPriceCoin("0xf439bf68fc695b4a62f9e3322c75229ba5a0ff99")
