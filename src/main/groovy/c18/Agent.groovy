package c18

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel

import groovyx.gpars.csp.MobileAgent
import org.jcsp.lang.ChannelInput
import org.jcsp.lang.ChannelOutput

class Agent implements MobileAgent {

  def ChannelOutput toLocal
  def ChannelInput fromLocal

  def results = [ ]

  def connect ( List c ) {
    this.toLocal = c[0]
    this.fromLocal = c[1]
  }
  def disconnect () {
    toLocal = null
    fromLocal = null
  }
  void run() {
    toLocal.write (results)
    results = fromLocal.read()
  }
}
