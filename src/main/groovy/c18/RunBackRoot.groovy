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
def fromRing = NetChannel.net2one() // 50

//def int iterations = Ask.Int ("Number of Iterations ? ", 1, 9)
//def String initialValue = Ask.string ( "Initial List Value ? ")

def iterations = JOptionPane.showInputDialog('Number of Iterations (1 to 9)?') as int
def initialValue = JOptionPane.showInputDialog('Initial List Value?')

def backChannel = NetChannel.net2one()  // 51
println " BackRoot channel location = ${backChannel.getLocation()} "

def nextNodeIP = "127.0.0.2"
def nextNodeAddress = new TCPIPNodeAddress(nextNodeIP, 3000)
def toRing = NetChannel.one2net(nextNodeAddress, 50)

toRing.write(0)
fromRing.read()

def rootNode = new BackRoot ( inChannel: fromRing,
                               outChannel: toRing,
                               iterations: iterations,
                               initialValue: initialValue,
                               backChannel: backChannel)

new PAR ( [rootNode] ).run()
