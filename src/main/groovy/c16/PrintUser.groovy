package c16

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel

import org.jcsp.lang.CSProcess
import org.jcsp.lang.ChannelOutput
import org.jcsp.net2.NetChannel

class PrintUser implements CSProcess {

  def ChannelOutput printerRequest
  def ChannelOutput printerRelease
  def int userId

  void run() {
    def printList = [
      "line 1 for user " + userId,
      "line 2 for user " + userId,
      "last line for user " + userId
    ]
    def useChannel = NetChannel.net2one()
    printerRequest.write(new PrintJob ( userId: userId,
        useLocation: useChannel.getLocation() ) )
    def printChannelLocation = useChannel.read()
    def useKey = useChannel.read()
    println "Print for user ${userId} accepted using Spooler $useKey"
    def printerChannel = NetChannel.one2net ( printChannelLocation)
    printList.each { printerChannel.write (new Printline ( printKey: useKey, line: it) )}
    printerRelease.write ( useKey )
    println "Print for user ${userId} completed"
  }
}
