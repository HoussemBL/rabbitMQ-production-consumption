package db

import java.sql._
import java.util.Properties
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

//class used to insert data in mysql DB
case class DAOwikiStats(num_edits: Long, num_edits_german: Long)(implicit SqlParams: Properties) {
  //:todo
  def insert(): Boolean = {


    var conn: Connection = null
    var stmt: Statement = null

    try {
      //database info
      val JDBC_DRIVER = SqlParams.getProperty("jdbc_driver")
      val DB_URL = SqlParams.getProperty("db_url")
      val USER = SqlParams.getProperty("mysql_user")
      val PASS = SqlParams.getProperty("mysql_pass")


      Class.forName(JDBC_DRIVER)
      conn = DriverManager.getConnection(DB_URL, USER, PASS)

      stmt = conn.createStatement

      val preparedStmt: PreparedStatement = conn.prepareStatement(DAOwikiStats.insertSql)

      preparedStmt.setString(1, DAOwikiStats.getcurrentTimestamp())
      preparedStmt.setLong(2, num_edits)
      preparedStmt.setLong(3, num_edits_german)
      preparedStmt.execute()

      preparedStmt.close()

      // cleanup
      stmt.close
      conn.close

      true

    } catch {
      case se: SQLException => {se.printStackTrace
      false}
      case e: Exception => {
        e.printStackTrace
        false
      }
    } finally {
      try {
        if (stmt != null) stmt.close
      } catch {
        case se2: SQLException => // nothing we can do
      }
      try {
        if (conn != null) conn.close
      } catch {
        case se: SQLException => se.printStackTrace
      } //end finally-try
    } //end try
  }
}


//companion object
object DAOwikiStats {

  val insertSql =
    """
      |insert into stats(timestamp_val,num_edits,num_edits_german)
      |values (?,?,?)
""".stripMargin

  //read properties of mysql specified in src.main.resources
  /* def readMYSQLProperties(): Properties =
     {
       val url = getClass.getResource("/db.properties")
       val source = Source.fromURL(url)
       val mysqlparameters = new Properties
       mysqlparameters.load(source.bufferedReader())
       mysqlparameters

     }
 */


  def getcurrentTimestamp() = {
    val currentDateTime = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val timestampString = currentDateTime.format(formatter)
    timestampString
  }


}



