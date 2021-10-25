docker build -t nnpda_node:1.0 .

docker run -d -p 8082:8080 -t nnpda_node:1.0
docker run -d -p 8083:8080 -t nnpda_node:1.0

java -jar /opt/app/app.jar --spring.config.location=file:local.yml
