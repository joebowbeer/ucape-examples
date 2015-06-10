package c21

import groovyx.gpars.csp.PAR
import org.jcsp.net2.NetChannel
import org.jcsp.net2.Node
import org.jcsp.net2.tcpip.TCPIPNodeAddress

def nodeIP = "127.0.0.2"
def nodeAddress = new TCPIPNodeAddress(nodeIP, 3000)
Node.getInstance().init(nodeAddress)
def fromNodesToGatherer = NetChannel.net2one() // cn 50

println "Gatherer Starting"
def processList = new Gatherer ( fromNodes: fromNodesToGatherer )

new PAR ([ processList]).run()
