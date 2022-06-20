package utils

import java.io.{PrintWriter, StringWriter}

object Utils {
  def getStackTraceAsString(t: Throwable) = {
    val sw = new StringWriter
    t.printStackTrace(new PrintWriter(sw))
    sw.toString
  }
}
