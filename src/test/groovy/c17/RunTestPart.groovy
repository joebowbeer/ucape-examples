package c17

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel

import org.junit.experimental.categories.Category

import groovyx.gpars.csp.PAR
import groovyx.gpars.csp.util.TestUtilities
import org.jcsp.net2.NetChannel
import org.jcsp.net2.Node
import org.jcsp.net2.tcpip.TCPIPNodeAddress

import c17.test.CollectNumbers
import c17.test.GenerateNumbers

// These tests require c17.test.RunScalingDevice to be running
interface IntegrationTests { } 

@Category(IntegrationTests)
class RunTestPart extends GroovyTestCase {

  void testScalingDevice() {
    def testPartIP = "127.0.0.1"
    def deviceIP = "127.0.0.2"
    def testPartAddr = new TCPIPNodeAddress(testPartIP, 3000)
    def deviceAddr = new TCPIPNodeAddress(deviceIP, 3000)
    Node.getInstance().init(testPartAddr)

    def ordinaryOutput = NetChannel.one2net(deviceAddr, 50)
    println "ordinaryOutput location = ${ordinaryOutput.getLocation()}"
    def scaledInput = NetChannel.net2one()
    println "scaledInput location = ${scaledInput.getLocation()}"
    ordinaryOutput.write(1)

    def collector = new CollectNumbers ( inChannel: scaledInput)
    def generator = new GenerateNumbers (outChannel: ordinaryOutput)

    def testList = [ collector, generator]

    new PAR(testList).run()

    def original = generator.generatedList
    def unscaled = collector.collectedList
    def scaled = collector.scaledList
    assertTrue (original == unscaled)
    assertTrue (TestUtilities.list1GEList2(scaled, original))
  }
}
