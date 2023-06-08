package solution

import Utils.Utils
import com.iot.wikipedia.WikiLog
import com.rabbitmq.RabbitMQReceiver
import db.DAOwikiStats
import org.apache.spark.streaming.{Seconds, StreamingContext}
import io.circe.parser._
import io.circe.generic.auto._
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import org.slf4j.LoggerFactory


object RabbitMQConsumerSpark {
  private val logger = LoggerFactory.getLogger(getClass)

  def main(args: Array[String]): Unit = {
    implicit val spark = Utils.getSpark()

    while (true) {
      val streamingContext = new StreamingContext(spark.sparkContext, Seconds(60))
      val messagesStream = streamingContext.receiverStream(new RabbitMQReceiver())
      messagesStream.foreachRDD { rdd =>
        processData(rdd)
      }

      streamingContext.start()
      streamingContext.awaitTerminationOrTimeout(6000)
    }
  }




  //:each batch of data, convert it to Dataset and aggregate it and finally store results in mysql db
  def processData(rdd: RDD[String])(implicit spark: SparkSession): Unit = {
    implicit  val sqlProperties= Utils.readPropertiesFile("/db.properties")
    import spark.implicits._
    val wikiRDD = castDataToWiki(rdd)
    val ds = wikiRDD.toDS()

    //query dataset
    val germanEdits = ds.filter($"language" === "German").count()
    val edits = ds.count()
    logger.debug(s" number of edits in wiki is $edits")
    val objectWiki = DAOwikiStats(edits, germanEdits)

    //insert data in db
    objectWiki .insert()
  }


  //convert RDD[String] to  RDD[WIkiLog]
  def castDataToWiki(rdd: RDD[String]): RDD[WikiLog] = {
    rdd.flatMap(jsonString => {
      decode[WikiLog](jsonString) match {
        case Right(wiki) => Some(wiki)
        case Left(_) => None
      }
    })
  }

}
