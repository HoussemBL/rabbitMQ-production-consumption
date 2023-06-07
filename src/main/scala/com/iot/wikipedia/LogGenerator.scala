package com.iot.wikipedia

import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.sql.types._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import java.util.UUID
import scala.util.Random
import java.time.Instant
import com.google.gson.Gson


case class LogGenerator()


//companion object
object LogGenerator {


  def printLog(): String = {
    val gson = new Gson
    val wikiLog = generateRandomWiki()
    val jsonString = gson.toJson(wikiLog)

    jsonString
  }


  //generate random wiki
  def generateRandomWiki(): WikiLog = {
    val lang = getRandomValueFromEnumeration(LanguageEnumeration)
    val term = generateRandomString(Random.nextInt(10))
    val paragraph = generateRandomParagraph(Random.nextInt(4))
    WikiLog(lang.toString, term, paragraph)
  }

  def getRandomValueFromEnumeration(enum: Enumeration): enum.Value = {
    val values = enum.values.toList
    val randomIndex = Random.nextInt(values.length)
    values(randomIndex)
  }


  def generateRandomString(length: Int): String = {
    val chars = ('a' to 'z') ++ ('A' to 'Z') ++ ('0' to '9') // Define the characters to include in the random string
    val sb = new StringBuilder(length + 1)

    for (_ <- 1 to length + 1) {
      val randomIndex = Random.nextInt(chars.length)
      sb.append(chars(randomIndex))
    }

    sb.toString()
  }

  def generateRandomParagraph(sentenceCount: Int): String = {
    val sb = new StringBuilder()
    for (_ <- 1 to sentenceCount + 1) {
      val sentenceLength = Random.nextInt(10) + 5 // Random sentence length between 5 and 14 words
      val sentence = generateRandomSentence(sentenceLength)
      sb.append(sentence).append(" ")
    }

    sb.toString().trim()
  }

  def generateRandomSentence(wordCount: Int): String = {
    val words = List("Lorem", "ipsum", "dolor", "sit", "amet", "consectetur", "adipiscing", "elit", "sed", "do", "eiusmod", "tempor", "incididunt")
    val sb = new StringBuilder()

    for (_ <- 1 to wordCount) {
      val randomIndex = Random.nextInt(words.length)
      sb.append(words(randomIndex)).append(" ")
    }

    val sentence = sb.toString().trim()
    val capitalizedSentence = sentence.charAt(0).toUpper + sentence.substring(1) + "."
    capitalizedSentence
  }
}
