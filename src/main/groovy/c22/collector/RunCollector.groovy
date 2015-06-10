package c22.collector;

//import org.jcsp.demos.util.Ask
import javax.swing.JOptionPane

import groovyx.gpars.csp.PAR
import org.jcsp.net2.NetChannel
import org.jcsp.net2.Node
import org.jcsp.net2.mobile.CodeLoadingChannelFilter
import org.jcsp.net2.tcpip.TCPIPNodeAddress

def nodeAddr = new TCPIPNodeAddress("127.0.0.2",3000)
Node.getInstance().init (nodeAddr)
println "Collector IP address = ${nodeAddr.getIpAddress()}"

def fromWorkers = NetChannel.net2one(new CodeLoadingChannelFilter.FilterRX())

def fromWorkersLoc = fromWorkers.getLocation()
println "Collector: from Workers channel Location - ${fromWorkersLoc.toString()}"

//def workers = Ask.Int ("Number of workers? ", 1, 20)
def workers = JOptionPane.showInputDialog('Number of workers (1..20)?') as int

def collector = new Collector ( fromWorkers: fromWorkers,
  workers: workers)

new PAR([collector]).run()
