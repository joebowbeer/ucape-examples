package c17.sniff

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel

import groovyx.gpars.csp.PAR
import org.jcsp.lang.Channel

def Copy2Sniff = Channel.one2one()
def Out2Comp = Channel.one2one()

def network = [ new SnifferComparator (
    fromCopy: Copy2Sniff.in(),
    fromScaler: Out2Comp.in(),
    interval: 15000 ),

  new ScalingSystem (
    toSniffer: Copy2Sniff.out(),
    toComparator: Out2Comp.out() )
]
new PAR (network).run()
