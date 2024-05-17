package scurbiriscv.exu

import spinal.core._
// import spinal.lib._
import spinal.lib.misc.pipeline._
import scurbiriscv.exu.IntAlu.AluCtrl


object IntAlu extends AreaObject {
  val AluCtrl = new SpinalEnum {
    val XOR, OR, AND, ADD_SUB, SLT_SLTU = newElement()
    
  }
}

case class IntAlu() extends Component {
  val io = new Bundle {
    val alu_result = out port Bits(32 bits)
    val alu_ctrl = AluCtrl()
    val src1, src2 = in port Bits(32 bits)
    val funct3 = in port Bits(3 bits)
    val funct7 = in port Bits(7 bits)
  }
  

  val alu = new Area {
    val result = io.alu_ctrl.mux(
      AluCtrl.AND -> (io.src1 & io.src2),
      AluCtrl.OR  -> (io.src1 | io.src2),
      AluCtrl.XOR -> (io.src1 ^ io.src2),
      AluCtrl.ADD_SUB -> (io.src1.asSInt + io.src2.asSInt),
      AluCtrl.SLT_SLTU -> ???
    )
    io.alu_result := result.asBits
  }
}