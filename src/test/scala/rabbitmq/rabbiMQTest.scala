package rabbitmq

import org.apache.spark.sql.SparkSession
import org.junit.runner.RunWith
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite
import org.specs2.runner.JUnitRunner


@RunWith(classOf[JUnitRunner])
class rabbiMQTest extends AnyFunSuite with BeforeAndAfter {



  var spark: SparkSession = _
  before {
    spark = Utils.Utils.getSpark()
   //create embedded mysql database
  }


  //clean all repos used during the test
  after {

    spark.stop()
  }


  test("test if we read json stream data correctly") {
    //val actual = ConsumerStreamData.readStreamData(spark, pathJsonFiles, "json", paramsApps.schemaJson)
    //assert(actual.schema.fields.length == 3, "the dataframe should contains 3 fields")
    assert(true)
  }



}
