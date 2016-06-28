package com.hiradimir.sforce.ci.xml

import org.scalatest._
import Matchers._

class XmlWriterSpec extends FlatSpec {
  
  "XmlWriter" should "can write xml create with directory" in {

    val writer = new AnyRef with XmlWriter
    
    writer.writeXml(new java.io.File("target/test/test.xml"), <test></test>) should not equal null;
    
  }

}
