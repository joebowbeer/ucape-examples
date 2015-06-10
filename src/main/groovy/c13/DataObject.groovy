package c13

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel

import groovyx.gpars.csp.JCSPCopy

class DataObject implements Serializable, JCSPCopy {

  def int pid
  def int location
  def int value

  def DataObject copy () {
    def dObj = new DataObject ( pid: this.pid,
      location: this.location,
      value: this.value
    )
    return dObj
  }

  def String toString() {
    def s = "[DataObject: pid:${pid}, location:${location}, value:${value}]"
    return s
  }
}
