package c13

import org.jcsp.lang.CSProcess
import org.jcsp.lang.ChannelInput
import org.jcsp.lang.ChannelOutput

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel

class ReadClerk implements CSProcess {
	
  def ChannelInput cin
  def ChannelOutput cout
  def CrewMap data
  
  void run () {
    println "ReadClerk has started "
    while (true) {
      def d = new DataObject()
      d = cin.read()
      d.value = data.get ( d.location )
      println "RC: Reader ${d.pid} has read ${d.value} from ${d.location}"
      cout.write(d)
    }
  }
}
  