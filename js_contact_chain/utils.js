config = require("./config")
EthereumTx = require('ethereumjs-tx')
Web3 = require("web3");

exports.sendTransction = function(web3, privateKey, account, contractAddr, encodedData){
	web3.eth.getTransactionCount(account, function (err, nonce) {  
    console.log("Contract Addr:" + contractAddr)
        var tx = new EthereumTx({
          nonce: nonce,
          gasPrice: 2,
          gasLimit: 4600000,
          to: contractAddr,
          value: 0,
          data: encodedData,
        });
        //tx.sign(ethereumjs.Buffer.Buffer.from(privateKey, 'hex'));
      tx.sign(privateKey)
        var raw = '0x' + tx.serialize().toString('hex');
        web3.eth.sendSignedTransaction(raw, function (err, transactionHash) {
          console.log(transactionHash);
        }).on('receipt', console.log);
  });
}

exports.hexStrToInt = function(str){
  var location=0;
  for(var index = 2, jk = str.length; index < jk; index++) {  
      if (str[index]!=0){
          location = index
          break
      }
  }
  finalHexStr = str.substring(location-1)
  intStr = hexToDec(finalHexStr)
  return parseInt(intStr, 10)
}

function hexToDec(s) {
  var i, j, digits = [0], carry;
  for (i = 0; i < s.length; i += 1) {
      carry = parseInt(s.charAt(i), 16);
      for (j = 0; j < digits.length; j += 1) {
          digits[j] = digits[j] * 16 + carry;
          carry = digits[j] / 10 | 0;
          digits[j] %= 10;
      }
      while (carry > 0) {
          digits.push(carry % 10);
          carry = carry / 10 | 0;
      }
  }
  return digits.reverse().join('');
}
