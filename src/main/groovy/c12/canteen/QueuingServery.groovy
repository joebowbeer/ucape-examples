package c12.canteen

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel

import groovyx.gpars.csp.PAR
import groovyx.gpars.csp.plugAndPlay.GConsole
import org.jcsp.lang.CSProcess
import org.jcsp.lang.Channel
import org.jcsp.lang.ChannelInput
import org.jcsp.lang.ChannelOutput

class QueuingServery implements CSProcess{

  def ChannelInput service
  def ChannelOutput deliver
  def ChannelInput supply

  void run() {
    def console = Channel.one2one()

    def servery = new QueuingCanteen (
      service: service,
      deliver: deliver,
      supply: supply,
      toConsole: console.out() )

    def serveryConsole = new GConsole (
      toConsole: console.in(),
      frameLabel: "Queuing Servery")

    new PAR([servery,serveryConsole]).run()
  }
}
