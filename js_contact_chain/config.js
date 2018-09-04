module.exports = {
    HttpProvider: "http://120.27.237.152:16803",
    //WebSocketProvider:"ws://120.27.237.152:8801",
    ProxyABI : '[{"inputs":[],"payable":false,"stateMutability":"nonpayable","type":"constructor"},{"constant":false,"inputs":[{"name":"_index","type":"uint8"},{"name":"_contractAddr","type":"address"}],"name":"setContractAddr","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[{"name":"_index","type":"uint8"}],"name":"getContractAddr","outputs":[{"name":"_contractAddr","type":"address"}],"payable":false,"stateMutability":"view","type":"function"}]',
    BalanceABI : '[{"constant":true,"inputs":[],"name":"name","outputs":[{"name":"","type":"string"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":false,"inputs":[{"name":"_spender","type":"address"},{"name":"_value","type":"uint64"}],"name":"approve","outputs":[{"name":"","type":"bool"}],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[],"name":"totalSupply","outputs":[{"name":"","type":"uint64"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":false,"inputs":[{"name":"_from","type":"address"},{"name":"_to","type":"address"},{"name":"_value","type":"uint64"}],"name":"transferFrom","outputs":[{"name":"","type":"bool"}],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[],"name":"INITIAL_SUPPLY","outputs":[{"name":"","type":"uint64"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":true,"inputs":[],"name":"decimals","outputs":[{"name":"","type":"uint8"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":false,"inputs":[{"name":"_to","type":"address"},{"name":"_value","type":"uint64"}],"name":"transfer","outputs":[{"name":"","type":"bool"}],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[{"name":"_owner","type":"address"}],"name":"balanceOf","outputs":[{"name":"","type":"uint64"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":false,"inputs":[{"name":"_spender","type":"address"},{"name":"_subtractedValue","type":"uint64"}],"name":"decreaseApproval","outputs":[{"name":"","type":"bool"}],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[],"name":"symbol","outputs":[{"name":"","type":"string"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":false,"inputs":[{"name":"_spender","type":"address"},{"name":"_addedValue","type":"uint64"}],"name":"increaseApproval","outputs":[{"name":"","type":"bool"}],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[{"name":"_owner","type":"address"},{"name":"_spender","type":"address"}],"name":"allowance","outputs":[{"name":"","type":"uint64"}],"payable":false,"stateMutability":"view","type":"function"},{"inputs":[],"payable":false,"stateMutability":"nonpayable","type":"constructor"},{"anonymous":false,"inputs":[{"indexed":true,"name":"owner","type":"address"},{"indexed":true,"name":"spender","type":"address"},{"indexed":false,"name":"value","type":"uint64"}],"name":"Approval","type":"event"},{"anonymous":false,"inputs":[{"indexed":true,"name":"from","type":"address"},{"indexed":true,"name":"to","type":"address"},{"indexed":false,"name":"value","type":"uint64"}],"name":"Transfer","type":"event"},{"constant":false,"inputs":[{"name":"_advOwner","type":"address"},{"name":"_reciever","type":"address"},{"name":"_price","type":"uint64"}],"name":"advCommission","outputs":[{"name":"","type":"bool"}],"payable":false,"stateMutability":"nonpayable","type":"function"}]',
    CoinABI : '[{"constant":true,"inputs":[],"name":"name","outputs":[{"name":"","type":"string"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":false,"inputs":[{"name":"_spender","type":"address"},{"name":"_value","type":"uint64"}],"name":"approve","outputs":[{"name":"","type":"bool"}],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[],"name":"totalSupply","outputs":[{"name":"","type":"uint64"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":false,"inputs":[{"name":"_from","type":"address"},{"name":"_to","type":"address"},{"name":"_value","type":"uint64"}],"name":"transferFrom","outputs":[{"name":"","type":"bool"}],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[],"name":"INITIAL_SUPPLY","outputs":[{"name":"","type":"uint64"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":true,"inputs":[],"name":"decimals","outputs":[{"name":"","type":"uint8"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":false,"inputs":[{"name":"_to","type":"address"},{"name":"_value","type":"uint64"}],"name":"transfer","outputs":[{"name":"","type":"bool"}],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[{"name":"_owner","type":"address"}],"name":"balanceOf","outputs":[{"name":"","type":"uint64"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":false,"inputs":[{"name":"_spender","type":"address"},{"name":"_subtractedValue","type":"uint64"}],"name":"decreaseApproval","outputs":[{"name":"","type":"bool"}],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[],"name":"symbol","outputs":[{"name":"","type":"string"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":false,"inputs":[{"name":"_spender","type":"address"},{"name":"_addedValue","type":"uint64"}],"name":"increaseApproval","outputs":[{"name":"","type":"bool"}],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[{"name":"_owner","type":"address"},{"name":"_spender","type":"address"}],"name":"allowance","outputs":[{"name":"","type":"uint64"}],"payable":false,"stateMutability":"view","type":"function"},{"inputs":[],"payable":false,"stateMutability":"nonpayable","type":"constructor"},{"anonymous":false,"inputs":[{"indexed":true,"name":"owner","type":"address"},{"indexed":true,"name":"spender","type":"address"},{"indexed":false,"name":"value","type":"uint64"}],"name":"Approval","type":"event"},{"anonymous":false,"inputs":[{"indexed":true,"name":"from","type":"address"},{"indexed":true,"name":"to","type":"address"},{"indexed":false,"name":"value","type":"uint64"}],"name":"Transfer","type":"event"},{"constant":false,"inputs":[{"name":"_advOwner","type":"address"},{"name":"_reciever","type":"address"},{"name":"_price","type":"uint64"}],"name":"advCommission","outputs":[{"name":"","type":"bool"}],"payable":false,"stateMutability":"nonpayable","type":"function"}]',
    IdentityABI : '[{"constant":true,"inputs":[{"name":"","type":"address"}],"name":"allUserProfiles","outputs":[{"name":"userProfileHash_Signed","type":"string"},{"name":"status","type":"uint8"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":false,"inputs":[{"name":"_userIDHash","type":"address"},{"name":"_userProfileHash_Signed","type":"string"},{"name":"_status","type":"uint8"}],"name":"setUser","outputs":[{"name":"","type":"bool"}],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":false,"inputs":[{"name":"_userIDHash","type":"address"}],"name":"inactiveUser","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[{"name":"_userIDHash","type":"address"}],"name":"getUserAtt","outputs":[{"name":"_userProfileHash_Signed","type":"string"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":true,"inputs":[{"name":"_userIDHash","type":"address"}],"name":"getUserStatus","outputs":[{"name":"_status","type":"uint8"}],"payable":false,"stateMutability":"view","type":"function"}]',
//    ApABI : '[{"constant":true,"inputs":[{"name":"","type":"address"}],"name":"allAPs","outputs":[{"name":"bssid","type":"string"},{"name":"owner","type":"address"},{"name":"status","type":"uint8"},{"name":"price","type":"uint256"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":false,"inputs":[{"name":"_apAddr","type":"address"},{"name":"_bssid","type":"string"},{"name":"_status","type":"uint8"}],"name":"setAP","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":false,"inputs":[{"name":"_apAddr","type":"address"},{"name":"_price","type":"uint256"}],"name":"setAPPrice","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[{"name":"_apAddr","type":"address"}],"name":"getPrice","outputs":[{"name":"_price","type":"uint256"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":true,"inputs":[{"name":"_apAddr","type":"address"}],"name":"getOwner","outputs":[{"name":"_owner","type":"address"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":true,"inputs":[{"name":"_apAddr","type":"address"}],"name":"getAPStatus","outputs":[{"name":"_status","type":"uint8"}],"payable":false,"stateMutability":"view","type":"function"}]',
//    AdvABI : '[{"constant":true,"inputs":[{"name":"","type":"address"}],"name":"allADVs","outputs":[{"name":"owner","type":"address"},{"name":"status","type":"uint8"},{"name":"price","type":"uint64"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":false,"inputs":[{"name":"_advAddr","type":"address"},{"name":"_status","type":"uint8"}],"name":"setAdv","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":false,"inputs":[{"name":"_advAddr","type":"address"},{"name":"_price","type":"uint64"}],"name":"setAdvPrice","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[{"name":"_advAddr","type":"address"}],"name":"getPrice","outputs":[{"name":"_price","type":"uint64"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":true,"inputs":[{"name":"_advAddr","type":"address"}],"name":"getOwner","outputs":[{"name":"_owner","type":"address"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":true,"inputs":[{"name":"_apAddr","type":"address"}],"name":"getAdvStatus","outputs":[{"name":"_status","type":"uint8"}],"payable":false,"stateMutability":"view","type":"function"}]',
    ApABI : '[{"constant":true,"inputs":[{"name":"","type":"address"}],"name":"allAPs","outputs":[{"name":"bssid","type":"string"},{"name":"owner","type":"address"},{"name":"status","type":"uint8"},{"name":"price","type":"uint64"},{"name":"coin","type":"uint64"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":false,"inputs":[{"name":"_APAddr","type":"address"},{"name":"_bssid","type":"string"},{"name":"_status","type":"uint8"}],"name":"setAP","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":false,"inputs":[{"name":"_APAddr","type":"address"},{"name":"_price","type":"uint64"}],"name":"setAPPrice","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[{"name":"_APAddr","type":"address"}],"name":"getPrice","outputs":[{"name":"_price","type":"uint64"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":false,"inputs":[{"name":"_APAddr","type":"address"},{"name":"_coin","type":"uint64"}],"name":"setAPCoin","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[{"name":"_APAddr","type":"address"}],"name":"getCoin","outputs":[{"name":"_coin","type":"uint64"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":true,"inputs":[{"name":"_APAddr","type":"address"}],"name":"getOwner","outputs":[{"name":"_owner","type":"address"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":true,"inputs":[{"name":"_APAddr","type":"address"}],"name":"getAPStatus","outputs":[{"name":"_status","type":"uint8"}],"payable":false,"stateMutability":"view","type":"function"}]',
    AdvABI : '[{"constant":true,"inputs":[{"name":"","type":"address"}],"name":"allADVs","outputs":[{"name":"owner","type":"address"},{"name":"status","type":"uint8"},{"name":"price","type":"uint64"},{"name":"coin","type":"uint64"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":false,"inputs":[{"name":"_advAddr","type":"address"},{"name":"_status","type":"uint8"}],"name":"setAdv","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":false,"inputs":[{"name":"_advAddr","type":"address"},{"name":"_price","type":"uint64"}],"name":"setAdvPrice","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":false,"inputs":[{"name":"_advAddr","type":"address"},{"name":"_coin","type":"uint64"}],"name":"setAdvCoin","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[{"name":"_advAddr","type":"address"}],"name":"getPrice","outputs":[{"name":"_price","type":"uint64"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":true,"inputs":[{"name":"_advAddr","type":"address"}],"name":"getCoin","outputs":[{"name":"_coin","type":"uint64"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":true,"inputs":[{"name":"_advAddr","type":"address"}],"name":"getOwner","outputs":[{"name":"_owner","type":"address"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":true,"inputs":[{"name":"_APAddr","type":"address"}],"name":"getAdvStatus","outputs":[{"name":"_status","type":"uint8"}],"payable":false,"stateMutability":"view","type":"function"}]',
    //    ThingABI : '[{"constant":true,"inputs":[{"name":"","type":"address"}],"name":"things","outputs":[{"name":"owner","type":"address"},{"name":"status","type":"uint8"},{"name":"price","type":"uint64"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":false,"inputs":[{"name":"_thingAddr","type":"address"},{"name":"_price","type":"uint64"},{"name":"_status","type":"uint8"}],"name":"setThing","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":false,"inputs":[{"name":"_thingAddr","type":"address"},{"name":"_price","type":"uint64"}],"name":"setPrice","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[{"name":"_thingAddr","type":"address"}],"name":"getPrice","outputs":[{"name":"_price","type":"uint64"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":true,"inputs":[{"name":"_thingAddr","type":"address"}],"name":"getOwner","outputs":[{"name":"_owner","type":"address"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":true,"inputs":[{"name":"_thingAddr","type":"address"}],"name":"getStatus","outputs":[{"name":"_status","type":"uint8"}],"payable":false,"stateMutability":"view","type":"function"}]',
    ThingABI : '[{"constant":true,"inputs":[{"name":"","type":"address"}],"name":"things","outputs":[{"name":"owner","type":"address"},{"name":"status","type":"uint8"},{"name":"price","type":"uint64"},{"name":"coin","type":"uint64"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":false,"inputs":[{"name":"_thingAddr","type":"address"},{"name":"_price","type":"uint64"},{"name":"_status","type":"uint8"}],"name":"setThing","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":false,"inputs":[{"name":"_thingAddr","type":"address"},{"name":"_price","type":"uint64"}],"name":"setPrice","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[{"name":"_thingAddr","type":"address"}],"name":"getPrice","outputs":[{"name":"_price","type":"uint64"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":false,"inputs":[{"name":"_thingAddr","type":"address"},{"name":"_coin","type":"uint64"}],"name":"setCoin","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[{"name":"_thingAddr","type":"address"}],"name":"getCoin","outputs":[{"name":"_coin","type":"uint64"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":true,"inputs":[{"name":"_thingAddr","type":"address"}],"name":"getOwner","outputs":[{"name":"_owner","type":"address"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":true,"inputs":[{"name":"_thingAddr","type":"address"}],"name":"getStatus","outputs":[{"name":"_status","type":"uint8"}],"payable":false,"stateMutability":"view","type":"function"}]',
    //ControllerABI_ : '[{"inputs":[],"payable":false,"stateMutability":"nonpayable","type":"constructor"},{"anonymous":false,"inputs":[{"indexed":false,"name":"_requester","type":"address"},{"indexed":false,"name":"_apAddr","type":"address"},{"indexed":false,"name":"_value","type":"uint64"}],"name":"Deduction","type":"event"},{"anonymous":false,"inputs":[{"indexed":false,"name":"advOwner","type":"address"},{"indexed":false,"name":"apOwner","type":"address"},{"indexed":false,"name":"advPrice","type":"uint64"}],"name":"AdvCommission","type":"event"},{"constant":false,"inputs":[{"name":"_signature","type":"string"}],"name":"userRegister","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":false,"inputs":[{"name":"_apAddr","type":"address"},{"name":"bssid","type":"string"}],"name":"aPRegister","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":false,"inputs":[{"name":"_apAddr","type":"address"},{"name":"_price","type":"uint64"}],"name":"aPSetPrice","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":false,"inputs":[{"name":"_advAddr","type":"address"}],"name":"advRegister","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":false,"inputs":[{"name":"_advAddr","type":"address"},{"name":"_price","type":"uint64"}],"name":"advSetPrice","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":false,"inputs":[{"name":"_apAddr","type":"address"}],"name":"deduction","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":false,"inputs":[{"name":"_apAddr","type":"address"},{"name":"_advAddr","type":"address"}],"name":"commission","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":false,"inputs":[{"name":"_thingAddr","type":"address"},{"name":"_price","type":"uint64"}],"name":"thingRegister","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":false,"inputs":[{"name":"_thingAddr","type":"address"},{"name":"_price","type":"uint64"}],"name":"thingSetPrice","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":false,"inputs":[{"name":"_advAddr","type":"address"},{"name":"_apAddr","type":"address"}],"name":"watchAdv","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":false,"inputs":[{"name":"_thingAddr","type":"address"}],"name":"buyThing","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"}]',
    ControllerABI : '[{"inputs":[],"payable":false,"stateMutability":"nonpayable","type":"constructor"},{"anonymous":false,"inputs":[{"indexed":false,"name":"_requester","type":"address"},{"indexed":false,"name":"_apAddr","type":"address"},{"indexed":false,"name":"_value","type":"uint64"}],"name":"Deduction","type":"event"},{"anonymous":false,"inputs":[{"indexed":false,"name":"advOwner","type":"address"},{"indexed":false,"name":"apOwner","type":"address"},{"indexed":false,"name":"advPrice","type":"uint64"}],"name":"AdvCommission","type":"event"},{"constant":false,"inputs":[{"name":"_signature","type":"string"}],"name":"userRegister","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":false,"inputs":[{"name":"_apAddr","type":"address"},{"name":"bssid","type":"string"}],"name":"aPRegister","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":false,"inputs":[{"name":"_apAddr","type":"address"},{"name":"_price","type":"uint64"}],"name":"aPSetPrice","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":false,"inputs":[{"name":"_advAddr","type":"address"}],"name":"advRegister","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":false,"inputs":[{"name":"_advAddr","type":"address"},{"name":"_price","type":"uint64"}],"name":"advSetPrice","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":false,"inputs":[{"name":"_apAddr","type":"address"}],"name":"deduction","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":false,"inputs":[{"name":"_apAddr","type":"address"},{"name":"_advAddr","type":"address"}],"name":"commission","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":false,"inputs":[{"name":"_thingAddr","type":"address"},{"name":"_price","type":"uint64"}],"name":"thingRegister","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":false,"inputs":[{"name":"_thingAddr","type":"address"},{"name":"_price","type":"uint64"}],"name":"thingSetPrice","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":false,"inputs":[{"name":"_advAddr","type":"address"},{"name":"_apAddr","type":"address"}],"name":"watchAdv","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":false,"inputs":[{"name":"_thingAddr","type":"address"}],"name":"buyThing","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"}]',
    CoinControllerABI : '[{"inputs":[],"payable":false,"stateMutability":"nonpayable","type":"constructor"},{"anonymous":false,"inputs":[{"indexed":false,"name":"advOwner","type":"address"},{"indexed":false,"name":"apOwner","type":"address"},{"indexed":false,"name":"advPrice","type":"uint64"}],"name":"AdvCommission","type":"event"},{"constant":false,"inputs":[{"name":"_apAddr","type":"address"},{"name":"_coin","type":"uint64"}],"name":"aPSetCoin","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":false,"inputs":[{"name":"_advAddr","type":"address"},{"name":"_coin","type":"uint64"}],"name":"advSetCoin","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":false,"inputs":[{"name":"_apAddr","type":"address"},{"name":"_advAddr","type":"address"}],"name":"commissionCoin","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":false,"inputs":[{"name":"_thingAddr","type":"address"},{"name":"_coin","type":"uint64"}],"name":"thingSetCoin","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":false,"inputs":[{"name":"_advAddr","type":"address"},{"name":"_apAddr","type":"address"}],"name":"watchAdvCoin","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":false,"inputs":[{"name":"_thingAddr","type":"address"}],"name":"buyThingCoin","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"}]',

    /*
    ProxyAddr : "0x3de26b8d950440d18f5f53932c9c3da9c1e9683e",
    BalanceAddr : "0x1f8f9b664d3d02b8f5ada45d7cff2b76135715ac",
    CoinAddr: "0xa56a56993eb805cace281d13d8cd35e7dda2585e",
    IdentityAddr : "0xef9c5da27e0bd5f9cc74cfcbf5db26df362f1ba0",
    ApAddr : "0x62cc335cde770babf06a39cb337ffa722bd87748",
    AdvAddr : "0xeff1ec3378b7ee6dd1891b52ad46f281c7b9244e",
    ControllerAddr_ : "0xaf637f082ed8339328e43499b9455964c6bf22d0",
    ControllerAddr: "0x2fde06df217eb8f14c6056e1c14cb423273b4478",
    CoinControllerAddr: "0x950a10254e2e3ab8a67cef83132f191964e56db4",
    ThingAddr : "0x90cccf70f164254cfe69bba296da088e5ab34923",
*/
    ProxyAddr: "0x2fbeE9B0438bD61BD27b2102690346f08F7a539A",
    /*
    BalanceAddr : "0xd0d33edf8c8be8f6070ac4446e782a6df3c582b7",
    CoinAddr: "0xf813bf7e55ad267119004ca85329788c87a963c2",
    IdentityAddr : "0x2754ffa6927e0cb5a9a672db6b547c2ef821efc2",
    ApAddr : "0x7406b41a5fcd2d4a106d3aebfcba3bebafe42d19",
    AdvAddr : "0xbd8edaed8f1f5e8832e21ea5a05ea88b30c23d8e",
    ControllerAddr_ : "0xc50fd470107415c1910e15174390f667cb08fe97",
    ControllerAddr: "0xc50fd470107415c1910e15174390f667cb08fe97",
    CoinControllerAddr: "0x8debe1721824823119768d8d8c22722f127ae2a1",
    ThingAddr : "0x54fa68859c3560df3a6db962171fac52e03460c8",
    */
    IdentityAddr: "0xCAf2cd1C8120d11A501e87a6aA1A527af539aD5E",
    APAddr: "0xd50669e5236b1AcEe7338f4d80e3D7A56dB0837e",
    AdvAddr: "0x8fD56F492C2B421A16FFBB246aB265959b77C058",
    BalanceAddr: "0x29ab720a8a40f997739CD229950f610Bcb06Ba01",
    ThingAddr: "0x12d6EafC0e7c131De9EF6d247920bE8D6B9840C1",
    CoinAddr: "0xBF1B3ECCfB316352e410e66857bAbB1Cb3aC71b6",
    CoinControllerAddr: "0x12E340FD23b48233fFCD263BEc9e32aD78E3E5Aa",
    ControllerAddr: "0x1e6E7d9e912E954277d2b363d1EEe871093025EE",

    TestApAddr : "0xf439bf68fc695b4a62f9e3322c75229ba5a0ff33",
    TestAdvAddr : "0xf439bf68fc695b4a62f9e3322c75229ba5a0ffbb",

    TestApAddr2 : "0xf439bf68fc695b4a62f9e3322c75229ba5a0ff44",
    TestAdvAddr2 : "0xf439bf68fc695b4a62f9e3322c75229ba5a0ffcc",

    TestThingAddr: "0xf439bf68fc695b4a62f9e3322c75229ba5a0ff88",
    TestThingAddr2: "0xf439bf68fc695b4a62f9e3322c75229ba5a0ff99",

    Admin: "0xa01647fB44D2f6F1985a734B7c53D7Bd4b44cdE7",
    AdminPK: "2ff64f4d666b690aa5f58a75bf1b9cda7dfc367fa0bc15912900dc956326283f",
    TestUser1 : "0x01c96e4d9be1f4aef473dc5dcf13d8bd1d4133cd",
    TestUser1PK : "e16a1130062b37f038b9df02f134d7ddd9009c54c62bd92d4ed42c0dba1189a8",
    TestUser2 : "0x1398f959b29d5a0f6610110477d622a8bcdc20c8",
    TestUser2PK : "7c83bdac3a9488ea87a8c865e48883aa93b9f4140787ea9316a7aa4f5aeb5740",
    TestApOwner : "0x93f97961eb166e2d96972ca192a20fb29138e2dd",
    TestApOwnerPK : "d9a24cd73459f9f9279c3fbeaecc2633a5fdc08e29a44d6c8c8ba09259ad6499",
    TestAdvOwner : "0x8580c4a0823a54539f9e14a84d7c56a636d20228",
    TestAdvOwnerPK : "d59376c6ba08376b337b4aa84ce6bf29e2ab562ef51a7c1ad962973cc8c22ec2"
}
