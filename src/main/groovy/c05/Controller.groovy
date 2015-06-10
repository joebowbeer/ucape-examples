package c05

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel

import java.awt.TextField
import org.jcsp.awt.ActiveTextField.Configure
import org.jcsp.lang.CSProcess
import org.jcsp.lang.CSTimer
import org.jcsp.lang.ChannelInput
import org.jcsp.lang.ChannelOutput

class Controller implements CSProcess {

  def long testInterval = 11000
  def ChannelOutput suspend
  def ChannelInput factor
  def ChannelOutput prompt
  def ChannelInput event
  def ChannelOutput injector

  void run() {
    def timer = new CSTimer()
    while (true) {
      timer.after(timer.read() + testInterval)   // wait for the timeout
      suspend.write(0)                           // suspend signal to Scale; value irrelevant
      prompt.write(factor.read())                // get current scaling from Scale
      prompt.write(true)                         // enable text field
      injector.write(event.read() as int)        // send new scale factor to Scale
      timer.after(timer.read() + 100)            // wait for disable time bogosity
      prompt.write(false)                        // disable text field
    }
  }
}
