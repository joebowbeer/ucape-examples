package c24.Distributed.loaderObjects

import org.jcsp.lang.CSProcess

interface WorkerInterface extends CSProcess, Serializable {
  abstract connect(inChannels, outChannels)
}
