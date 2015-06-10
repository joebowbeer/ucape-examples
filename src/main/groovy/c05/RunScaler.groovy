package c05

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel

import groovyx.gpars.csp.PAR
import groovyx.gpars.csp.plugAndPlay.GFixedDelay
import groovyx.gpars.csp.plugAndPlay.GNumbers
import org.jcsp.lang.CSProcess
import org.jcsp.lang.Channel
import org.jcsp.lang.ChannelInput
import org.jcsp.lang.ChannelOutput

def data = Channel.one2one()
def timedData = Channel.one2one()
def scaledData = Channel.one2one()
def oldScale = Channel.one2one()
def newScale = Channel.one2one()
def pause = Channel.one2one()
def original = Channel.one2one()
def scaled = Channel.one2one()
def field = Channel.one2one()
def entered = Channel.one2one()

def network = [
  new GNumbers ( outChannel: data.out() ),

  new GFixedDelay (
    delay: 1000,
    inChannel: data.in(),
    outChannel: timedData.out() ),

  new Scale (
    inChannel: timedData.in(),
    outChannel: scaledData.out(),
    factor: oldScale.out(),
    suspend: pause.in(),
    injector: newScale.in(),
    multiplier: 2,
    scaling: 2 ),

  new Controller (
    testInterval: 11000,
    suspend: pause.out(),
    factor: oldScale.in(),
    prompt: field.out(),
    event: entered.in(),
    injector: newScale.out()
  ),

  new CSProcess() {
    def ChannelInput dataIn = scaledData.in()
    def ChannelOutput originalOut = original.out()
    def ChannelOutput scaledOut = scaled.out()
    void run() {
      while (true) {
        def ScaledData sd = dataIn.read()
        originalOut.write(sd.original.toString())
        scaledOut.write(sd.scaled.toString())
      }
    }
  },

  new ScalerUI(
    originalConfig: original.in(),
    scaledConfig: scaled.in(),
    factorConfig: field.in(),
    event: entered.out()
  )
]

new PAR ( network ).run()
