package c03.exercises

import groovyx.gpars.csp.PAR
import groovyx.gpars.csp.plugAndPlay.GPlus
import groovyx.gpars.csp.plugAndPlay.GTail
import org.jcsp.lang.CSProcess
import org.jcsp.lang.Channel
import org.jcsp.lang.ChannelInput
import org.jcsp.lang.ChannelOutput
import org.jcsp.lang.One2OneChannel

class GSPairsA implements CSProcess {

  def ChannelOutput outChannel
  def ChannelInput  inChannel

  void run() {

    One2OneChannel a = Channel.createOne2One()
    One2OneChannel b = Channel.createOne2One()
    One2OneChannel c = Channel.createOne2One()

    def pairsList  = [
      new GPlus   (
        outChannel: outChannel,
        inChannel0: a.in(),
        inChannel1: c.in() ),
      new GSCopy  (
        inChannel: inChannel,
        outChannel0: a.out(),
        outChannel1: b.out() ),
      new GTail   (
        inChannel: b.in(),
        outChannel: c.out() )
    ]

    new PAR ( pairsList ).run()
  }
}
