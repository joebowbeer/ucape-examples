package c16

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel

import org.jcsp.net2.NetChannelLocation

class PrintJob  implements Serializable{

  def int userId
  def NetChannelLocation useLocation
}
