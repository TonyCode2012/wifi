# Support command line to call test.js

## For example:

### Client register

When client register in, client will receive  some token

```node test.js UserRegister [Client Account] [Client PK] [Signature]```

### Client login

When client logs in, client will pay some token to Ap owner

```node test.js DeductionToken [Client Account] [Client PK] [ApAddr]```

### Commission with Token

When user logs in, the adv owner will pay token to Ap owner, when Adv price is token

```node test.js CommissionToken [Client Account] [Client PK] [ApAddr] [AdvAddr]```

### Commission with Coin

When user logs in, the adv owner will pay coin to Ap owner, when Adv price is coin

```node test.js CommissionCoin [Client Account] [Client PK] [ApAddr] [AdvAddr]```

### Watch ADV with token award

When user watch an ADV and the ADV is awared by token

```node test.js WatchAdvToken [Client Account] [Client PK] [AdvAddr] [ApAddr] ```

### Watch ADV with coin award

When user watch an ADV and the ADV is awared by coin

```node test.js WatchAdvCoin [Client Account] [Client PK] [AdvAddr] [ApAddr]```

### Buy thing with token

When client buy goods, which price is settlemented by token

```node test.js BuyThing [Client Account] [Client PK] [ThingAddr]```

### Buy thing with coin

When client buy goods, which price is settlemented by coin

```node test.js BuyThingCoin [Client Account] [Client PK] [ThingAddr]```
