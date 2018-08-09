config = require("./config")
transaction = require("./sendTransactions")


// Test transfer
transaction.sendRawTransfer(
    config.TestAdvOwner, 
    config.TestAdvOwnerPK, 
    config.TestUser1, 
    100
)


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
)*/


/*
transaction.sendRawApSetPrice(
    config.TestApOwner, 
    config.TestApOwnerPK, 
    config.TestApAddr,
    10
)
*/