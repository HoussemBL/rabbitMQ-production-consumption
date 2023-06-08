package Utils

import com.rabbitmq.client.ConnectionFactory
import org.apache.spark.sql.SparkSession

import java.util.Properties
import scala.io.Source
import scala.util.Try

object Utils {


  //get the spark session
  def getSpark(): SparkSession = {

    val spark = SparkSession.builder()
      .appName("SparkRabbitMQ")
      .master("local[*]")
      .getOrCreate()
    spark
  }


  //read properties of socket specified in src.main.resources
  def readPropertiesFile(propFile: String): Properties = {
    val prop = getClass.getResource(propFile)
    val source = Source.fromURL(prop)
    val rabbitMQparams = new Properties
    rabbitMQparams.load(source.bufferedReader())

    rabbitMQparams

  }

  //create a factory conenction based on properits in rabbitmq.properties file
  def createFactoryConnection(): ConnectionFactory = {

    val rabbitMqParams = Utils.readPropertiesFile("/rabbitmq.properties")

    val rabbit_host = rabbitMqParams.getProperty("host")
    val rabbit_port = rabbitMqParams.getProperty("port")
    val rabbit_user = rabbitMqParams.getProperty("username")
    val rabbit_pass = rabbitMqParams.getProperty("password")


    val factory = new ConnectionFactory()
    factory.setHost(rabbit_host)
    factory.setPort(Try(rabbit_port.toInt).getOrElse(0))
    factory.setUsername(rabbit_user)
    factory.setPassword(rabbit_pass)

    factory
  }

}