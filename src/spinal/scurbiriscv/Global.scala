package scurbiriscv

import spinal.core._
import spinal.lib.misc.pipeline.Payload
import scurbiriscv.riscv._


// Global signals
object Global extends AreaRoot{


  val PC = Payload(UInt(Riscv.XLEN bits))

}
