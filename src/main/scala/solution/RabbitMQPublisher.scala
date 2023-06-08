package solution

import com.iot.wikipedia.LogGenerator
import com.rabbitmq.client.{ ConnectionFactory}



object RabbitMQPublisher {



  def main(args: Array[String]): Unit = {


    val factory = Utils.Utils.createFactoryConnection()


    while (true) {
      try {
        val connection = factory.newConnection()
        val channel = connection.createChannel()

        //create exhcnage and queue
        val exchangeName = "my-exchange"
        val routingKey = "my-queue"
        val exchangeType = "direct"
        channel.exchangeDeclare(exchangeName, exchangeType, true)
        channel.queueDeclare(routingKey, true, false, false, null)
        channel.queueBind(routingKey, exchangeName, routingKey)


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

