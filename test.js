config = require("./config")
transaction = require("./sendTransactions")


//const args = require('minimist')(process.argv.slice(2));
var parameters = []
for (let j = 0; j < process.argv.length; j++) {  
    parameters.push(process.argv[j])
    console.log(j + ' -> ' + (parameters[j]))
}

switch(parameters[2])  {
    case "TransferToken":
        if(parameters.length != 7){
            console.log("Error: Parameter count not match!")
            break;
        }
        else{
            transaction.sendRawTransfer(parameters[3],parameters[4],parameters[5],parameters[6])
        }
        break

    case "CommissionToken":
        if(parameters.length != 7){
            console.log("Error: Parameter count not match! ")
            break;
        }
        else{
            transaction.sendRawCommission(parameters[3],parameters[4],parameters[5],parameters[6])
        }
        break

    case "DeductionToken":
        if(parameters.length != 6){
            console.log("Error: Parameter count not match! ")
            break;
        }
        else{
            transaction.sendRawDeduction(parameters[3],parameters[4],parameters[5])
        }
        break

    case "WatchAdvToken":
        if(parameters.length != 7){
            console.log("Error: Parameter count not match! ")
            break;
        }
        else{
            transaction.sendRawWatchAdv(parameters[3],parameters[4],parameters[5],parameters[6])
        }
        break

    case "AdvSetPrice":
        if(parameters.length != 7){
            console.log("Error: Parameter count not match! 5")
            break;
        }
        else{
            transaction.sendRawAdvSetPrice(parameters[3],parameters[4],parameters[5],parameters[6])
        }
        break

    case "ApSetPrice":
        if(parameters.length != 7){
            console.log("Error: Parameter count not match! 6")
            break;
        }
        else{
            transaction.sendRawApSetPrice(parameters[3],parameters[4],parameters[5],parameters[6])
        }
        break

    case "ApRegister":
        if(parameters.length != 7){
            console.log("Error: Parameter count not match! 7")
            break;
        }
        else{
            transaction.sendRawApRegister(parameters[3],parameters[4],parameters[5],parameters[6])
        }
        break

    case "ApSetCoin":
        if(parameters.length != 7){
            console.log("Error: Parameter count not match! 8")
            break;
        }
        else{
            transaction.sendRawApSetCoin(parameters[3],parameters[4],parameters[5],parameters[6])
        }
        break

    case "AdvRegister":
        if(parameters.length != 6){
            console.log("Error: Parameter count not match! 9")
            break;
        }
        else{
            transaction.sendRawAdvRegister(parameters[3],parameters[4],parameters[5])
        }
        break

    case "AdvSetCoin":
        if(parameters.length != 7){
            console.log("Error: Parameter count not match! 10")
            break;
        }
        else{
            transaction.sendRawAdvSetCoin(parameters[3],parameters[4],parameters[5],parameters[6])
        }
        break

    case "CommissionCoin":
        if(parameters.length != 7){
            console.log("Error: Parameter count not match! 11")
            break;
        }
        else{
            transaction.sendRawCommissionCoin(parameters[3],parameters[4],parameters[5],parameters[6])
        }
        break

    case "WatchAdvCoin":
        if(parameters.length != 7){
            console.log("Error: Parameter count not match! haha")
            break;
        }
        else{
            transaction.sendRawWatchAdvCoin(parameters[3],parameters[4],parameters[5],parameters[6])
        }
        break

    case "ThingRegister":
        if(parameters.length != 7){
            console.log("Error: Parameter count not match! 12")
            break;
        }
        else{
            transaction.sendRawThingRegister(parameters[3],parameters[4],parameters[5],parameters[6])
        }
        break

    case "ThingSetCoin":
        if(parameters.length != 7){
            console.log("Error: Parameter count not match! 13")
            break;
        }
        else{
            transaction.sendRawThingSetCoin(parameters[3],parameters[4],parameters[5],parameters[6])
        }
        break


    case "BuyThingCoin":
        if(parameters.length != 6){
            console.log("Error: Parameter count not match!14")
            break;
        }
        else{
            transaction.sendRawBuyThingCoin(parameters[3],parameters[4],parameters[5])
        }
        break

    case "BuyThing":
        if(parameters.length != 6){
            console.log("Error: Parameter count not match!15")
            break;
        }
        else{
            transaction.sendRawBuyThing(parameters[3],parameters[4],parameters[5])
        }
        break

}


