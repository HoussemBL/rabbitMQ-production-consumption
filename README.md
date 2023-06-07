


# Project description

As a prototype, a RabbtiqMQ instance is to be set up.

The following components are to be programmed:

● A producer that reads the sample data and emits it at random intervals
between 0-1 second. 

● In RabbitMQ Consumer, which reads this data from a queue, performs the following
aggregations, and stores the results:

  ○ Global number of edits per minute.

  ○ Number of edits of the German Wikipedia per minute.

<!--
## Command in terminal

`java -cp target/Europace_Project-0.0.1-SNAPSHOT.jar solution.RabbitMQPublisher`

`java -cp target/Europace_Project-0.0.1-SNAPSHOT.jar solution.RabbitMQConsumerSpark`
-->


## docker compose usage explanation
go to the project location in the terminal

run the following command 

`mvn clean package`

then run docker compose as follows

`docker-compose up -d`

you should get information as shown below indicating the creation of four docker containers:

```
Creating network "rabbitmq-production-consumption_spark_net" with the default driver

Creating rabbitmq-production-consumption_rabbitmq_1 ... done

Creating rabbitmq-production-consumption_mysql_1    ... done

Creating rabbitmq-production-consumption_rabbit-mqpublisher_1 ... done
C
reating rabbitmq-production-consumption_rabbitmq-consumer_1  ... done
```

rabbitMQ can be monitored via the following link "http://localhost:15672/"
username and password are "guest" and "guest"

check the following queue 


## check database content
write the following command

`docker exec -it rabbitmq-production-consumption_mysql_1 mysql -uroot -ppassword
`

then tape the following sql commands

`use wiki;`

then write the following command to check aggregation results

`select * from stats;`

