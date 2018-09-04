
let Web3 = require('web3'); // https://www.npmjs.com/package/web3
var config = require("./config")
var EthereumTx = require('ethereumjs-tx')


var httpProvider = config.HttpProvider

let web3 = new Web3();

account = "0xa01647fB44D2f6F1985a734B7c53D7Bd4b44cdE7"
privateKeyStr = "2ff64f4d666b690aa5f58a75bf1b9cda7dfc367fa0bc15912900dc956326283f"


//byteCode = "0x608060405234801561001057600080fd5b5033600a60006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555061022c806100616000396000f30060806040526004361061004c576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680630b2b4560146100515780633a7db42b146100c1575b600080fd5b34801561005d57600080fd5b5061007f600480360381019080803560ff169060200190929190505050610111565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b3480156100cd57600080fd5b5061010f600480360381019080803560ff169060200190929190803573ffffffffffffffffffffffffffffffffffffffff16906020019092919050505061014d565b005b6000808260ff16600a8110151561012457fe5b0160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050919050565b600a60009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415156101a9576101fc565b8060008360ff16600a811015156101bc57fe5b0160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505b50505600a165627a7a72305820a5bcb6d8dc98ddcb12c89682d453329eafec10ec5eb0b61574333851dbb79ef70029"
web3.setProvider(new web3.providers.HttpProvider(httpProvider));
var privateKey = new Buffer(privateKeyStr, 'hex')
web3.eth.getTransactionCount(account, function (err, nonce) {  
	var tx = new EthereumTx({
		from: account,
		nonce: nonce,
		to: config.TestAdvOwner,
	  gasPrice: 2,
	  gasLimit: 4000000,
	  value: 8888800000000000000000000000,
	});
	console.log("Nonce is: ", nonce)
	//tx.sign(ethereumjs.Buffer.Buffer.from(privateKey, 'hex'));
	//var tx = new Tx(rawTx);

  tx.sign(privateKey)
	var raw = '0x' + tx.serialize().toString('hex');
	web3.eth.sendSignedTransaction(raw, function (err, transactionHash) {
	  console.log(transactionHash);
	}).on('receipt', console.log);
});

/*
var rawTx = {
  nonce: '0x00',
  gasPrice: '0x09184e72a000',
  gasLimit: '0x2710',
  to: '0x0000000000000000000000000000000000000000',
  value: '0x00',
  data: '0x7f7465737432000000000000000000000000000000000000000000000000000000600057'
}

var tx = new Tx(rawTx);
tx.sign(privateKey);

var serializedTx = tx.serialize();

web3.eth.sendSignedTransaction('0x' + serializedTx.toString('hex'))
.on('receipt', console.log);
*/