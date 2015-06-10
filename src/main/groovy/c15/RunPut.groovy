package c15

import groovyx.gpars.csp.PAR
import org.jcsp.net2.NetChannel
import org.jcsp.net2.Node
import org.jcsp.net2.tcpip.TCPIPNodeAddress

def putNodeIP = "127.0.0.1"
def getNodeIP = "127.0.0.2"

def nodeAddr = new TCPIPNodeAddress(putNodeIP,3000)
Node.getInstance().init (nodeAddr)

def getNode = new TCPIPNodeAddress(getNodeIP, 3000)
def comms = NetChannel.one2net(getNode, 50)

def pList = [ new Put ( outChannel: comms ) ]

new PAR ( pList ).run()
