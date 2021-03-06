package c13

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel

//import org.jcsp.demos.util.Ask
import javax.swing.JOptionPane

import groovyx.gpars.csp.ChannelInputList
import groovyx.gpars.csp.ChannelOutputList
import groovyx.gpars.csp.PAR
import groovyx.gpars.csp.plugAndPlay.GConsole
import org.jcsp.lang.Channel

//def nReaders = Ask.Int ( "Number of Readers ? ", 1, 5)
def nReaders = JOptionPane.showInputDialog('Number of Readers?') as int
//def nWriters = Ask.Int ( "Number of Writers ? ", 1, 5)
def nWriters = JOptionPane.showInputDialog('Number of Writers?') as int

def connections = nReaders + nWriters

def toDatabase = Channel.one2oneArray(connections)
def fromDatabase = Channel.one2oneArray(connections)
def consoleData = Channel.one2oneArray(connections)

def toDB = new ChannelInputList(toDatabase)
def fromDB = new ChannelOutputList(fromDatabase)

def readers = ( 0 ..< nReaders).collect { r ->
  return new Read   (id: r,
    r2db: toDatabase[r].out(),
    db2r: fromDatabase[r].in(),
    toConsole: consoleData[r].out())
}

def writers = ( 0 ..<nWriters).collect { w ->
  int wNo = w + nReaders
  return new Write  ( id: w,
    w2db: toDatabase[wNo].out(),
    db2w: fromDatabase[wNo].in(),
    toConsole: consoleData[wNo].out())
}

def database = new DataBase ( inChannels:  toDB,
  outChannels: fromDB,
  readers: nReaders,
  writers: nWriters)

def consoles = ( 0 ..< connections).collect { c ->
  def frameString = c < nReaders ?
                        		"Reader " + c :
                        		"Writer " + (c - nReaders)
  return new GConsole (toConsole: consoleData[c].in(),
    frameLabel: frameString )
}
def procList = readers + writers + database + consoles

new PAR(procList).run()
