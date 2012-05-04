package com.yutagithub.sforce.ci.xml

object CoberturaXmlWriter extends XmlWriter {

  override val xmlHeader = """<?xml version="1.0"?>
<!DOCTYPE coverage SYSTEM "http://cobertura.sourceforge.net/xml/coverage-04.dtd">
"""

  def saveTestResult(filePath: String, deployResult: com.sforce.soap.metadata.DeployResult) = {
    
  }
}