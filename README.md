# graylog-elastic-demo
A demo for querying elasticsearch which is running on docker container and being used by graylog log system.
# Prerequisites
Install graylog using docker as mentioned in:
https://docs.graylog.org/en/4.0/pages/installation/docker.html

basically you need to run the following commands:
1. docker run --name mongo -d mongo:4.2
2. docker run --name elasticsearch \
    -e "http.host=0.0.0.0" \
    -e "discovery.type=single-node" \
    -e "ES_JAVA_OPTS=-Xms512m -Xmx512m" \
    -d docker.elastic.co/elasticsearch/elasticsearch-oss:7.10.0
3. docker run --name graylog --link mongo --link elasticsearch \
    -p 9000:9000 -p 12201:12201 -p 1514:1514 \
    -e GRAYLOG_HTTP_EXTERNAL_URI="http://127.0.0.1:9000/" \
    -d graylog/graylog:4.0

# Note
Since I wanted to define an "Raw/AMQP Input" within Graylog web interface to automatically read rabbitmq queue (better say act as a consumer). So my installation include rabbitmq container as well: (note that while running graylog I have included --link rabbitmq)

3. docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management
4. docker run --name graylog --link mongo --link elasticsearch --link rabbitmq \
    -p 9000:9000 -p 12201:12201 -p 1514:1514 \
    -e GRAYLOG_HTTP_EXTERNAL_URI="http://127.0.0.1:9000/" \
    -d graylog/graylog:4.0


# References
1. Using elastic rest apis:
https://www.elastic.co/guide/en/elasticsearch/reference/current/rest-apis.html





