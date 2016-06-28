package com.hiradimir.sforce.ci

import org.scalatest._
import Matchers._

class DeployForCITaskSpec extends FlatSpec {
  
  "DeployForCITask" should "can create with default constructor" in {

    val sampleInstance = new DeployForCITask
    
    assert(sampleInstance !== null)
    
  }

}
