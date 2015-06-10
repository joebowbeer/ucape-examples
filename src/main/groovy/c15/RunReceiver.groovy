package c15

import groovyx.gpars.csp.PAR
import org.jcsp.net2.NetChannel
import org.jcsp.net2.Node
import org.jcsp.net2.tcpip.TCPIPNodeAddress

def receiverNodeIP = "127.0.0.1"
def receiverNode = new TCPIPNodeAddress(receiverNodeIP, 3000)
Node.getInstance().init (receiverNode)

def comms = NetChannel.numberedNet2One(100)
def pList = [ new Receiver ( inChannel: comms ) ]

new PAR ( pList ).run()
