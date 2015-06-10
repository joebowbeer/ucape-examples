package c12.fork

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel

import groovyx.gpars.csp.ALT
import groovyx.gpars.csp.ChannelInputList
import org.jcsp.lang.CSProcess

class LazyButler implements CSProcess {

  def ChannelInputList enters
  def ChannelInputList exits

  void run() {
    def seats = enters.size()
    def allChans = []

    for ( i in 0 ..< seats ) { allChans << exits[i]  }
    for ( i in 0 ..< seats ) { allChans << enters[i] }

    def eitherAlt = new ALT ( allChans )

    while (true) {
      def i = eitherAlt.select()
      allChans[i].read()
    } // end while
  } //end run
} // end class
