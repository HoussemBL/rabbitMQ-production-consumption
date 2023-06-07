package consumer


import db.DAOwikiStats
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.junit.runner.RunWith
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite
import org.specs2.runner.JUnitRunner

import java.io.File
import java.nio.file.{Files, Path, Paths}
import slick.jdbc.MySQLProfile.api._

import java.sql.{Connection, DriverManager, PreparedStatement}
import java.util.Properties

@RunWith(classOf[JUnitRunner])
class DAOwikiStatsTest extends AnyFunSuite with BeforeAndAfter {






  // Database connection and statement variables
  var connection: Connection = _
  var statement: PreparedStatement = _


  before {
    // Create a new connection and prepare the insert statement
//    Class.forName(driver)
    //connection = DriverManager.getConnection(url, username, password)
    //statement = connection.prepareStatement("INSERT INTO persons (id, name) VALUES (?, ?)")
  }


  //clean all repos used during the test
  after {

    // Close the statement and connection
  //  statement.close()
   // connection.close()
  }


  test("test if we read json stream data correctly") {
    implicit val propsSQL = new Properties
    propsSQL.setProperty("jdbc_driver", "org.h2.Driver")
    propsSQL.setProperty("db_url", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1")
    propsSQL.setProperty("mysql_user", "sa")
    propsSQL.setProperty("mysql_pass", "")
    val objectWiki = DAOwikiStats(22, 11)
    objectWiki.insert()
    assert(true)
  }

  test("wrong db info") {
    implicit val propsSQL = new Properties
    propsSQL.setProperty("jdbc_driver", "org.h2.Driver")
    propsSQL.setProperty("db_url", "jd-1")
    propsSQL.setProperty("mysql_user", "sa")
    propsSQL.setProperty("mysql_pass", "")
    val objectWiki = DAOwikiStats(22, 11)

    assertThrows[java.lang.ClassNotFoundException](objectWiki.insert())
  }

}
