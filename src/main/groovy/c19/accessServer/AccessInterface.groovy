package c19.accessServer

import java.awt.BorderLayout
import java.awt.Container
import java.awt.GridLayout

import groovyx.gpars.csp.PAR
import org.jcsp.awt.ActiveButton
import org.jcsp.awt.ActiveClosingFrame
import org.jcsp.lang.CSProcess
import org.jcsp.lang.ChannelOutput

class AccessInterface implements CSProcess {
	
	def ChannelOutput buttonEvents
	
	void run (){
		def root = new ActiveClosingFrame ("Access Server")
		def main = root.getActiveFrame()
		def groupService = new ActiveButton ( null, buttonEvents, "Group Location Service")
		def serviceA = new ActiveButton ( null, buttonEvents, "Service - A")
		def serviceB = new ActiveButton ( null, buttonEvents, "Service - B")
		def serviceC = new ActiveButton ( null, buttonEvents, "Service - C")
		def container = new Container()
		container.setLayout(new GridLayout(4,1))
		container.add(groupService)
		container.add(serviceA)
		container.add(serviceB)
		container.add(serviceC)
		main.setLayout(new BorderLayout())
		main.setSize(480, 640)
		main.add(container)
		main.pack()
		main.setVisible(true)
		new PAR([root, groupService, serviceA, serviceB, serviceC]).run()

	}
}
