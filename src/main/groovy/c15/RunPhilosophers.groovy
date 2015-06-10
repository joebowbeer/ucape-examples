package c15

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel

import groovyx.gpars.csp.PAR
import org.jcsp.net2.NetChannel
import org.jcsp.net2.Node
import org.jcsp.net2.tcpip.TCPIPNodeAddress

import c12.canteen.Philosopher

def chefNodeIP = "127.0.0.1"
def canteenNodeIP = "127.0.0.2"
def philosopherNodeIP = "127.0.0.3"

def philosopherNodeAddr = new TCPIPNodeAddress(philosopherNodeIP,3002)
Node.getInstance().init (philosopherNodeAddr)
def gotOne = NetChannel.net2any()
println "gotOne location = ${gotOne.getLocation()}"

def canteenAddress = new TCPIPNodeAddress(canteenNodeIP,3000)
def getOne = NetChannel.any2net(canteenAddress,51)
println "getOne location = ${getOne.getLocation()}"

getOne.write(0)
def philList = ( 0 .. 4 ).collect{
  i -> return new Philosopher(philosopherId:i, service:getOne, deliver:gotOne)
}
new PAR ( philList ).run()
