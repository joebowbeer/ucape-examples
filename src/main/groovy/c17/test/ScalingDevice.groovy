package c17.test

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel

import groovyx.gpars.csp.PAR
import org.jcsp.lang.CSProcess
import org.jcsp.lang.CSTimer
import org.jcsp.lang.Channel
import org.jcsp.lang.ChannelInput
import org.jcsp.lang.ChannelOutput

import c05.Controller
import c05.Scale

class ScalingDevice implements CSProcess {

  def ChannelInput inChannel
  def ChannelOutput outChannel

  void run() {
    println "scaling device started"
    def oldScale = Channel.one2one()
    def newScale = Channel.one2one()
    def pause = Channel.one2one()

    def field = Channel.one2one()
    def entered = Channel.one2one()

    def testList = [
      new Scale (
        inChannel: inChannel,
        outChannel: outChannel,
        factor: oldScale.out(),
        suspend: pause.in(),
        injector: newScale.in(),
        multiplier: 2,
        scaling: 2 ),
      
      new Controller (
        testInterval: 7000,
        suspend: pause.out(),
        factor: oldScale.in(),
        prompt: field.out(),
        event: entered.in(),
        injector: newScale.out()
      ),
      
      new CSProcess() {
        def timer = new CSTimer()
        def ChannelInput prompt = field.in()
        def ChannelOutput event = entered.out()
        def computeInterval = 700
        def addition = 1
        void run() {
          while (true) {
            def currentFactor = prompt.read()
            assert true == prompt.read()
            timer.sleep(computeInterval)
            currentFactor = currentFactor + addition
            event.write(currentFactor.toString())
            assert false == prompt.read()
          }
        }
      }
    ]

    new PAR(testList).run()
  }
}
