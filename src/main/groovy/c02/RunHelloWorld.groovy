package c02

import groovyx.gpars.csp.PAR
import org.jcsp.lang.Channel

def connect = Channel.one2one()
def processList = [
  new ProduceHW(outChannel: connect.out()),
  new ConsumeHello(inChannel: connect.in())
]
new PAR(processList).run()
