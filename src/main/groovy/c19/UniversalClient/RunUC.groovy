package c19.UniversalClient

import groovyx.gpars.csp.PAR
import org.jcsp.lang.Channel
import org.jcsp.util.OverWriteOldestBuffer

def ipChannel = Channel.one2one(new OverWriteOldestBuffer(5))

new PAR([ new UCInterface(sendNodeIdentity: ipChannel.out()),
    new UCCapability(receiveNodeIdentity: ipChannel.in() )]).run()
