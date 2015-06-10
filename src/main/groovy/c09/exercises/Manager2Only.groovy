package c09.exercises

import groovyx.gpars.csp.ALT
import groovyx.gpars.csp.ChannelInputList
import groovyx.gpars.csp.ChannelOutputList
import org.jcsp.lang.CSProcess
import org.jcsp.lang.ChannelInput
import org.jcsp.lang.ChannelOutput

class Manager2Only implements CSProcess {

  def ChannelInputList inputs
  def ChannelOutputList outputs
  def ChannelInput fromBlender
  def ChannelOutput toBlender
  def int hoppers = 3

  void run(){
    def alt = new ALT(inputs)
    while (true) {
      // read from 2 of the hoppers and remember which are being used
      def boolean[] preCon = [true] * hoppers
      def selected = []
      2.times {
        def index = alt.fairSelect(preCon)
        selected << index
        assert 1 == inputs[index].read()
        preCon[index] = false
      }
      // now read 1 from the blender
      assert 1 == fromBlender.read()
      // now send a response to the two accepted hoppers and the blender
      for (index in selected) {
        outputs[index].write(0)
      }
      toBlender.write(0)
      // now read terminating 2 from blender
      assert 2 == fromBlender.read()
      // and the termninating 2 from the hoppers
      for (index in selected) {
        assert 2 == inputs[index].read()
      }
      // now send a response to the hoppers and the blender
      for (index in selected) {
        outputs[index].write(0)
      }
      toBlender.write(0)
    }
  }
}
