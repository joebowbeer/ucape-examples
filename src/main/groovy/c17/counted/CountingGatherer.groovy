package c17.counted

import groovyx.gpars.csp.ALT
import org.jcsp.lang.CSProcess
import org.jcsp.lang.ChannelInput
import org.jcsp.lang.ChannelOutput

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel

class CountingGatherer implements CSProcess {

  def ChannelInput inChannel
  def ChannelOutput outChannel
  def ChannelOutput gatheredData
  def ChannelInput countInput

  void run(){
    def counter = 0
    def required = 0
    def gatherAlt = new ALT([countInput, inChannel])

    while (true){
      def index = gatherAlt.priSelect()
      if (index == 0) {
        required = countInput.read()
      }
      else {
        def v = inChannel.read()
        counter = counter + 1
        outChannel.write(v)
        if (counter == required) {
          println "Gathered value was ${v}"
          def cv = new CountedData ( counter: counter, value: v)
          gatheredData.write(cv)
        } // end if
      } // end else
    } // end while
  } // end run
}
