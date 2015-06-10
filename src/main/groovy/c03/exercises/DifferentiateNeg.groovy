package c03.exercises

import groovyx.gpars.csp.PAR
import groovyx.gpars.csp.plugAndPlay.GPCopy
import groovyx.gpars.csp.plugAndPlay.GPlus
import groovyx.gpars.csp.plugAndPlay.GPrefix
import org.jcsp.lang.CSProcess
import org.jcsp.lang.Channel
import org.jcsp.lang.ChannelInput
import org.jcsp.lang.ChannelOutput
import org.jcsp.lang.One2OneChannel

class DifferentiateNeg implements CSProcess {

  def ChannelInput  inChannel
  def ChannelOutput outChannel

  void run() {

    One2OneChannel a = Channel.createOne2One()
    One2OneChannel b = Channel.createOne2One()
    One2OneChannel c = Channel.createOne2One()
    One2OneChannel d = Channel.createOne2One()

    def differentiateList = [
      new GPrefix (
        prefixValue: 0,
        inChannel: b.in(),
        outChannel: c.out() ),
      new GPCopy (
        inChannel: inChannel,
        outChannel0: a.out(),
        outChannel1: b.out() ),
      new Negator (
        inChannel: c.in(),
        outChannel: d.out()),
      new GPlus  (
        inChannel0: a.in(),
        inChannel1: d.in(),
        outChannel: outChannel )
    ]

    new PAR ( differentiateList ).run()
  }
}
