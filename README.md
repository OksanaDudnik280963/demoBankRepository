#### Examples Request bode in the JSON 

Please on the postman input JSON as Request Bode

### POST 127.0.0.1:8082/api/accounts/create
### for create account:
    {"firstName":"Oksana", "lastName":"Dudnik", "otherName":"","gender":"FEMALE",
     "address":"9 Singleton Close, Preston, Fulwood", "stateOfOrigin":"ACTIVE",
 ","accountBalance":"0.0","email":"asyadudnik@hotmail.com","phoneNumber":"07492555481", "sortCode":"12-13-14", "bankName":"Bank1",
    "alternativePhoneNumber":"","status":""
    }
#### Response from Postman:
{
"responseCode": "002",
"responseMessage": "Account created successfully!",
"accountInfo": {
"accountName": "Oksana Dudnik ",
"accountBalance": 0,
"accountNumber": "31812133",
"sortCode": "67-60-44",
"bankName": "Bank1"
}
}
Then add 35 pounds into created account.

### POST 127.0.0.1:8082/api/transactions/create    
### for create transaction:
       {"sourceAccountSortCode":"17-60-49",
        "sourceAccountNumber":"09905025",
         "targetAccountSortCode":"67-60-44",
         "targetAccountNumber":"31812133",
         "targetOwnerName":"Sofiia Nastasiuk",
         "amount":"12.0",
         "reference":"test",
           }
#####
First response :
{
"responseCode": "004",
"responseMessage": "Unable to find an account matching this sort code and account number",
"accountInfo": {
"accountName": "",
"accountBalance": 0.0,
"accountNumber": "",
"sortCode": "",
"bankName": ""
}
}
Create at first second account 
       POST: 127.0.0.1:8082/api/accounts/create
{"firstName":"Sofiia", "lastName":"Nastasiuk", "otherName":"","gender":"FEMALE",
"address":"9 Singleton Close, Preston, Fulwood", "stateOfOrigin":"ACTIVE",
"accountNumber":"09905025","accountBalance":"0.0","email":"sofiiaNastasiuk@hotmail.com",
"phoneNumber":"+573246858282", "sortCode":"17-60-49", "bankName":"Bank2",
"alternativePhoneNumber":"","status":"ACTIVE"
}
Response:
{
"responseCode": "002",
"responseMessage": "Account created successfully!",
"accountInfo": {
"accountName": "Sofiia Nastasiuk ",
"accountBalance": 0,
"accountNumber": "50307916",
"sortCode": "76-30-50",
"bankName": "Bank2"
}
}
Then creat transaction:
{"sourceAccountSortCode":"76-30-50",
"sourceAccountNumber":"50307916",
"targetAccountSortCode":"67-60-44",
"targetAccountNumber":"31812133",
"targetOwnerName":"Sofiia Nastasiuk",
"amount":"12.0",
"reference":"test",
}
Response:
{
"responseCode": "006",
"responseMessage": "Your account does not have sufficient balance",
"accountInfo": {
"accountName": "Sofiia Nastasiuk ",
"accountBalance": 0.00,
"accountNumber": "50307916",
"sortCode": "76-30-50",
"bankName": "Bank2"
}
}
Add money to new account
Then response will be:
{
"responseCode": "003",
"responseMessage": "Operation completed successfully",
"accountInfo": {
"accountName": "Sofiia Nastasiuk ",
"accountBalance": 58.00,
"accountNumber": "50307916",
"sortCode": "76-30-50",
"bankName": "Bank2"
}
}
==============================================================
### GET 127.0.0.1:8082/api/transactions/list
### You will receive Bank response:
[
{
"id": 5,
"sourceAccountSortCode": "76-30-50",
"sourceAccountNumber": "50307916",
"targetAccountSortCode": "67-60-44",
"targetAccountNumber": "31812133",
"targetOwnerName": "Oksana Dudnik ",
"amount": 12.00,
"reference": "test",
"category": null,
"type": null,
"initiationDate": "2024-12-04T14:12:37.118824",
"completionDate": "2024-12-04T14:12:37.113827"
}
]
###==============================================================================
