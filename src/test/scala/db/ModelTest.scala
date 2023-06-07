package db

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.junit.runner.RunWith
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite
import org.specs2.runner.JUnitRunner

import java.io.File
import java.nio.file.{Files, Path, Paths}


@RunWith(classOf[JUnitRunner])
class ModelTest extends AnyFunSuite with BeforeAndAfter {

/*
  val pathJsonFiles = Paths.get("./resources/data/test/json_files/").toAbsolutePath.normalize().toString
  val pathCsvFilesActual = Paths.get("./resources/data/test/csv_files/").toAbsolutePath.normalize().toString
  val pathCsvFilesExpected = Paths.get("./resources/data/test/csv_files_expected/").toAbsolutePath.normalize().toString
  val pathParquetFilesActual = Paths.get("./resources/data/test/parquet_files/").toAbsolutePath.normalize().toString
  val pathParquetFilesExpected = Paths.get("./resources/data/test/parquet_files_expected/").toAbsolutePath.normalize().toString

  val checkpointReposJson = "/tmp/checkpointsTestJson"
  val checkpointReposCsv = "/tmp/checkpointsTestCsv"
  val checkpointReposParquet = "/tmp/checkpointsTestParquet"

  var spark: SparkSession = _
  var consumer: ConsumerStreamData = _
  var paramsApps: CodeParameters = _
  var consumerEnv1: ConsumerStreamData = _
  var consumerEnv2: ConsumerStreamData = _


  before {
    spark = Utils.getSpark()
    paramsApps = Utils.getAppParameters()
    consumerEnv1 = ConsumerStreamData(paramsApps.jsonPath, paramsApps.schemaJson, paramsApps.csvPath)
    consumerEnv2 = ConsumerStreamData(paramsApps.csvPath, paramsApps.schemaCSV, paramsApps.parquetPath)
    createDirectory(pathCsvFilesActual)
    createDirectory(pathParquetFilesActual)
  }


  //clean all repos used during the test
  after {
    deleteDirectory(checkpointReposJson)
    deleteDirectory(checkpointReposCsv)
    deleteDirectory(checkpointReposParquet)
    deleteDirectory(pathCsvFilesActual)
    deleteDirectory(pathParquetFilesActual)
    spark.stop()
  }


  test("test if we read json stream data correctly") {
    val actual = ConsumerStreamData.readStreamData(spark, pathJsonFiles, "json", paramsApps.schemaJson)
    assert(actual.schema.fields.length == 3, "the dataframe should contains 3 fields")
  }


  test("test flatten data and save it as csv") {
    //check first if directory that will be used to store csv data, is already created an empty
    val directory = new File(pathCsvFilesActual)
    assert((directory.exists() && directory.isDirectory()) == true, "the directory of csv should exist")
    assert(directory.listFiles().size == 0, "the directory of csv should be empty")

    //check the process of flatten data
    val actual = ConsumerStreamData.readStreamData(spark, pathJsonFiles, "json", paramsApps.schemaJson)
    val df_flatten = ConsumerStreamData.flattenDF(actual)
    assert(df_flatten.schema.fields.length == 5,"flatten data was done successfully")

    // read in memory stream data after flattening process and compared it to expected csv directory
    val streamingQuery = ConsumerStreamData.read_in_memory_StreamingDF(df_flatten, checkpointReposJson)
    streamingQuery.processAllAvailable()
    val dfCsv_actual = spark.sql("select * from rawStreamData")
    val dfCsv_expected = spark.read.schema(paramsApps.schemaCSV).csv(pathCsvFilesExpected)
    /** **************check content of csv ********* */
    assert(dfCsv_actual.schema.fields.length == dfCsv_expected.schema.fields.length, "the dataframe of the saved csv file should contains 5 fields")
    assert(dfCsv_actual.count() == dfCsv_expected.count(), "number of records in the the saved csv file should be 20")
  }


  test("test aggregation and persist stream data in parquet format") {
    //check first if directory that will be used to store parquet data, is already created an empty
    val directory = new File(pathParquetFilesActual)
    assert((directory.exists() && directory.isDirectory()) == true, "the directory of parquet should exist")
    assert(directory.listFiles().size == 0, "the directory of parquet should be empty")

    //read csv file as stream and aggregate it and store it as parquet
    val actualCsv = ConsumerStreamData.readStreamData(spark, pathCsvFilesExpected, "csv", paramsApps.schemaCSV)
    val dfStream = ConsumerStreamData.aggAndPersistData(actualCsv, pathParquetFilesActual, "parquet")
    dfStream.processAllAvailable()
    assert(directory.listFiles().size > 0, "the directory of parquet files should be not empty")


    /** **************check content of parquet ********* */
    val dfParquet_actual = spark.read.schema(paramsApps.schemaCSV).csv(pathParquetFilesActual)
    val dfParquet_expected = spark.read.schema(paramsApps.schemaCSV).csv(pathParquetFilesExpected)
    assert(dfParquet_actual.schema.fields.length == dfParquet_expected.schema.fields.length, "the dataframe of the saved parquet file should contains 5 fields")
    assert(dfParquet_actual.count() == dfParquet_expected.count(), "number of records in the the saved parquet file should be 5")
  }


  //used to clean all repos used during test
  def deleteDirectory(directoryPath: String) = {

    val path: Path = Paths.get(directoryPath)
    if (Files.exists(path)) {
      Files.walk(path)
        .sorted(java.util.Comparator.reverseOrder())
        .forEach(Files.delete)
    } else {
      println(s"Directory '$directoryPath' does not exist.")
    }
  }


  //code to create repose of data
  def createDirectory(directoryPath: String): Unit = {
    val path: Path = Paths.get(directoryPath)
    if (!Files.exists(path)) {
      Files.createDirectories(path)
      println(s"Directory '$directoryPath' created.")
    } else {
      println(s"Directory '$directoryPath' already exists.")
    }
  }


  //function used to persist "expected results"
  def persistData(df: DataFrame, format: String, pathFiles: String) = {
    df.write
      .format(format)
      .mode("overwrite")
      .save(pathFiles)
  }

  */

}
