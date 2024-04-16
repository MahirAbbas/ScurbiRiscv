package scurbiriscv.frontend.fetch

import spinal.core._
import spinal.lib._
import spinal.lib.misc.pipeline._
import scurbiriscv.riscv.Riscv


case class JumpCmd() extends Bundle {
  val pc = UInt(Riscv.XLEN bits)
}

case class Pc() extends Bundle {
  val PC = Payload(UInt(Riscv.XLEN bits))
  // val PC_NEXT = Payload(UInt(Riscv.XLEN bits))
  

  
  val logic = new Area {

    val jump = new Area {
      val pcLoad = Flow(JumpCmd())
      
    }

    val fetchPc = new Area {
      val output = Stream(PC)
      val pc = (UInt(Riscv.XLEN bits)) 
      val pcReg = Reg(PC) 
      
      when(jump.pcLoad.valid) {
        pc := jump.pcLoad.pc
      }

      when(output.ready) {
        pcReg := pc
      }
      
      val fetcherHalt = False 
      output.valid := !fetcherHalt
      output.payload := pc
    }
    
    fetchPc.output.ready := Node().ready
    Node().valid := fetchPc.output.valid
    
    

  }

}