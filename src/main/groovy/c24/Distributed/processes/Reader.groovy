package c24.Distributed.processes

import groovyx.gpars.csp.ChannelOutputList
import org.jcsp.lang.CSProcess
import org.jcsp.lang.CSTimer

import c24.Distributed.dataRecords.Sentinel
import c24.Distributed.dataRecords.WordBlock
import c24.SingleMachine.methods.defs

class Reader implements CSProcess {

  def ChannelOutputList outChannels
  def inRoot = '/'
  def N = 6
  def blockLength = 5000
  def runs = 8
  def sourceList
  def timeRoot = '/Concordance/OutputFiles/Distributed/Times/'
  def runId  = 'SM'

  void run(){
    def timeFileName = timeRoot + runId + "_R_" + "_times.txt"
    def timeHandle = new File(timeFileName)
    if (timeHandle.exists()) timeHandle.delete()
    def timeWriter = timeHandle.newPrintWriter()
    def timer = new CSTimer()
    def int nodes = outChannels.size()
    def int blockStride = blockLength - N + 1
    for (source in sourceList){
      def fileName = inRoot + source + '.txt'
      println "READER - Processing: $fileName, N: $N," +
              "block length: $blockLength, nodes: $nodes, runs: $runs"
      timeWriter.println "READER - Processing: $fileName, N: $N, " +
                         "block length: $blockLength, nodes: $nodes, runs: $runs"
      for (run in 1..runs){
        def startTime = timer.read()
        def wordBuffer = new ArrayList(blockLength)
        def fileHandle = getClass().getResource(fileName).getFile()
        def fileReader = new FileReader(fileHandle)
        def globalIndex = 0
        def localIndex = 0
        def int currentNode = 0
        def firstWrite = true
        def beginTime
        fileReader.eachLine { line ->
          def words = defs.processLine(line)
          for ( w in words) {
            wordBuffer << w
            localIndex = localIndex + 1
            if (localIndex == blockLength) {
              def wordBlock = new WordBlock(
                startSubscript: globalIndex,
                words: wordBuffer)
              globalIndex = globalIndex + blockStride
              outChannels[currentNode].write(wordBlock)
              if (firstWrite) {
                beginTime = timer.read()
                firstWrite = false
              }
              currentNode = (currentNode + 1) % nodes
              def overlapBuffer = []
              for (overlap in blockStride..(blockLength - 1)) {
                overlapBuffer << wordBuffer[overlap]
              }
              wordBuffer = []
              localIndex = 0
              for (ow in overlapBuffer) {
                wordBuffer << ow
                localIndex = localIndex + 1
              } // end for ow
            } // end if
          } // end for words

        } // end eachLine
        def wordBlock = new WordBlock(
          startSubscript: globalIndex,
          last: true,
          words: wordBuffer)
        outChannels[currentNode].write(wordBlock)
        for ( n in 0..< nodes) outChannels[n].write(new Sentinel())
        def endTime = timer.read()
        def words = localIndex + globalIndex
        println "READER, $source, $run, ${endTime - startTime}, " +
                "${endTime - beginTime}, $words"
        timeWriter.println "READER, $source, $run, ${endTime - startTime}, " +
                           "${endTime - beginTime}, $words"
      } // end for run
    } // end for source
    timeWriter.flush()
    timeWriter.close()
  } // end void run
}
