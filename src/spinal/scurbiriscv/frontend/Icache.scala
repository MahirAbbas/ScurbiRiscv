package scurbiriscv.frontend

import spinal.core._
import spinal.lib._


case class DataLoadCmd() extends Bundle {
  
}

case class DataLoadPort() extends Bundle with IMasterSlave {

  override def asMaster() = {

  }
}

case class DataMemReadCmd() extends Bundle {
  
}

case class DataMemBus() extends Bundle with IMasterSlave {
  
  override def asMaster() = {
    
  }
}

class ICache() extends Area {
  val io = new Bundle {
    
  }
  
  val logic = new Area {
    
    val cacheSize = ???
    val cpuWordWidth = ???
    val waySize = ???
    val nSets = ???
    val linePerWay = ???
    val tagWidth = ???
    val tagRange = ???
    val setIndexRange = ???
    val blockIndexRange = ???
    
    case class Tag() extends Bundle {
      
    }
    
    val WAYS_HITS = ???
    val BANK_WORD = ???
    val INSTRUCTION = ???
    



    val invalidate = new Area {
      
    }


    val refill = new Area {
      
    }
    
    val load = new Area {

    }
  }


}