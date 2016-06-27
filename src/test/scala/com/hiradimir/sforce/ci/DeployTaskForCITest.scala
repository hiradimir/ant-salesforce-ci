package com.hiradimir.sforce.ci

import org.specs2._

import org.junit.runner._
import runner._
import org.junit.internal.runners.JUnit4ClassRunner

/**
 * This class must be inherited to allow a Specification to be executed as a JUnit test
 */
@RunWith(classOf[JUnit4ClassRunner])
class DeployTaskForCITest extends Specification {
  def is = "DeployTaskForCI" ^
    "can create " ! e1 ^
    "getRunTests is empty" ! e2 ^
    end

  val sampleInstance = new DeployTaskForCI

  def e1 = sampleInstance must not be null

  def e2 = sampleInstance.getRunTests must have size (0)

}