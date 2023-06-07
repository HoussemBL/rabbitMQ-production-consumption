


# Project description

You can run the project using docker-compose. This will create the following containers


● A RabbtiqMQ instance

●  A RabbitMQ producer that reads the sample data and emits it at random intervals
between 0-1 second. 

● A RabbitMQ Consumer, which reads this data from a queue, performs the following
aggregations, and stores the results:

  ○ Global number of edits per minute.

  ○ Number of edits of the German Wikipedia per minute.

● A Mysql database instance where we store aggregations results made by the RabbitMQ Consumer

<!--
## Command in terminal

`java -cp target/Europace_Project-0.0.1-SNAPSHOT.jar solution.RabbitMQPublisher`

`java -cp target/Europace_Project-0.0.1-SNAPSHOT.jar solution.RabbitMQConsumerSpark`
-->


## How to run the project
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

