package c21

import org.jcsp.lang.CSProcess
import org.jcsp.lang.ChannelInput
import org.jcsp.net2.NetChannel

class Type1Process implements CSProcess, Serializable {

  def toGatherer
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
      def Type1 d = inChannel.read()
      d.modify(nodeId)
      toGathererChannel.write(d)
    }
  }
}
