# WALLET MICROSERVICE

This simple microservice project is designed to provide APIs that other microservices will use so users in the gaming app can successfully perform credit/debit transactions with their wallets.

* Technologies :
    * Java 11
    * Spring boot
    * Maven 3
    * Spring Data JPA with Hibernate
    * H2 Database
    * JUnit
    * Mockito
    * Slf4j
    * SwaggerUI
    * Docker

## Installation

1. Install Java 11 and Maven 3.
2. Clone the project : 
  ```bash
git clone https://github.com/erdemakkuzu/wallet-service.git
``` 
3. Go to project folder with command line and run the following command : 
```bash
mvn clean package
```
4. **wallet-service.jar** should have been created under  the target folder. Run the .jar file with the following command: 
```bash
java -jar target/wallet-service.jar
```
Or if you prefer to run the application inside a container, then please  follow the steps below after creating the **wallet-service.jar** (3rd step above)

1. Make sure that your docker engine is up.
2. Execute the following command under the project folder. It will build a docker image.
```bash
docker build -t wallet-service.jar .
```
3. Run the following command to see the docker images. You should be able to see **wallet-service.jar** image.
```bash
docker image ls
```
4. And finally execute this command 
```bash
docker run -p 8080:8080 wallet-service.jar 
```


By clicking the link below, you should be accessing the swagger API documentation.
```bash
http://localhost:8080/swagger-ui.html
```

## Database Design

![Db Scema](/src/main/resources/walletServiceDbSchema.png "Db schema")

H2 in memory database was used in this project.

Console URL = http://localhost:8080/h2/

You can examine the database structure and tables of the project from the diagram above.

To keep things simple, the number of tables is limited to 3.

Relationships between tables:
1. **One player** can have **many wallets.**
2. **One wallet** can have **many transactions.**

## Endpoints
In this chapter you can find detailed information about the APIs.
For swagger documenation of the APIs, visit the link = http://localhost:8080/swagger-ui.html 

**1. POST player /api/players**
* You can create a new player with this endpoint

Request body example:
```json
{
  "name" : "eakkuzu",
  "first_name" : "Erdem",
  "last_name" : "Akkuzu",
  "age" : 28,
  "gender" : "male"
}
```
* name must be unique and can't be null
* first name can't be null
* last name can't be null


Success response example (Http Status = 201 - Created):
```json
{
  "player_name": "eakkuzu",
  "player_id": 2
}
```

Fail response example (Http Staus = 409 - Conflict):
```json
{
  "error_code": "player_already_exists",
  "field": "name",
  "value": "eakkuzu"
}
```

Note that : This API does not create wallet. **It only creates the player account**.

**2. GET player /api/players/{name}**
* You can retrieve information of a specific player by giving the name of the player (player name) as a path variable.

Success response example (Http Status = 200 - OK - No wallet (New user)):
```json
{
  "id": 1,
  "name": "eakkuzu",
  "first_name": "Erdem",
  "last_name": "Akkuzu",
  "wallets": [],
  "total_balance": []
}
```
* If total balance list and wallets list are empty, that means **user does not have a wallet(s)** yet.

Success response example (Http Status = 200 - OK - User has 2 wallets):
```json
{
  "id": 1,
  "name": "eakkuzu",
  "first_name": "Erdem",
  "last_name": "Akkuzu",
  "wallets": [
    {
      "id": 1,
      "name": "Sweedish Krone wallet",
      "balance": 123.0,
      "currency": "SEK",
      "create_date": "2022-07-30T19:55:12.207+00:00"
    },
    {
      "id": 2,
      "name": "Jackpot Wallet in USD",
      "balance": 88.0,
      "currency": "USD",
      "create_date": "2022-07-30T19:55:29.546+00:00"
    }
  ],
  "total_balance": [
    {
      "currency": "USD",
      "balance": 88.0
    },
    {
      "currency": "SEK",
      "balance": 123.0
    }
  ]
}
```
* total_balance field indicates the player's total balances for all currencies. **Player can have more than one wallets for each currency**.

**3. GET player /api/players**

 Returns the information of all players.

