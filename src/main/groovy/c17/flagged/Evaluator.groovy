package c17.flagged

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel

import org.jcsp.lang.CSProcess
import org.jcsp.lang.ChannelInput

class Evaluator implements CSProcess {  
	
  def ChannelInput inChannel
  
  void run() {
    while (true) {
      def v = inChannel.read()
      def ok = (v.c == (v.a +v.b))
      println "Evaluation: ${ok} from " + v.toString()
    }    
  }
}
