package scurbiriscv.frontend

import spinal.core._ 
import spinal.lib._ 
import spinal.lib.misc.pipeline._

// Fetch Payload
object Fetch extends AreaObject {
  val WORD = Payload(Bits(64 bits))
  val WORD_PC = Payload(Bits (64 bits))
}