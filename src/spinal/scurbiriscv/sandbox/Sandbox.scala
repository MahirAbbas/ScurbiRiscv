package scurbiriscv.sandbox


import spinal.core._
import spinal.lib._
import spinal.lib.misc.pipeline._


case class testMultiPipes() extends Component {

  val n01, n11,n12,n13 = Node()
  
  val INT = Payload(UInt(32 bits))
  val FP = Payload(UInt(32 bits))
  val AGU = Payload(UInt(32 bits))
  

  n01(INT,1)  := U(32).resize(32)
  // n01(FP, 2)   := U(10).resized
  // n01(AGU,3)  := U(18).resized
  n11(INT, 1)  := U(64).resize(32)
  // n12(FP, 2)   := U(20).resized
  // n13(AGU, 3)  := U(36).resized
  
  val c01 = StageLink(n01, n11)
  // val c02 = CtrlLink(n01, n12)
  // val c03 = CtrlLink(n01, n13)
  
  Builder(c01)
}

object Verilog {
  def main(args: Array[String]) {
    SpinalVerilog(new testMultiPipes())
  }
}