Success response example (Http Status = 200 - OK)
```json
[
  {
    "id": 1,
    "name": "eakkuzu",
    "first_name": "Erdem",
    "last_name": "Akkuzu",
    "wallets": [
      {
        "id": 1,
        "name": "Sweedish Krone wallet",
        "balance": 123.0,
        "currency": "SEK",
        "create_date": "2022-07-30T19:55:12.207+00:00"
      },
      {
        "id": 2,
        "name": "Jackpot Wallet in USD",
        "balance": 88.0,
        "currency": "USD",
        "create_date": "2022-07-30T19:55:29.546+00:00"
      }
    ],
    "total_balance": [
      {
        "currency": "USD",
        "balance": 88.0
      },
      {
        "currency": "SEK",
        "balance": 123.0
      }
    ]
  },
  {
    "id": 2,
    "name": "johnDoe",
    "first_name": "John",
    "last_name": "Doe",
    "wallets": [
      {
        "id": 3,
        "name": "Roulette wallet",
        "balance": 88.0,
        "currency": "USD",
        "create_date": "2022-07-30T20:02:50.616+00:00"
      },
      {
        "id": 4,
        "name": "Jackpot wallet",
        "balance": 72.0,
        "currency": "USD",
        "create_date": "2022-07-30T20:03:01.882+00:00"
      }
    ],
    "total_balance": [
      {
        "currency": "USD",
        "balance": 160.0
      }
    ]
  }
]
```

**4. POST Wallet /api/wallet/{playerName}**

* You can create a wallet by giving playerName as parameter.

Request body example:
```json
{
  "name" : "Jackpot wallet",
  "balance" : "72",
  "currency" : "USD"
}
```

Success response example (Http Status = 201 - CREATED):
```json
{
  "id": 5,
  "name": "Jackpot wallet",
  "owner": "johnDoe",
  "created_date": "2022-07-30T20:10:35.518+00:00",
  "balance": 72.0,
  "currency": "USD"
}
```

Fail response example (Http Staus = 404 - Not Found):
```json
{
  "error_code": "player_not_found",
  "value": "william123"
}
```

**5. POST Credit /api/wallet/{walletId}/credit**

 Credit transaction API. It takes a wallet id as a path variable.

Request body example:
```json
{
  "hash_id" : "3554s2333",
  "amount" : 18,
  "currency" : "SEK",
  "note" : "Wanna play more "
}
```

* hash_id must be unique in order to achive the transaction.
* amount must be a positive number (can be decimal).
* **Wallet's currency** and the **currency of the request** must be **same**.
* currency must be one of the supported currencies **(USD, EU, TR, SEK)**.  

Success response example (Http Status = 201 - Created):
```json
{
  "walletId": 1,
  "transaction_hash_id": "3554s2333",
  "currency": "SEK",
  "current_balance": 142.0
}
```

Fail response example (Http Status = 409 - Conflict):
```json
{
  "error_code": "non_unique_transaction_hash_id",
  "value": "3554s2333"
}
```

Fail response example (Http Status = 400 - Bad Request):
```json
{
  "error_code": "transaction_and_wallet_currency_mismatch",
  "value": "USD"
}
```

**6. POST Debit /api/wallet/{walletId}/debit**

Debit transaction API. It takes a wallet id as a path variable.

Request body example:
```json
{
  "hash_id" : "123345678c9313121",
  "amount" : 11,
  "currency" : "USD",
  "note" : "Need some cash"
}
```
* hash_id must be unique in order to achive the transaction.
* amount must be a positive number (can be decimal).
* **amount can not be more than balace of the wallet.**
* **Wallet's currency** and the **currency of the request** must be **same**.
* currency must be one of the supported currencies **(USD, EU, TR, SEK)**.


Success response example (Http Status = 201 - Created):
```json
{
  "walletId": 1,
  "transaction_hash_id": "123345678c9313121",
  "currency": "SEK",
  "current_balance": 131.0
}
```

Fail response example (Http Staus = 400 - Bad Request):
```json
{
  "error_code": "not_enough_balance",
  "details": "Wallet balance : 131.0"
}
```

**7. GET Wallet transaction history /api/wallet/{walletId}/transaction-history**

With this API you can retrieve the transactions of a specific wallet. Wallet Id must be given as path variable.

