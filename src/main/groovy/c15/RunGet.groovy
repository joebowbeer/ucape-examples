package c15

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel

import groovyx.gpars.csp.PAR
import org.jcsp.net2.NetChannel
import org.jcsp.net2.Node
import org.jcsp.net2.tcpip.TCPIPNodeAddress

def getNodeIP = "127.0.0.2"

def getNodeAddr = new TCPIPNodeAddress(getNodeIP, 3000)
Node.getInstance().init (getNodeAddr)

def comms = NetChannel.net2one()
def pList = [ new Get ( inChannel: comms , id: 0 ) ]

new PAR ( pList ).run()
