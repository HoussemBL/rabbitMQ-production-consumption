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

object RabbitMQConsumerSpark {


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

  //:todo documentation
  def processData(rdd: RDD[String])(implicit spark: SparkSession): Unit = {
    implicit  val sqlProperties= Utils.readMYSQLProperties()
    import spark.implicits._
    val wikiRDD = rdd.flatMap(jsonString => {
      decode[WikiLog](jsonString) match {
        case Right(wiki) => Some(wiki)
        case Left(_) => None
      }
    })

    val ds = wikiRDD.toDS()

    val germanEdits = ds.filter($"language" === "German").count()
    val edits = ds.count()

    val objectWiki = DAOwikiStats(edits, germanEdits)
    objectWiki .insert()
    println(s"Received message")
  }

}
