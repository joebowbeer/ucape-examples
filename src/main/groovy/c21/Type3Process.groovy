package c21

import org.jcsp.lang.CSProcess
import org.jcsp.lang.ChannelInput
import org.jcsp.net2.NetChannel
import org.jcsp.net2.NetChannelLocation

class Type3Process implements CSProcess, Serializable {

  def NetChannelLocation toGatherer
  def ChannelInput inChannel
  def int nodeId

  def connect (List l) {
    inChannel = l[0]
    nodeId = l[1]
    toGatherer = l[2]
  }

  def disconnect () {
    inChannel = null
  }

  void run() {
    def toGathererChannel = NetChannel.any2net(toGatherer)
    while (true) {
      def Type3 d = inChannel.read()
      //println "T3: $d read data into Type process"
      d.modify(nodeId)
      //println "T3: $d sending modified data to Gatherer"
      toGathererChannel.write(d)
      //println "T3: $d have sent modified data to Gatherer"
    }
  }
}
