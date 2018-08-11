Import config.js and sendTransactions.js

'''
config = require("./config")
transaction = require("./sendTransactions")
'''

 For all functions, first parameter is the caller, second parameter is caller's private key 
Attention: If a function ended with "Coin", it means all tokens related in this transaction are new token.

'''
transaction.sendRawTransfer([sender], [sender's privateKey], [reciever], [amount])
'''

when user loggin, commission will be sent from adv owner to ap owner
'''
transaction.sendRawCommission([trigged client], [client privateKey], [AP Owner], [Adv Owner])
'''

If function name endup by "Coin", the function is related to 2nd token
'''
transaction.sendRawCommission([trigged client], [client privateKey], [AP Owner], [Adv Owner])
'''

User login is only related to old token by default, because user could not choise when he have no network
'''
transaction.sendRawDeduction([client], [client privateKey], [AP address])
'''


when client watch an adv, token flows from adv owner, client get 50%, AP owner get 30%, platform get 20% 
'''
transaction.sendRawWatchAdv([client], [client privateKey], [AP address], [AVD address])
'''

