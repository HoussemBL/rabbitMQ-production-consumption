package iot.wikipedia

import com.iot.wikipedia.{LanguageEnumeration, LogGenerator}
import org.apache.spark.sql.SparkSession
import org.junit.runner.RunWith
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite
import org.specs2.runner.JUnitRunner


@RunWith(classOf[JUnitRunner])
class LogGeneratorTest extends AnyFunSuite with BeforeAndAfter {


  test("test generation thema ") {
    val themaEmpty= LogGenerator.generateRandomString(0)
    assert(themaEmpty.size>0, "thema should not be empty")
  }

  test("test generation description") {
    val paragraphEmpty = LogGenerator.generateRandomParagraph(0)
    assert(paragraphEmpty.size > 0, "paragraph should not be empty")
  }

  test("test generation wiki") {
    val wikiEntry =LogGenerator.generateRandomWiki()
    val possibelValues = LanguageEnumeration.values.map{lang=> lang.toString}
    assert(possibelValues.contains(wikiEntry.language), "all possible values of language should be english,italian,french,german")

    assert(!wikiEntry.thema.isEmpty, "thema should not be empty")

    assert(!wikiEntry.description.isEmpty, "description should not be empty")
  }



}
