config = require("./config")
EthereumTx = require('ethereumjs-tx')
Web3 = require("web3");

exports.sendTransction = function(web3, privateKey, account, contractAddr, encodedData){
	web3.eth.getTransactionCount(account, function (err, nonce) {  
        var tx = new EthereumTx({
          nonce: nonce,
          gasPrice: web3.utils.toHex(web3.utils.toWei('2', 'gwei')),
          gasLimit: 1000000,
          to: contractAddr,
          value: 0,
          data: encodedData,
        });
        //tx.sign(ethereumjs.Buffer.Buffer.from(privateKey, 'hex'));
      tx.sign(privateKey)
        var raw = '0x' + tx.serialize().toString('hex');
        web3.eth.sendSignedTransaction(raw, function (err, transactionHash) {
          console.log(transactionHash);
        });
  });
}
