package c09.exercises

import groovyx.gpars.csp.ALT
import groovyx.gpars.csp.ChannelInputList
import groovyx.gpars.csp.ChannelOutputList
import org.jcsp.lang.CSProcess
import org.jcsp.lang.ChannelInput
import org.jcsp.lang.ChannelOutput

class Manager1Only implements CSProcess {

  def ChannelInputList inputs
  def ChannelOutputList outputs
  def ChannelInput fromBlender
  def ChannelOutput toBlender
  def int hoppers = 3

  void run(){
    def alt = new ALT(inputs)
    while (true) {
      // read 1 from one of the three hoppers
      def index = alt.fairSelect()
      def ChannelInput fromHopper = inputs[index]
      def ChannelOutput toHopper = outputs[index]
      assert 1 == fromHopper.read()
      // now read 1 from the blender
      assert 1 == fromBlender.read()
      // now send a response to the accepted hopper and the blender
      toHopper.write(0)
      toBlender.write(0)
      // now read terminating 2 from blender
      assert 2 == fromBlender.read()
      // and the terminating 2 from the accepted hopper
      assert 2 == fromHopper.read()
      // now send a response to the hopper and the blender
      toHopper.write(0)
      toBlender.write(0)
    }
  }
}
