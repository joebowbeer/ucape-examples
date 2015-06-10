package c15

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel

//import org.jcsp.demos.util.Ask
import javax.swing.JOptionPane

import groovyx.gpars.csp.PAR
import groovyx.gpars.csp.plugAndPlay.GConsole
import org.jcsp.net2.NetChannel
import org.jcsp.net2.Node
import org.jcsp.net2.tcpip.TCPIPNodeAddress

import c13.Write

def dbIp = "127.0.0.1"
def writeBase = 200
def writerBaseIP = "127.0.0."
//def writerId = Ask.Int ("Writer process ID (0..4)? ", 0, 4)
def writerId = JOptionPane.showInputDialog('Writer process ID (0..4)?') as int
def writerIndex = writeBase + writerId
def writerIP = writerBaseIP + writerIndex
def writerAddress = new TCPIPNodeAddress(writerIP, 2000)
Node.getInstance().init(writerAddress)

println "Write Process $writerId, $writerIP is creating its Net channels "

//NetChannelInput
def fromDB = NetChannel.numberedNet2One(150)  // the net channel from the database
println "fromDB location = ${fromDB.getLocation()}"

//NetChannelOutput
def dbAddress =  new TCPIPNodeAddress(dbIp, 3000)
def toDB = NetChannel.one2net(dbAddress, writerIndex) // the net channel to the database
println "toDB location = ${toDB.getLocation()}"
toDB.write(0)
fromDB.read()

println "Write Process $writerId has created its Net channels "
def consoleChannel = Channel.one2one()

def pList = [
  new Write ( id:writerId, w2db:toDB, db2w:fromDB, toConsole: consoleChannel.out()  ),
  new GConsole(toConsole:consoleChannel.in(), frameLabel: "Writer $writerId"  )
]
new PAR (pList).run()
