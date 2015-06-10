package c14

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel

import groovyx.gpars.csp.ALT
import groovyx.gpars.csp.ChannelOutputList
import org.jcsp.lang.AltingBarrier
import org.jcsp.lang.Barrier
import org.jcsp.lang.CSProcess
import org.jcsp.lang.ChannelInput
import org.jcsp.lang.ChannelOutput

class TargetController implements CSProcess {

  def ChannelOutput getActiveTargets
  def ChannelInput activatedTargets
  def ChannelInput receivePoint
  def ChannelOutputList sendPoint

  def Barrier setUpBarrier
  def Barrier goBarrier
  def AltingBarrier timeAndHitBarrier
  def int targets = 16

  void run() {
    def POINT = 1
    def BARRIER = 0
    def controllerAlt = new ALT ( [ timeAndHitBarrier, receivePoint] )

    setUpBarrier.sync()
    while (true) {
      getActiveTargets.write(1)
      def activeTargets = activatedTargets.read()  // a non-zero list of activated targets
      def runningTargets = activeTargets.size      // greater than zero
      def ChannelOutputList sendList = []
      for ( t in activeTargets) sendList.append(sendPoint[t])
      def active = true
      goBarrier.sync()
      while (active) {
        switch ( controllerAlt.priSelect() ) {
        case BARRIER:
          active = false
          break
        case POINT:
          def point = receivePoint.read()
          sendList.write(point)
          break
        } // end switch
      } // end while active
    } // end while true
  } // end run
}