Success response example (Http Status = 200 - OK):
```json
{
  "id": 1,
  "owner": "eakkuzu",
  "name": "Sweedish Krone wallet",
  "balance": 131.0,
  "currency": "SEK",
  "created_date": "2022-07-30T19:55:12.207+00:00",
  "transactions": [
    {
      "hash_id": "3554233",
      "amount": 1.0,
      "currency": "SEK",
      "type": "CREDIT",
      "note": "transaction test note",
      "date": "2022-07-30T20:16:28.067+00:00"
    },
    {
      "hash_id": "3554s2333",
      "amount": 18.0,
      "currency": "SEK",
      "type": "CREDIT",
      "note": "Wanna play more ",
      "date": "2022-07-30T20:18:34.069+00:00"
    },
    {
      "hash_id": "123345678c9313121",
      "amount": 11.0,
      "currency": "SEK",
      "type": "DEBIT",
      "note": "Need some cash",
      "date": "2022-07-30T20:22:01.548+00:00"
    }
  ]
}
```

## Improvements for the future

### 1. Database 
* Relational databases like PostgreSQL, MySql or Oracle Db can be used instead of H2 In memory database. It would be a healthier approach to write and read data to disk.
### 2. Authorization
* JWT token can be used for authorization.  If the Bearer token in the header is not valid, the request will be denied. Currently this validation logic is skipped.
### 3. Message Broker
* For transactions we can use a message broker such as Kafka. Transaction requests can be queued and processed by consumers subscribed to it with FIFO logic. This approach will make our application more scalable and fault tolerat.
### 4.  Caching
* For some structures that don't change frequently (such as currencies) an inmemory caching library like **Cache2k** can be used. Once the application is up, we will inflate the cache and for each request, instead of going to db we will retrieve the data from cache.
* One more endpoint will be created for refresh cache operation. When we add a new value to db (for currency) we will trigger this endpoint. 
* Also we can store some of the GET Responses (Get Player response) in cache for a short period of time (1 minute) . Key will be {playerId} and value will be GetPlayerResponse object. We won't need to query the database all the time for **frequent requests in a short period of time with the same playerId**.
### 5. Load Balancing and Hashing
* According to traffic we recieve, we may want to increase or decrease the running instances of wallet-service.
For this case, we would use load balancer such as NGINIX.
* To redicret requets to correct insances, we should think about hashing strategy (**consistent, round robin, rendezvous**).  
* To redirect users to correct instances, we should generate the hash with accurate instances. **(such as player name and instance name etc.)** 
With this strategy, we will use caches of the wallet-service instances in a efficient way even if we decrease or increase the number of instances.
### 6. Logging and metrics
* To track the status of the application, we can use logging tools such as NewRelic. 
* By using the metric registries we can recieve detailed information about caches etc.
### 7. Display Failed Transactions
* We can think of a structre that holds status of each transaction attempt. With this feature
when we use **GET /api/wallet/{walletNumber}/transaction-history** API, it will retrieve extra information 
  such as: 
   * status : "SUCCESS" OR "FAILED"
   * fail_reason : null OR "non_unique_transaction_id"
### 8. Transaction Hash Id Generation
* Currently we don't validate hash_id format for transaction. In future it can be obtained
from another microservice in **UUID format**. Then we will validate if it's unique or not.
### 9. Usage of BigDecimal instead of Double
* Using BigDecimal data type is safer way than performing operations with Double data type.
We can replace our Double data types (amount, balance etc.) with BigDecimal in the future.
### 10. Cross-Currency support
* Currently we don't support a transaction attempt which has different currency type in a target wallet.
To implement this feature, we can store currency rate information in another table and update it daily.
  When we recieve a cross-currency transaction attempt, we will be able to convert between currencies and perform the transaction.
### 11. Testing
* BDD testing tools such as **Cucumber** can be used.
* Creating Jenkins jobs which runs **Unit Tests** and **Cucumber Tests** when a new commit merged, 
will inform us about potential defects in the application.
### 11. Storing HTTP Requests and Responses 
* For each requets we recieve, we can store the request and response in our storage as Json Blob.
* It will be easier to keep track of past requests.
