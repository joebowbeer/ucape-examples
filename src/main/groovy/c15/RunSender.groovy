package c15

//import org.jcsp.demos.util.Ask
import javax.swing.JOptionPane

import groovyx.gpars.csp.PAR
import org.jcsp.net2.NetChannel
import org.jcsp.net2.Node
import org.jcsp.net2.tcpip.TCPIPNodeAddress

//def v= Ask.Int ("Sender identity number 2-9 ? ", 2, 9)
def v = JOptionPane.showInputDialog('Sender identity number 2-9?') as int

def senderNodeIPbase = "127.0.0."
def senderNodeIP = senderNodeIPbase + v
def senderNode = new TCPIPNodeAddress(senderNodeIP, 3000)
Node.getInstance().init (senderNode)

def receiverNodeIP = "127.0.0.1"
def receiverNode = new TCPIPNodeAddress(receiverNodeIP, 3000)

def comms = NetChannel.any2net(receiverNode, 100)
def pList = [ new Sender ( outChannel: comms, id: v ) ]

new PAR ( pList ).run()
