package c18

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel

//import org.jcsp.demos.util.Ask
import static javax.swing.JOptionPane.showConfirmDialog
import static javax.swing.JOptionPane.showInputDialog
import static javax.swing.JOptionPane.YES_NO_OPTION
import static javax.swing.JOptionPane.YES_OPTION

import groovyx.gpars.csp.PAR
import org.jcsp.net2.NetChannel
import org.jcsp.net2.Node
import org.jcsp.net2.tcpip.TCPIPNodeAddress

//def int nodeId = Ask.Int ("Node identification (2..9) ? ", 2, 9)
//def Boolean last = Ask.Boolean ("Is this the last node? - ( y or n):")

def nodeId = showInputDialog('Node identification (2 to 9)?') as int
def last = showConfirmDialog(null, 'Is this the last node?', 'Ask', YES_NO_OPTION) == YES_OPTION

def ipBase = "127.0.0."
def nodeIP = ipBase + nodeId
def nodeAddress = new TCPIPNodeAddress(nodeIP, 3000)
Node.getInstance().init(nodeAddress)
def fromRing = NetChannel.net2one()
fromRing.read()

def nextNodeIP = (last) ? "127.0.0.1" : ipBase + (nodeId + 1)

def nextNodeAddress = new TCPIPNodeAddress(nextNodeIP, 3000)
def toRing = NetChannel.one2net(nextNodeAddress, 50)
toRing.write(0)

def processNode = new ProcessNode ( inChannel: fromRing,
                                     outChannel: toRing,
                                     nodeId: nodeId)

new PAR ([ processNode]).run()
