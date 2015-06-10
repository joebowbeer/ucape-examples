package c04

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel

import groovyx.gpars.csp.PAR
import groovyx.gpars.csp.plugAndPlay.GPCopy
import groovyx.gpars.csp.plugAndPlay.GPrefix
import groovyx.gpars.csp.plugAndPlay.GSuccessor
import org.jcsp.lang.CSProcess
import org.jcsp.lang.Channel
import org.jcsp.lang.ChannelInput
import org.jcsp.lang.ChannelOutput

class ResetNumbers implements CSProcess {

  def int initialValue = 0
  def ChannelInput resetChannel
  def ChannelOutput outChannel

  void run() {

    def a = Channel.one2one ()
    def r = Channel.one2one ()
    def b = Channel.one2one()
    def c = Channel.one2one()

    def testList = [
      new GPrefix(
        prefixValue: initialValue,
        outChannel: r.out(),
        inChannel: c.in()),
      new Reset(
        inChannel: r.in(),
        resetChannel: resetChannel,
        outChannel: a.out()),
      new GPCopy(
        inChannel: a.in(),
        outChannel0: outChannel,
        outChannel1: b.out()),
      new GSuccessor(
        inChannel: b.in(),
        outChannel: c.out())
    ]
    new PAR(testList).run()
  }
}
