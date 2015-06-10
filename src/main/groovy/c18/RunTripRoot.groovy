package c18

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel

//import org.jcsp.demos.util.Ask
import javax.swing.JOptionPane

import groovyx.gpars.csp.PAR
import org.jcsp.net2.NetChannel
import org.jcsp.net2.Node
import org.jcsp.net2.tcpip.TCPIPNodeAddress

def rootIP = "127.0.0.1"
def rootAddress = new TCPIPNodeAddress(rootIP, 3000)
Node.getInstance().init(rootAddress)
def fromNodes = NetChannel.net2one()

//def int nodes = Ask.Int ("Number of nodes (1..8) ? ", 1, 8)
//def String initialValue = Ask.string ( "Initial List Value ? ")

def nodes = JOptionPane.showInputDialog('Number of Nodes (1 to 8)?') as int
def initialValue = JOptionPane.showInputDialog('Initial List Value?')

def rootNode = new TripRoot ( fromNodes: fromNodes,
                               nodes: nodes,
                               initialValue: initialValue )

new PAR ( [rootNode] ).run()
