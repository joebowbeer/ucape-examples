package c15

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel

import groovyx.gpars.csp.PAR
import org.jcsp.lang.Channel

def comms = Channel.one2one()

def network = [
  new Put (outChannel: comms.out()),
  new Get (inChannel: comms.in())
]

new PAR(network).run()
