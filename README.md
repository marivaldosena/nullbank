<!-- Badges -->
[blue-academy-badge]: https://img.shields.io/static/v1?label=IBM&message=Blue%20Academy&color=blue
[java-badge]: https://img.shields.io/static/v1?label=Java&message=11&color=blue
[spring-boot-badge]:  https://img.shields.io/static/v1?label=Spring%20Boot&message=2.5&color=blue

<!-- Images -->

<!-- Links -->
[orange-talents-url]: https://www.zup.com.br/orange-talents
[java-url]: https://www.oracle.com/java/
[spring-url]: https://spring.io/


<!-- Content -->
# IBM Blue Academy | NullBank

[![IBM Blue Academy][blue-academy-badge]][orange-talents-url]
[![Java][java-badge]][java-url]
[![Spring][spring-boot-badge]][spring-url]


# Topics

- [Topics](#topics)
- [Curriculum](#curriculum)
- [NullBank](#nullbank)
- [Mission](#mission)
- [Activities](#activities)
- [Endpoints](#endpoints)
  - [Create Agency](#create-agency)
  - [New Client](#new-client)
  - [Open Account](#open-account)
  - [Deposit](#deposit)
  - [Cash Transfer](#cash-transfer)
  - [Transaction History](#transaction-history)
  - [List All Clients](#list-all-clients)
  - [Find Account](#find-account)
  - [Withdrawal](#withdrawal)
  - [List All Accounts](#list-all-accounts)

# Curriculum

The curriculum will touch on the following  subjects:

- Java
- Spring Boot
- Microservices
- Unit tests
- Rest APIs
- Among others

[Go to topics](#topics)

# NullBank

NullBank was born as a project for Gama Academy's Java Web Development course sponsored by IBM.

The object is to highlight the theoretical knowledge acquired in classes turning it into something substantial and practical.

Along with this project, it will be necessary to accomplish some activities to attest the acquired knowledge. The activities must strictly follow the statement.

[Go to topics](#topics)

# Mission

NullBank is the bank made by and for developers. It's possible to manage all your financial life with it.

It's possible to open current and saving accounts, do transfers, and much more!

[Go to topics](#topics)

# Activities

The features required for approval are:

- Registering clients
- Listing clients
- Cash transfer between accounts
- Transaction history

[Go to topics](#topics)

# Endpoints

Below are all the available endpoints.

## Create Agency

Endpoint to create an agency

Endoint:

```http

POST /agencies

```
Request:

```json
{
    "agencyName": "Central Agency",
    "agencyNumber": "0001"
}
```

Response:

```json
{
    "agencyName": "Central Agency",
    "agencyNumber": "0001"
}
```

[Go to topics](#topics)

## New Client

Endoint for creating a client.

Endpoint:

```http
POST /clients
```

Request:

```json
{
    "name": "Client 01",
    "address": "Address 01",
    "cpf": "606.344.610-95",
    "salary": "500.0"
}
```

Response:

```json
{
    "id": 2,
    "name": "Client 01"
}
```

[Go to topics](#topics)

## Open Account

Endpoint for opening an account after creating a client.

Endpoint:

```http
POST /accounts
```

Request:

```json
{
    "cpf": "606.344.610-95",
    "agencyNumber": "0001",
    "accountType": "CURRENT_ACCOUNT"
}
```

Response:

```json
{
    "agencyNumber": "0001",
    "agencyName": "Central Agency",
    "accountHolderName": "Client 01",
    "accountHolderId": 1,
    "accountNumber": "0000256938",
    "currentBalance": 0
}
```

[Go to topics](#topics)

## Deposit

Endpoint for depositing to an account.

Endpoint:

```http
POST /accounts/{ACCOUNT_NUMBER}/deposit
```

Request:

```json
{
    "amount": 1000
}
```

Response:

```json
{
    "agencyNumber": "0001",
    "agencyName": "Central Agency",
    "accountHolderName": "Client 01",
    "accountHolderId": 1,
    "accountNumber": "0000256938",
    "currentBalance": 1000.00
}
```

[Go to topics](#topics)

## Cash Transfer

Endpoint for transfering cash between accounts.

Endpoints:

```http
POST /accounts/{ORIGIN_ACCOUNT_NUMBER}/transfer-to/{DESTINATION_ACCOUNT_NUMBER}
```

Request:

```json
{
    "amount": 1000
}
```

Response:

```json
{
    "originAccountNumber": "0001664535",
    "destinationAccountNumber": "0001076192",
    "transferredAmount": 1000,
    "currentBalance": 0.00
}
```

[Go to topics](#topics)

## Transaction History

Endpoint for getting the transaction history.

Endpoint:

```http
GET /accounts/{{ACCOUNT_NUMBER}}/history
```

Response:

```json
{
    "accountNumber": "0001664535",
    "history": [
        {
            "amount": 1000.00,
            "description": "Deposit to account 0001664535"
        },
        {
            "amount": 1000.00,
            "description": "Transfer to account 0001076192"
        }
    ]
}
```

[Go to topics](#topics)

## List All Clients

Endpoint for listing clients.

Endpoint:

```http
GET /clients
```

Response:

```json
{
    "data": [
        {
            "id": 1,
            "name": "Client 01"
        }
    ]
}
```

[Go to topics](#topics)

## Find Account

Endpoint for finding account by account number.

Endpoint:

```http
GET /accounts/{ACCOUNT_NUMBER}
```

Response:

```json
{
    "agencyNumber": "0001",
    "agencyName": "Central Agency",
    "accountHolderName": "Client 01",
    "accountHolderId": 1,
    "accountNumber": "0001664535",
    "currentBalance": 0.00
}
```

[Go to topics](#topics)

## Withdrawal

Endpoint for withdrawal.

Endpoint:

```http
POST /accounts/{ACCOUNT_NUMBER}/withdraw
```

Request:

```json
{
    "amount": 1000
}
```

Response:

```json
{
    "agencyNumber": "0001",
    "agencyName": "Central Agency",
    "accountHolderName": "Client 01",
    "accountHolderId": 1,
    "accountNumber": "0001664535",
    "currentBalance": -1000.00
}
```

[Go to topics](#topics)

## List All Accounts

Endpoint for listing the available accounts.

Endpoint:

```http
GET /accounts
```

Response:

```json
{
    "data": [
        {
            "agencyNumber": "0001",
            "agencyName": "Central Agency",
            "accountHolderName": "Client 01",
            "accountHolderId": 1,
            "accountNumber": "0001076192",
            "currentBalance": 2000.00
        },
        {
            "agencyNumber": "0001",
            "agencyName": "Central Agency",
            "accountHolderName": "Client 01",
            "accountHolderId": 1,
            "accountNumber": "0001664535",
            "currentBalance": -1000.00
        }
    ]
}
```

[Go to topics](#topics)
