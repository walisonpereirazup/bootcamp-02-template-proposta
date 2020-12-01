# Credicard Zero - Proposta

## Usage
The following will demonstrate the usage of the HTTP endpoints and example responses.

- Create a new proposal
```
$> curl localhost:8080/proposals -i -XPOST \
  -H 'Content-Type: application/json' \
  -d '{"document":"298.060.780-04","email":"john.doe@gmail.com","name":"John Doe","address":"123 Fake St.","salary":9200.50}'

HTTP/1.1 201
Location: http://localhost:8080/proposals/2651a9ec-0a29-4e14-bc10-9384ad523caa
Content-Length: 0
Date: Tue, 01 Dec 2020 17:01:22 GMT

``` 
