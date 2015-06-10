package c18

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel

//import org.jcsp.demos.util.Ask
import javax.swing.JOptionPane

import groovyx.gpars.csp.PAR
import org.jcsp.net2.NetChannel
import org.jcsp.net2.Node
import org.jcsp.net2.tcpip.TCPIPNodeAddress

//def int nodeId = Ask.Int ("Node identification (2..9)? ", 2, 9)
def nodeId = JOptionPane.showInputDialog('Node identification (2 to 9)?') as int

def ipBase = "127.0.0."
def nodeIP = ipBase + nodeId
def nodeAddress = new TCPIPNodeAddress(nodeIP, 3000)
Node.getInstance().init(nodeAddress)

def rootNodeIP = "127.0.0.1"
def rootNodeAddress = new TCPIPNodeAddress(rootNodeIP, 3000)

def toRoot = NetChannel.any2net(rootNodeAddress, 50)

def processNode = new TripNode ( toRoot: toRoot,
                                  nodeId: nodeId)

new PAR ([processNode]).run()
