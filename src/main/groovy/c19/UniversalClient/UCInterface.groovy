package c19.UniversalClient

import java.awt.BorderLayout
import java.awt.Container
import java.awt.GridLayout
import java.awt.Label

import groovyx.gpars.csp.PAR
import org.jcsp.awt.ActiveClosingFrame
import org.jcsp.awt.ActiveTextEnterField
import org.jcsp.lang.CSProcess
import org.jcsp.lang.ChannelOutput

class UCInterface implements CSProcess {
	
	def ChannelOutput sendNodeIdentity
	
	void run (){
		def root = new ActiveClosingFrame ("Universal Client")
		def main = root.getActiveFrame()
		def requestlabel = new Label("Client IP Node Identity ?")
		def enterIPfield = new ActiveTextEnterField (null, sendNodeIdentity)
		def enterIP = enterIPfield.getActiveTextField ()
		def container = new Container()
		container.setLayout(new GridLayout(2,1))
		container.add(requestlabel)
		container.add(enterIP)
		main.setLayout(new BorderLayout())
		main.setSize(480, 640)
		main.add(container)
		main.pack()
		main.setVisible(true)
		new PAR([root, enterIPfield]).run()
	}
}
