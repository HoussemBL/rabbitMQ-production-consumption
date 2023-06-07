package solution

import com.iot.wikipedia.LogGenerator
import com.rabbitmq.client.{ ConnectionFactory}



object RabbitMQPublisher {



  def main(args: Array[String]): Unit = {


    val factory = new ConnectionFactory()
    //factory.setHost("localhost")
    factory.setHost("rabbitmq")
    factory.setPort(5672)
    factory.setUsername("guest")
    factory.setPassword("guest")


    while (true) {
      try {
        val connection = factory.newConnection()
        val channel = connection.createChannel()

        val exchangeName = "my-exchange"
        val routingKey = "my-queue"

        // send each second randomly wiki data
        val input = LogGenerator.printLog()
        channel.basicPublish(exchangeName, routingKey, null, input.getBytes())
        Thread.sleep(1000)
        channel.close()
        connection.close()
      }
      catch {
        case ex: Exception =>
          // Exception handling
          println(s"Exception occurred: ${ex.getMessage}")
      }


    }


  }
}
