config = require("./config")
transaction = require("./sendTransactions")

/*
// Test transfer
transaction.sendRawTransfer(
    config.TestAdvOwner, 
    config.TestAdvOwnerPK, 
    config.TestUser1, 
    100
)
*/

/*
transaction.sendRawCommission(
    config.TestUser1, 
    config.TestUser1PK, 
    config.TestApAddr, 
    config.TestAdvAddr
)
*/


/*
transaction.sendRawDeduction(
    config.TestUser1, 
    config.TestUser1PK, 
    config.TestApAddr
)
*/

/*
transaction.sendRawWatchAdv(
    config.TestUser1, 
    config.TestUser1PK, 
    config.TestAdvAddr,
    config.TestApAddr
)
*/

/*
transaction.sendRawAdvSetPrice(
    config.TestAdvOwner, 
    config.TestAdvOwnerPK, 
    config.TestAdvAddr,
    30
)
*/

/*
transaction.sendRawApSetPrice(
    config.TestApOwner, 
    config.TestApOwnerPK, 
    config.TestApAddr,
    10
)
*/


/*
transaction.sendRawApRegister(
    config.TestApOwner, 
    config.TestApOwnerPK, 
    config.TestApAddr2,
    "testBSSID2"
)
*/

/*
transaction.sendRawApSetCoin(
    config.TestApOwner, 
    config.TestApOwnerPK, 
    config.TestApAddr2,
    10
)
*/



/*
transaction.sendRawAdvRegister(
    config.TestAdvOwner, 
    config.TestAdvOwnerPK, 
    config.TestAdvAddr2
)
*/

/*
transaction.sendRawAdvSetCoin(
    config.TestAdvOwner, 
    config.TestAdvOwnerPK, 
    config.TestAdvAddr2,
    10
)
*/


/*
transaction.sendRawCommissionCoin(
    config.TestUser1, 
    config.TestUser1PK, 
    config.TestApAddr, 
    config.TestAdvAddr2
)
*/

/*
transaction.sendRawWatchAdvCoin(
    config.TestUser1, 
    config.TestUser1PK, 
    config.TestAdvAddr2,
    config.TestApAddr
)
*/

/*
transaction.sendRawThingRegister(
    config.TestAdvOwner, 
    config.TestAdvOwnerPK, 
    config.TestThingAddr,
    5
)
*/

/*
transaction.sendRawThingSetCoin(
    config.TestAdvOwner, 
    config.TestAdvOwnerPK, 
    config.TestThingAddr,
    10
)
*/

/*
transaction.sendRawBuyThingCoin(
    config.TestUser1, 
    config.TestUser1PK, 
    config.TestThingAddr
)
*/


transaction.sendRawBuyThing(
    config.TestUser1, 
    config.TestUser1PK, 
    config.TestThingAddr
)

