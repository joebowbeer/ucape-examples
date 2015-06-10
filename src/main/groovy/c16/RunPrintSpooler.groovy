package c16

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel

//import org.jcsp.demos.util.Ask
import javax.swing.JOptionPane

import groovyx.gpars.csp.PAR
import org.jcsp.net2.NetChannel
import org.jcsp.net2.Node
import org.jcsp.net2.tcpip.TCPIPNodeAddress

//def spoolers = Ask.Int ("Number of spoolers ? ", 1, 9)
def spoolers = JOptionPane.showInputDialog('Number of spoolers (1 to 9)?') as int

def printSpoolerIP = "127.0.0.1"
def psAddress = new TCPIPNodeAddress(printSpoolerIP, 2000)
Node.getInstance().init(psAddress)
def pRequest = NetChannel.net2one() // cn = 50
def pRelease = NetChannel.net2one() // cn = 51
println "pRequest location = ${pRequest.getLocation()}"
println "pRelease location = ${pRelease.getLocation()}"

new PAR ( [ new PrintSpooler (
      printerRequest: pRequest,
      printerRelease: pRelease,
      spoolers : spoolers
    )
  ] ).run()
