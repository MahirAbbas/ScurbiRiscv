package scurbiriscv.frontend

import spinal.core._
import spinal.lib._
import spinal.lib.misc.pipeline._
import scurbiriscv.riscv.Riscv


// case class JumpCmd() extends Bundle {
//   val pc = UInt(Riscv.XLEN bits)
// }

case class Pc() extends Component {
  val PC = Payload(UInt(Riscv.XLEN bits))
  // val PC_NEXT = Payload(UInt(Riscv.XLEN bits))
  

  val pcNode = Node()
  
  val logic = new pcNode.Area {

    val jump = new Area {
      val pcLoad = Flow(JumpCmd())
    }

    val fetchPc = new Area {
      val pc = (UInt(Riscv.XLEN bits)) 
      val pcReg = Reg(PC) 
      
      pcReg := pcReg + 4
      
      when(jump.pcLoad.valid) {
        pc := jump.pcLoad.pc
      }
      PC := pcReg
    }
  }
}