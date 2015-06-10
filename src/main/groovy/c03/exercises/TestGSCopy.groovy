package c03.exercises

import groovyx.gpars.csp.PAR
import groovyx.gpars.csp.plugAndPlay.GPrint
import org.jcsp.lang.Channel
import org.jcsp.lang.One2OneChannel

One2OneChannel S2P = Channel.createOne2One()

def testList = [
  new GSquares (
    outChannel: S2P.out() ),
  new GPrint   (
    inChannel: S2P.in(),
    heading : "Squares" )
]

new PAR ( testList ).run()
