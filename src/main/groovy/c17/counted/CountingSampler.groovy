package c17.counted

import groovyx.gpars.csp.ALT
import org.jcsp.lang.CSProcess
import org.jcsp.lang.ChannelInput
import org.jcsp.lang.ChannelOutput

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel

class CountingSampler implements CSProcess {

  def ChannelInput inChannel
  def ChannelOutput outChannel
  def ChannelInput sampleRequest
  def ChannelOutput countReturn

  void run() {
    def sampleAlt = new ALT ([sampleRequest, inChannel])
    def counter = 0

    while (true){
      counter = counter + 1
      def index = sampleAlt.priSelect()
      if (index == 0) {
        sampleRequest.read()
        def v = inChannel.read()
        outChannel.write(v)
        println "Sampled Value was ${v}"
        countReturn.write(counter)
      }
      else {
        outChannel.write(inChannel.read())
      } // end else
    } // end while
  } // end run
}
