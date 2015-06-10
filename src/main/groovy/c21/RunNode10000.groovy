package c21

import groovyx.gpars.csp.PAR

def dataGenIP = "127.0.0.1"
def gathererIP = "127.0.0.2"

def pList = [ new Type1Process()]
def vList = [ new Type1Process()]

def processList = new NodeProcess (
  nodeId: 1,
  nodeIPFinalPart: 3,
  toGathererIP: gathererIP,
  toDataGenIP: dataGenIP,
  processList: pList,
  vanillaList: vList
)

new PAR ([ processList]).run()
