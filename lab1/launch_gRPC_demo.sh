#!/bin/bash

if [[ "$1" == "server" ]];then
	echo "mvn exec:java -Dexec.mainClass=\"com.lab1.grpc.server.GrpcServer\""
	mvn exec:java -Dexec.mainClass="com.lab1.grpc.server.GrpcServer"
fi

if [[ "$1" == "client" ]];then
	echo "mvn exec:java -Dexec.mainClass=\"com.lab1.grpc.client.GrpcClient\""
	mvn exec:java -Dexec.mainClass="com.lab1.grpc.client.GrpcClient"
fi
