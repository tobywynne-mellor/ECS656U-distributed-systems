# lab2 REST 
## Summary
This maven project is an implementation of a simple REST API using the SpringBoot framework.
I'm only using GET requests to interact with the API. In a non-demo situation I would be using more semantically correct HTTP methods.


Install
```
mvn clean install
```

Launch the server
```
mvn spring-boot:run
```

Interact with the API via get requests on localhost:8080 in the browser
```
# add an item to the inventory
http://localhost:8080/addItem?item=plant

# set quantity to the stock level
http://localhost:8080/setStock?item=plant&stockLevel=10

# add quantity to the stock level
http://localhost:8080/addStock?item=plant&numItem=1

# remove quantity from the stock level
http://localhost:8080/removeStock?item=plant&numItem=1

# list current inventory
http://localhost:8080/listStock

```
