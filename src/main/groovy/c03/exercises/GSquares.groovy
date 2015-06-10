package c03.exercises

import groovyx.gpars.csp.PAR
import groovyx.gpars.csp.plugAndPlay.GIntegrate
import groovyx.gpars.csp.plugAndPlay.GNumbers
import org.jcsp.lang.CSProcess
import org.jcsp.lang.Channel
import org.jcsp.lang.ChannelOutput
import org.jcsp.lang.One2OneChannel

class GSquares implements CSProcess {

  def ChannelOutput outChannel

  void run () {

    One2OneChannel N2I = Channel.createOne2One()
    One2OneChannel I2P = Channel.createOne2One()

    def testList =  [
      new GNumbers   (
        outChannel: N2I.out() ),
      new GIntegrate (
        inChannel: N2I.in(),
        outChannel: I2P.out() ),
      new GSPairsB (
        inChannel: I2P.in(),
        outChannel: outChannel )
      // you will need to modify this twice
      //first modification is to insert a constructor for GSPairsA
      // then run the network using TestGSCopy
      //second modification replace the constructor for GSPairsA with GSPairsB
      // then run the network again using TestGSCopy
      // you will then be able to compare the behaviour and also to
      // explain why this happens!
    ]

    new PAR ( testList ).run()
  }
}
