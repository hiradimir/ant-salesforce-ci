package com.yutagithub.sforce.ci.xml

import java.io._
import scala.xml._
import scala.xml.transform._

object PluralLabelCombine extends XmlWriter {

  def combine(name: String): Unit = {
    println(name)
    println(this)
    val cobjxmlns = """<CustomObject xmlns="http://soap.sforce.com/2006/04/metadata">"""

    val r = scala.io.Source.fromFile(name, "UTF-8")
    val a = r.getLines.toList.map {
      case x if x.contains(cobjxmlns) => {
        x.replace(cobjxmlns, "<CustomObject>")
      }
      case x => x;
    }
    r.close();
    withPrintWriter(new File(name), pw => {
      a.foreach(line => {
        pw.println(line)
      })
    })

    val sobject = XML.loadFile(name)
    if ((sobject \\ "customSettingsType").size == 0) {
      val xmlgen = <CustomObject xmlns="http://soap.sforce.com/2006/04/metadata">
                     {
                       sobject.child
                     }
                     <pluralLabel>{ (sobject \ "label").text + "s" }</pluralLabel>
                   </CustomObject>
      writeXml(name, xmlgen);
    }

  }

}