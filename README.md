# To dockerize

- mvn package OR mvn clean install
- docker build -t test-api-springboot:v1 .
- docker run -i --rm -p 3082:8082 test-api-springboot:v1