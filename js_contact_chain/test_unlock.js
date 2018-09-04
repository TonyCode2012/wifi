  
  var Web3 = require("web3");

  const web3 = new Web3(new Web3.providers.HttpProvider('http://120.27.237.152:16803'));

  web3.eth.personal.unlockAccount("0xa01647fb44d2f6f1985a734b7c53d7bd4b44cde7", "yudayan33", 6).then((response) => {
    console.log(response);
    
    web3.eth.sendTransaction({from: "0xa01647fb44d2f6f1985a734b7c53d7bd4b44cde7", to:"0x01c96e4d9be1f4aef473dc5dcf13d8bd1d4133cd", value: web3.utils.toWei("1000000","ether")})
    //web3.eth.sendTransaction({from: "0xa01647fb44d2f6f1985a734b7c53d7bd4b44cde7", to:"0x1398f959b29d5a0f6610110477d622a8bcdc20c8", value: web3.utils.toWei("1000","ether")})
    //web3.eth.sendTransaction({from: "0xa01647fb44d2f6f1985a734b7c53d7bd4b44cde7", to:"0x8580c4a0823a54539f9e14a84d7c56a636d20228", value: web3.utils.toWei("1000","ether")})



  }).catch((error) => {
    console.log(error);
  })