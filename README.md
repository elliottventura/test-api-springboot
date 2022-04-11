# To dockerize

- mvn package OR mvn clean install
- docker build -t test-api-springboot:v1 .
- docker run -i --rm -p 3082:8082 test-api-springboot:v1

# To allow communication beetween containers : 

## Solution 1 (bad solution)
Find the internal ip of the container to access : docker inspect <container_id> | grep IPAddress
Use this ip instead of localhost in the url. The port is the port of the api (and not the container exposed port)
Here we use the bridge. Each container has its ip and we use the ip to communicate.

## Solution 2
Create your own network in which your applications will run.

- docker network create test-api-net
- docker run -i --rm --net test-api-net -p 3082:8082 --name test-api-springboot test-api-springboot:v1

- Use docker network rm test-api-net to delete the network


# Summary
mvn clean install
docker build -t test-api-springboot:v1 .
docker run -i --rm --net test-api-net -p 3082:8082 --name test-api-springboot test-api-springboot:v1