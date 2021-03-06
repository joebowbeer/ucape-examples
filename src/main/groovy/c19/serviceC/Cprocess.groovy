package c19.serviceC

import java.awt.BorderLayout
import java.awt.Label

import groovyx.gpars.csp.PAR
import org.jcsp.awt.ActiveClosingFrame
import org.jcsp.lang.CSProcess

class Cprocess implements CSProcess, Serializable{
	
	void run(){
		def root = new ActiveClosingFrame ("SERVICE C")
		def main = root.getActiveFrame()
		def sorrylabel = new Label("This service is not yet available")
		main.setLayout(new BorderLayout())
		main.setSize(480, 640)
		main.add(sorrylabel)
		main.pack()
		main.setVisible(true)
		new PAR([root]).run()

	}
}
