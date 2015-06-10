package c15

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel

import groovyx.gpars.csp.PAR
import org.jcsp.net2.NetChannel
import org.jcsp.net2.Node
import org.jcsp.net2.tcpip.TCPIPNodeAddress

import c12.canteen.ClockedQueuingServery

def chefNodeIP = "127.0.0.1"
def canteenNodeIP = "127.0.0.2"
def philosopherNodeIP = "127.0.0.3"

def canteenNodeAddr = new TCPIPNodeAddress(canteenNodeIP, 3000)
Node.getInstance().init (canteenNodeAddr)
def cooked = NetChannel.net2one() 
println "cooked location = ${cooked.getLocation()}"

def getOne = NetChannel.net2one()
println "getOne location = ${getOne.getLocation()}"

getOne.read()  // signal from the philosophers
def philosopherAddr = new TCPIPNodeAddress (philosopherNodeIP, 3002)
def gotOne = NetChannel.one2net(philosopherAddr, 50)

def processList = [
  new ClockedQueuingServery(service:getOne, deliver:gotOne, supply:cooked)
]
new PAR ( processList ).run()     
