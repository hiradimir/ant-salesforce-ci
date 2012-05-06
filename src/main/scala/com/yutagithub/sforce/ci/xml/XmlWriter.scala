package com.yutagithub.sforce.ci.xml

import java.io.File
import java.io.PrintWriter
import scala.xml.Elem

trait XmlWriter {
  val xmlHeader = """<?xml version="1.0" encoding="UTF-8"?>"""

  import java.io.{ File, PrintWriter }
  def writeXml(filePath: String, elem: Elem): Unit = writeXml(new File(filePath), elem)
  def writeXml(file: File, elem: Elem): Unit = {
    withPrintWriter(
      file,
      pw => {
        pw.println(xmlHeader)
        pw.println((new scala.xml.PrettyPrinter(120, 4)).format(elem))
      })
  }
  def withPrintWriter(file: File, op: PrintWriter => Unit) {
    if(file.getParentFile != null && !file.getParentFile.exists){
      file.getParentFile.mkdirs
    }
    val writer = new PrintWriter(file, "UTF-8")
    try {
      op(writer)
    } catch{
      case ex => throw ex;
    }finally {
      writer.close()
    }
  }
}