package Utils

import org.apache.spark.sql.SparkSession

import java.util.Properties
import scala.io.Source

object Utils{
  
  
//get the spark session
  def getSpark():SparkSession={

    val spark = SparkSession.builder()
      .appName("SparkRabbitMQ")
      .master("local[*]")
      .getOrCreate()
  spark    
  }


  //read properties of mysql specified in src.main.resources
  def readMYSQLProperties(): Properties = {
    val url = getClass.getResource("/db.properties")
    val source = Source.fromURL(url)
    val mysqlparameters = new Properties
    mysqlparameters.load(source.bufferedReader())
    mysqlparameters

  }

  
}