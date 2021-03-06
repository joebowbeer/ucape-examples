package c17.flagged

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel

import groovyx.gpars.csp.ALT
import org.jcsp.lang.CSProcess
import org.jcsp.lang.ChannelInput
import org.jcsp.lang.ChannelOutput

class Sampler implements CSProcess {  
	
  def ChannelInput inChannel
  def ChannelOutput outChannel
  def ChannelInput sampleRequest
  
  void run() {
    def sampleAlt = new ALT ([sampleRequest, inChannel])
	
    while (true){
      def index = sampleAlt.priSelect()
      if (index == 0) {
        sampleRequest.read()
        def v = inChannel.read()
        def fv = new FlaggedSystemData ( a: v.a, b:v.b, testFlag: true)
        outChannel.write(fv)
      }
      else {
        outChannel.write(inChannel.read())
      } // end else
    } // end while
  } // end run
}
