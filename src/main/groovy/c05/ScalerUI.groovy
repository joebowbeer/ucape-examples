package c05

import groovyx.gpars.csp.PAR
import java.awt.GridLayout
import java.awt.Label
import org.jcsp.awt.ActiveClosingFrame
import org.jcsp.awt.ActiveLabel
import org.jcsp.awt.ActiveTextEnterField
import org.jcsp.lang.CSProcess
import org.jcsp.lang.ChannelInput
import org.jcsp.lang.ChannelOutput

class ScalerUI implements CSProcess {

  def ChannelInput originalConfig
  def ChannelInput scaledConfig
  def ChannelInput factorConfig
  def ChannelOutput event

  void run() {
    def root = new ActiveClosingFrame ("Scaler")
    def mainFrame = root.getActiveFrame()
    def originalLabel = new Label('Original')
    def originalValue = new ActiveLabel(originalConfig)
    def scaledLabel = new Label('Scaled')
    def scaledValue = new ActiveLabel(scaledConfig)
    def factorLabel = new Label('Factor:')
    def factorValue = new ActiveTextEnterField(factorConfig, event)
    factorValue.setDisableTime(0)
    def textField = factorValue.getActiveTextField()
    textField.setEnabled(false)
    mainFrame.setLayout(new GridLayout(3, 2))
    mainFrame.add(originalLabel)
    mainFrame.add(scaledLabel)
    mainFrame.add(originalValue)
    mainFrame.add(scaledValue)
    mainFrame.add(factorLabel)
    mainFrame.add(textField)
    mainFrame.pack()
    mainFrame.setVisible(true)
    def network = [root, originalValue, scaledValue, factorValue]
    new PAR(network).run()
  }
}
