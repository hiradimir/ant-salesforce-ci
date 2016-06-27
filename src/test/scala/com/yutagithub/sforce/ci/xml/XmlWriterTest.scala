package com.hiradimir.sforce.ci.xml

import org.specs2._
import org.junit.runner._
import runner._
import scala.xml.Elem
import scala.xml.dtd.EMPTY

/**
 * This class must be inherited to allow a Specification to be executed as a JUnit test
 */
@RunWith(classOf[JUnitRunner])
class XmlWriterTest extends Specification {

  def is = "XmlWriter" ^
    "can write xml " ! e1  ^
    "can create with directory" ! e2 ^
    end

  val writer = new AnyRef with XmlWriter

  def e1 = writer.writeXml(new java.io.File("test.xml"), <test></test>)  must not be null

  def e2 = writer.writeXml(new java.io.File("target/test/test.xml"), <test></test>) must not be null


}