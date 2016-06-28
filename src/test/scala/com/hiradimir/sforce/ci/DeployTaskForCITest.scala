package com.hiradimir.sforce.ci

import org.scalatest._
import Matchers._

class XmlWriterTest extends FlatSpec {
  
  "DeployTaskForCI" should "can create with default constructor" in {

    val sampleInstance = new DeployTaskForCI
    
    assert(sampleInstance !== null)
    
  }

}
