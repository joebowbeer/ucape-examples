package c22.emitter;

//import org.jcsp.demos.util.Ask
import javax.swing.JOptionPane

import groovyx.gpars.csp.PAR
import org.jcsp.net2.NetChannel
import org.jcsp.net2.Node
import org.jcsp.net2.tcpip.TCPIPNodeAddress

def nodeAddr = new TCPIPNodeAddress("127.0.0.1", 3000)
Node.getInstance().init (nodeAddr)
println "Emitter IP address = ${nodeAddr.getIpAddress()}"

def fromWorkers = NetChannel.net2one()

def fromWorkersLoc = fromWorkers.getLocation()
println "Emitter: from Workers channel Location - ${fromWorkersLoc.toString()}"

//def workers = Ask.Int ("Number of workers? ", 1, 17)
//def loops = Ask.Int ("Number of data objects to send? ", 1, 1000000)
//def elements = Ask.Int ("Number of elements in each TestObject? ", 1, 200)

def workers = JOptionPane.showInputDialog('Number of workers (1..17)?') as int
def loops = JOptionPane.showInputDialog('Number of data objects to send (1..1000000)?') as int
def elements = JOptionPane.showInputDialog('Number of elements in each TestObject (1..200)?') as int

def emit = new EmitterNet ( fromWorkers: fromWorkers,
  loops: loops,
  workers: workers,
  elements: elements )

new PAR([emit]).run()
