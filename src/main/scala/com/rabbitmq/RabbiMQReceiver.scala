package com.rabbitmq

import com.rabbitmq.client.AMQP.BasicProperties
import com.rabbitmq.client._
import org.apache.spark.storage.StorageLevel

class RabbitMQReceiver() extends org.apache.spark.streaming.receiver.Receiver[String](StorageLevel.MEMORY_AND_DISK_2) {


  var connection: Connection = _
  var channel: Channel = _

  override def onStart(): Unit = {
    val factory = new ConnectionFactory()
    //factory.setHost("localhost")
    factory.setHost("rabbitmq")
    factory.setPort(5672)
    factory.setUsername("guest")
    factory.setPassword("guest")

    connection = factory.newConnection()
    channel = connection.createChannel()


    val consumer = new DefaultConsumer(channel) {
      override def handleDelivery(consumerTag: String, envelope: Envelope, properties: BasicProperties, body: Array[Byte]): Unit = {
        val message = new String(body, "UTF-8")
        store(message)
      }
    }

    channel.basicConsume("my-queue", true, consumer)
  }

  override def onStop(): Unit = {
    // Close the channel and connection
    if (channel != null) {
      channel.close()
    }
    if (connection != null) {
      connection.close()
    }
  }
}