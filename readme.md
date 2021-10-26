docker build -t nnpda_node:1.0 .

//start nodes
docker run --name device1 -d -p 8082:8080 -t nnpda_node:1.0
docker run --name device2 -d -p 8084:8080 -t nnpda_node:1.0 

java -jar /opt/app/app.jar --spring.config.location=file:local.yml
