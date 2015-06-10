package c20

import groovyx.gpars.csp.PAR
import org.jcsp.net2.NetChannel
import org.jcsp.net2.Node
import org.jcsp.net2.tcpip.TCPIPNodeAddress

def int nodeId = 5
def int sentMessages = 500
def int nodes = 5

def nodeIP = "127.0.0.5"
def nextNodeIP = "127.0.0.6"

def nodeAddress = new TCPIPNodeAddress(nodeIP, 3000)
Node.getInstance().init(nodeAddress)
def fromRing = NetChannel.net2one()
println "Node $nodeId has been created"

fromRing.read()
def nextNodeAddress = new TCPIPNodeAddress(nextNodeIP, 3000)
def toRing = NetChannel.one2net(nextNodeAddress, 50)
toRing.write(0)

def processNode = new AgentElement ( fromRing: fromRing,
                                     toRing: toRing,
                                     element: nodeId,
                                     iterations: sentMessages,
                                     nodes: nodes) 

new PAR ([ processNode]).run()
