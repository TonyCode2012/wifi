# Read Me First

## Support Command line For example:

### Client register

When client register in, client will receive  some token

```node client.js UserRegister [Client Account] [Client PK] [Signature]```

### Client login

When client logs in, client will pay some token to Ap owner

```node client.js DeductionToken [Client Account] [Client PK] [ApAddr]```

### Commission with Token

When user logs in, the adv owner will pay token to Ap owner, when Adv price is token

```node client.js CommissionToken [Client Account] [Client PK] [ApAddr] [AdvAddr]```

### Commission with Coin

When user logs in, the adv owner will pay coin to Ap owner, when Adv price is coin

```node client.js CommissionCoin [Client Account] [Client PK] [ApAddr] [AdvAddr]```

### Watch ADV with token award

When user watch an ADV and the ADV is awared by token

```node client.js WatchAdvToken [Client Account] [Client PK] [AdvAddr] [ApAddr] ```

### Watch ADV with coin award

When user watch an ADV and the ADV is awared by coin

```node client.js WatchAdvCoin [Client Account] [Client PK] [AdvAddr] [ApAddr]```

### Buy thing with token

When client buy goods, which price is settlemented by token

```node client.js BuyThing [Client Account] [Client PK] [ThingAddr]```

### Buy thing with coin

When client buy goods, which price is settlemented by coin

```node client.js BuyThingCoin [Client Account] [Client PK] [ThingAddr]```

## Some preseted value

TestApAddr : "0xf439bf68fc695b4a62f9e3322c75229ba5a0ff33"
    price token: 10
TestAdvAddr : "0xf439bf68fc695b4a62f9e3322c75229ba5a0ffbb"
    price token: 30
TestApAddr2 : "0xf439bf68fc695b4a62f9e3322c75229ba5a0ff44"
    price coin: 15
TestAdvAddr2 : "0xf439bf68fc695b4a62f9e3322c75229ba5a0ffcc"
    price coin: 30
TestThingAddr : "0xf439bf68fc695b4a62f9e3322c75229ba5a0ff88"
    price token: 20
TestThingAddr2 : "0xf439bf68fc695b4a62f9e3322c75229ba5a0ff99"
    price coin: 20
