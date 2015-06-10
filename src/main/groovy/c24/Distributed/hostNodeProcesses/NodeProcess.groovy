package c24.Distributed.hostNodeProcesses

//import org.jcsp.demos.util.Ask
import static javax.swing.JOptionPane.showInputDialog

import groovyx.gpars.csp.ChannelInputList
import groovyx.gpars.csp.ChannelOutputList
import org.jcsp.lang.CSTimer
import org.jcsp.lang.ProcessManager
import org.jcsp.net2.NetChannel
import org.jcsp.net2.Node
import org.jcsp.net2.mobile.CodeLoadingChannelFilter
import org.jcsp.net2.tcpip.TCPIPNodeAddress

import c24.Distributed.loaderObjects.RequestWorker
import c24.Distributed.loaderObjects.Signal
import c24.Distributed.loaderObjects.WorkerInterface
import c24.Distributed.loaderObjects.WorkerObject

println "running $args"
assert "Expected args = [hostIP] or [hostIP, nodeAddr4]" && args && (1..2).contains(args.size())
def hostIP = args[0]
def nodeAddr
if (args.size() == 2) {
  def nodeIP = '127.0.0.'
  def nodeAddr4 = args[1] as int
  nodeAddr = new TCPIPNodeAddress(nodeIP + nodeAddr4, 1000)
} else {
  nodeAddr = new TCPIPNodeAddress(1000) // create nodeAddr for most global IP address
}

def timer = new CSTimer()
def startTime = timer.read()
// create node instance
Node.getInstance().init(nodeAddr)
def workerIP = nodeAddr.getIpAddress()
println "Worker is located at $workerIP"
// create load channel
def loadChannel = NetChannel.numberedNet2One(1, new CodeLoadingChannelFilter.FilterRX())
def loadChannelLocation = loadChannel.getLocation()

// make connection to host
def hostAddr = new TCPIPNodeAddress(hostIP, 1000)
// create host request channel
def hostRequest = NetChannel.any2net(hostAddr, 1)
// send request for worker to host
println "Sending request to host"
def requestWorker = new RequestWorker (
  loadLocation: loadChannelLocation,
  nodeIP: workerIP)
hostRequest.write(requestWorker)
def requestSentTime = timer.read()
// read worker from host using load channel
def workerObject = (WorkerObject)loadChannel.read()
def workerReadTime = timer.read()
println "Have read WorkerObject"
// extract worker process components
def wProcess = (WorkerInterface) workerObject.workerProcess
def inConnections =  workerObject.inConnections
def outConnections =  workerObject.outConnections
// create input channels
println "Creating in channels $inConnections"
def inChannels = new ChannelInputList()
//for ( i in 0 ..< inConnections.size()){
inConnections.each{ cn ->
  inChannels.append(NetChannel.numberedNet2One(cn))
}
// signal that input channels have been created
hostRequest.write(new Signal())
println "Sent signal to host"
// wait for responding signal from host
loadChannel.read()
println "Response read from host - Creating out channels $outConnections"
// create the output channels
def outChannels = new ChannelOutputList()
//for ( i in 0 ..< outConnections.size()){
outConnections.each{ connection ->
  def outNodeAddr = new  TCPIPNodeAddress(connection[0], 1000)
  outChannels.append(NetChannel.any2net(outNodeAddr, connection[1]))
}
println "Created out channels"
// connect the channel lists to the worker process
wProcess.connect(inChannels, outChannels)
// create a process manager for wProcess
def wPM = new ProcessManager(wProcess)
println "Starting loaded worker process"
// start wProcess
def workerStartTime = timer.read()
wPM.start()
// and wait for it to terminate
wPM.join()
def workerEndTime = timer.read()
println "worker $workerIP has terminated"
hostRequest.write([startTime, requestSentTime, workerReadTime, workerStartTime, workerEndTime])
