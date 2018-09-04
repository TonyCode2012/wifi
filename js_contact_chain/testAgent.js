config = require("./config")
EthereumTx = require('ethereumjs-tx')
Web3 = require("web3");
var fs = require('fs');

sendTransctionRaw = function(web3, privateKey, account, contractAddr, encodedData){
	web3.eth.getTransactionCount(account, function (err, nonce) {  
        var tx = new EthereumTx({
          nonce: nonce,
          gasPrice: 2,
          gasLimit: 1000000,
          to: contractAddr,
          value: 0,
          data: encodedData,
        });
        //tx.sign(ethereumjs.Buffer.Buffer.from(privateKey, 'hex'));
      tx.sign(privateKey)
        var raw = '0x' + tx.serialize().toString('hex');
        console.log(raw)
        fs.writeFile("./testRaw.txt", raw, function(err) {
          if(err) {
              return console.log(err);
          }
      
          console.log("The raw was saved!");
      });
        //web3.eth.sendSignedTransaction(raw, function (err, transactionHash) {
          //console.log(transactionHash);
        //});
  });
}

sendRawTransfer = function(account, pk, transferTo, amount){
	var aBI = config.BalanceABI
	var addr = config.BalanceAddr
	var httpProvider = config.HttpProvider
	var web3 = new Web3(new Web3.providers.HttpProvider(httpProvider));

	var privateKey = new Buffer(pk, 'hex')
	console.log("coinbase:")
	web3.eth.getCoinbase((err, coinbase) => {console.log(coinbase)})
  	var abi = JSON.parse(aBI);
	var contractInstant = new web3.eth.Contract(abi, addr);
	contractInstant.once('Transfer',function(error, event){console.log(event);})  
	var encodedData = contractInstant.methods.transfer(transferTo, amount).encodeABI()
    sendTransctionRaw(web3, privateKey, account, addr, encodedData)
}

//sendRawTransfer("0x01c96e4d9be1f4aef473dc5dcf13d8bd1d4133cd", "e16a1130062b37f038b9df02f134d7ddd9009c54c62bd92d4ed42c0dba1189a8", "0x01c96e4d9be1f4aef473dc5dcf13d8bd1d4133cc", 7 )

rawT = "0xf8a782028802830f4240941f8f9b664d3d02b8f5ada45d7cff2b76135715ac80b8445d359fbd00000000000000000000000001c96e4d9be1f4aef473dc5dcf13d8bd1d4133cc00000000000000000000000000000000000000000000000000000000000000071ca079a2618331b687de945209592a1918fef98075a02e8631af9f784840813efadca0761a48453c9fbb4d5570d3a44843b7119ebf240660f373d1bc68d9a002ae14d5"

sendRawText = function(rawText) {
  var httpProvider = config.HttpProvider
  var web3 = new Web3(new Web3.providers.HttpProvider(httpProvider));

  web3.eth.sendSignedTransaction(rawText, function (err, transactionHash) {
    console.log(transactionHash);
  });
}

sendRawText(rawT)