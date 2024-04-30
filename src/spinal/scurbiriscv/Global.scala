package scurbiriscv

import spinal.core._
import spinal.lib.misc.pipeline.Payload
import scurbiriscv.riscv._


// Global signals
object Global extends AreaRoot{

  val PHYSICAL_WIDTH = 64
  val PC_WIDTH = 64
  val TVAL_WIDTH = Int
  val CODE_WIDTH = Int
  val TRAP_ARG_WIDTH = Int

  val PHYSICAL_ADDRESS = Payload(UInt(PHYSICAL_WIDTH bits))
  val PC = Payload(UInt(PHYSICAL_WIDTH bits))
  val PC_TARGET = Payload(UInt(PHYSICAL_WIDTH bits))

}
