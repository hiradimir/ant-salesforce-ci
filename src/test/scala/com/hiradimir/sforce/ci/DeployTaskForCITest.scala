package com.hiradimir.sforce.ci

import org.specs2._

import runner._

/**
 * This class must be inherited to allow a Specification to be executed as a JUnit test
 */
class DeployTaskForCITest extends Specification {
  def is = "DeployTaskForCI" ^
    "can create " ! e1 ^
    "getRunTests is empty" ! e2 ^
    end

  val sampleInstance = new DeployTaskForCI

  def e1 = sampleInstance must not be null

  def e2 = sampleInstance.getRunTests must have size (0)

}