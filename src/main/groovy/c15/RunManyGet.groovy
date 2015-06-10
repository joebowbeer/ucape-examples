package c15

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel

//import org.jcsp.demos.util.Ask
import javax.swing.JOptionPane

import groovyx.gpars.csp.PAR
import org.jcsp.net2.NetChannel
import org.jcsp.net2.Node
import org.jcsp.net2.tcpip.TCPIPNodeAddress

//def numberOfGets = Ask.Int("How many get processes (2..9)?", 2, 9)
def numberOfGets = JOptionPane.showInputDialog('How many get processes (2..9)?') as int

def manyGetNodeIP = "127.0.0.2"
def manyGetAddr = new TCPIPNodeAddress(manyGetNodeIP, 3000)
Node.getInstance().init (manyGetAddr)

def comms = NetChannel.net2any()
def pList = (0 ..< numberOfGets).collect{
  i -> new Get ( inChannel: comms, id: i )
}

new PAR ( pList ).run()
