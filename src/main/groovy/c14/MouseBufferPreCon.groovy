package c14

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel

import java.awt.Point
import java.awt.event.MouseEvent

import groovyx.gpars.csp.ALT
import org.jcsp.lang.CSProcess
import org.jcsp.lang.ChannelInput
import org.jcsp.lang.ChannelOutput

class MouseBufferPreCon implements CSProcess{

  def ChannelInput mouseEvent
  def ChannelInput getClick
  def ChannelOutput sendPoint

  void run() {
    def mouseBufferAlt = new ALT ( [ getClick, mouseEvent ] )
    def preCon = new boolean [2]
    def EVENT = 1
    def GET = 0
    preCon[EVENT]= true
    preCon[GET] = false
    def point
    while (true) {
      switch (mouseBufferAlt.select(preCon)) {
      case GET:
        getClick.read()
        sendPoint.write(point)
        preCon[GET] = false
        break
      case EVENT:
        def mEvent = mouseEvent.read()
        if ( mEvent.getID() == MouseEvent.MOUSE_PRESSED) {
          preCon[GET] = true
          point = mEvent.getPoint()
        }
        break
      }
    }
  }
}
