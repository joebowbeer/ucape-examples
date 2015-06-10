package c12.canteen;

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel

import groovyx.gpars.csp.PAR
import groovyx.gpars.csp.plugAndPlay.GConsole
import org.jcsp.lang.CSProcess
import org.jcsp.lang.Channel
import org.jcsp.lang.ChannelOutput

class Kitchen implements CSProcess {

  def ChannelOutput supply

  void run() {

    def console = Channel.one2one()
    def chef = new Chef ( supply: supply,
      toConsole: console.out() )
    def chefConsole = new GConsole ( toConsole: console.in(),
      frameLabel: "Chef")
    new PAR([chef, chefConsole]).run()
  }
}
