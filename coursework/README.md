## Lab 1 - gRPC

### Distributed gRPC enabled matrix multiplication 
- only supports square matrices
- uses deadline scaling as the client side loadbalancing algorithm


### Install
```
mvn clean install
```

### Launch the server
```
mvn exec:java@server
```

### Launch the client
```
mvn exec:java@client
```
