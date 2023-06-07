package consumer


import db.DAOwikiStats
import org.h2.jdbc.JdbcSQLInvalidAuthorizationSpecException
import org.junit.runner.RunWith
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite
import org.specs2.runner.JUnitRunner

import java.sql.{Connection, DriverManager, Statement}
import java.util.Properties

@RunWith(classOf[JUnitRunner])
class DAOwikiStatsTest extends AnyFunSuite with BeforeAndAfter {

  // Database connection and statement variables
  var connection: Connection = _
  var statement: Statement = _


  before {
    // Create a new connection and prepare the insert statement
    Class.forName("org.h2.Driver")
    connection = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "")
    statement = connection.createStatement()

    // Create the "person" table
    val createTableQuery =
      """
        |CREATE TABLE if not exists stats  (
        |  id INT AUTO_INCREMENT PRIMARY KEY,
        |  timestamp_val VARCHAR(255),
        |  num_edits BIGINT ,
        |  num_edits_german BIGINT
        |)
        |""".stripMargin

    statement.execute(createTableQuery)
  }


  //clean all repos used during the test
  after {


    // Create the "person" table
    val dropTableQuery =
      """
        |drop TABLE  stats
        |""".stripMargin

    statement.execute(dropTableQuery)
    // Close the statement and connection
    statement.close()
    connection.close()
  }


  test("test if we read json stream data correctly") {
    implicit val propsSQL = new Properties
    propsSQL.setProperty("jdbc_driver", "org.h2.Driver")
    propsSQL.setProperty("db_url", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1")
    propsSQL.setProperty("mysql_user", "sa")
    propsSQL.setProperty("mysql_pass", "")
    val objectWiki = DAOwikiStats(22, 11)
    val operationSucceeed=objectWiki.insert()
    assert(operationSucceeed == true)
  }


  test("wrong db  username") {
    implicit val propsSQL = new Properties
    propsSQL.setProperty("jdbc_driver", "org.h2.Driver")
    propsSQL.setProperty("db_url", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-")
    propsSQL.setProperty("mysql_user", "sadsa")
    propsSQL.setProperty("mysql_pass", "")
    val objectWiki = DAOwikiStats(22, 11)

   val operationSucceeed= objectWiki.insert()
    //assertThrows[JdbcSQLInvalidAuthorizationSpecException](objectWiki.insert())
    assert(operationSucceeed== false)
  }

  test("wrong db  password") {
    implicit val propsSQL = new Properties
    propsSQL.setProperty("jdbc_driver", "org.h2.Driver")
    propsSQL.setProperty("db_url", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-")
    propsSQL.setProperty("mysql_user", "sa")
    propsSQL.setProperty("mysql_pass", "sa")
    val objectWiki = DAOwikiStats(22, 11)

    val operationSucceeed = objectWiki.insert()
    //assertThrows[JdbcSQLInvalidAuthorizationSpecException](objectWiki.insert())
    assert(operationSucceeed == false)
  }

}
