package c02

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel

import groovyx.gpars.csp.PAR
import org.jcsp.lang.Channel

class ProduceHWTest extends GroovyTestCase {

  void testMessage() {
    def connect = Channel.one2one()
    def producer =  new ProduceHW ( outChannel: connect.out() )
    def consumer = new ConsumeHelloForTest ( inChannel: connect.in() )

    def processList = [ producer, consumer ]
    new PAR (processList).run()
    def expected = "Hello World!!!"
    def actual = consumer.message
    assertTrue(expected == actual)
  }
}
