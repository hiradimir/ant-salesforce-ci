package com.hiradimir.sforce.ci.xml

import scala.xml
import scala.xml.Elem

object CoberturaXmlWriter extends XmlWriter {

  override val xmlHeader = """<?xml version="1.0"?>
<!DOCTYPE coverage SYSTEM "http://cobertura.sourceforge.net/xml/coverage-04.dtd">
"""

  trait toXml {
    def toXml: Elem
  }

  trait CoveredRate {
    val linesValid: Int
    val linesNotCovered: Int
    lazy val linesCovered = linesValid - linesNotCovered
    lazy val lineRate = linesCovered.toDouble / linesValid
  }
  trait NotCoveredValid {
    val child: Traversable[CoveredRate]
    lazy val linesNotCovered = child.map(_.linesNotCovered).sum
    lazy val linesValid = child.map(_.linesValid).sum
  }

  case class CoverageNotCoveredLine(line: Int) extends toXml {
    def toXml = <line number={ line.toString } hits="0" branch="false"/>
  }

  case class CoverageClass(name: String, fileName: String, linesNotCovered: Int, linesValid: Int, lines: Traversable[CoverageNotCoveredLine]) extends CoveredRate with toXml {
    def toXml = <class name={ name } filename={ fileName } line-rate={ lineRate.toString } branch-rate="0" complexity="0">
                  <methods/>
                  <lines>{
                    // dummy line coverage
                    (0 until (linesValid - linesNotCovered)).
                      map(x => {
                        <line number={ (-x).toString } hits="1" branch="false"/>
                      })
                  }{
                    lines.map(_.toXml)
                  }</lines>
                </class>
  }

  case class CoveragePackage(packageName: String, child: Traversable[CoverageClass]) extends NotCoveredValid with CoveredRate with toXml {
    def toXml = <package com.hiradimir.sforce.ci.xml={ packageName } line-rate={ lineRate.toString } branch-rate="0" complexity="0">
                  <classes>{ child.map(_.toXml) }</classes>
                </package>
  }

  case class Covarage(child: Traversable[CoveragePackage]) extends NotCoveredValid with CoveredRate with toXml {
    def toXml = <coverage line-rate={
      lineRate.toString
    } lines-covered={
      linesCovered.toString
    } lines-valid={
      linesValid.toString
    } branch-rate="0" branches-covered="0" branches-valid="0" complexity="0" version="1.9.4.1" timestamp={ System.currentTimeMillis.toString }>
                  <sources>
                    <source>--source</source>
                    <source>src/</source>
                  </sources>
                  {
                    child.map(_.toXml)
                  }
                </coverage>;
  }

  def saveCoverageResult(filePath: String, deployResult: com.sforce.soap.metadata.DeployResult) = {
//    val rtr = deployResult.getRunTestResult

    /*
    rtr.getCodeCoverage.map(cc => {
      println("codeCoverage", cc.getNumLocations, cc.getNumLocationsNotCovered, cc.getName, cc.getType);
      cc.getMethodInfo.foreach(mi => {
        println("methodInfo", mi.getLine, mi.getColumn, mi.getNumExecutions, mi.getTime);
      })
      cc.getLocationsNotCovered.foreach(lnc => {
       println("LocationsNotCovered", lnc.getLine, lnc.getTime);
      })
    })
    */

    //println(rtr.getCodeCoverage, rtr.getCodeCoverageWarnings, rtr.getFailures, rtr.getSuccesses)

//    val coverage = Covarage(rtr.getCodeCoverage.groupBy(_.getType).map(pkg => {
//      CoveragePackage(pkg._1, pkg._2.map(cls => {
//        val filename = {
//          pkg._1 match {
//            case "Class" => {
//              "classes/" + cls.getName + ".cls"
//            }
//            case "Trigger" => {
//              "triggers/" + cls.getName + ".trigger"
//            }
//          }
//        }
//        CoverageClass(cls.getName, filename, cls.getNumLocationsNotCovered, cls.getNumLocations, cls.getLocationsNotCovered.map(lnc => {
//          CoverageNotCoveredLine(lnc.getLine)
//        }))
//      }))
//    }))
//
//    writeXml(filePath, coverage.toXml)


  }
}