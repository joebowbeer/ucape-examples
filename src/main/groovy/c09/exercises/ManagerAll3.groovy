package c09.exercises

import groovyx.gpars.csp.ChannelInputList
import groovyx.gpars.csp.ChannelOutputList
import org.jcsp.lang.CSProcess

class ManagerAll3 implements CSProcess {

  def ChannelInputList inputs
  def ChannelOutputList outputs

  void run(){
    while (true) {
      // read 1 from the hoppers and the blender
      assert [1] * inputs.size() == inputs.read()
      // now send a response to the hoppers and the blender
      outputs.write(0)
      // now read terminating 2 from hoppers and blender
      assert [2] * inputs.size() == inputs.read()
      // now send a response to hoppers and blender
      outputs.write(0)
    }
  }
}
