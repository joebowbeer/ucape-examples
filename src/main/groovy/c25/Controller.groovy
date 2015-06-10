package c25

import groovyx.gpars.csp.ChannelInputList
import groovyx.gpars.csp.ChannelOutputList
import groovyx.gpars.csp.PAR
import org.jcsp.awt.ActiveCanvas
import org.jcsp.awt.DisplayList
import org.jcsp.lang.Channel
import org.jcsp.lang.CSProcess

class Controller implements CSProcess {
  int maxPlayers = 5

  void run(){
    def dList = new DisplayList()
    def gameCanvas = new ActiveCanvas()
    gameCanvas.setPaintable(dList)
    def statusConfig = Channel.createOne2One()
    def IPlabelConfig = Channel.createOne2One()
    def pairsConfig = Channel.createOne2One()
    def playerNames = Channel.createOne2One(maxPlayers)
    def pairsWon = Channel.createOne2One(maxPlayers)
    def playerNamesIn = new ChannelInputList(playerNames)
    def playerNamesOut = new ChannelOutputList(playerNames)
    def pairsWonIn = new ChannelInputList(pairsWon)
    def pairsWonOut = new ChannelOutputList(pairsWon)

    def network = [ new ControllerManager (
        dList: dList,
        statusConfig: statusConfig.out(),
        IPlabelConfig: IPlabelConfig.out(),
        pairsConfig: pairsConfig.out(),
        playerNames: playerNamesOut,
        pairsWon: pairsWonOut,
        maxPlayers: maxPlayers
      ),
      new ControllerInterface(
        gameCanvas: gameCanvas,
        statusConfig: statusConfig.in(),
        IPlabelConfig: IPlabelConfig.in(),
        pairsConfig: pairsConfig.in(),
        playerNames: playerNamesIn,
        pairsWon: pairsWonIn
      )
    ]
    new PAR (network).run()
  }
}