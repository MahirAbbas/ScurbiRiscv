package scurbiriscv.exu

import spinal.core._
import spinal.lib._
import scurbiriscv.frontend.InstructionPacket._

case class RegFileWrite() extends Bundle {
  val valid = Bool()
  val address = UInt(5 bits)
  val data = Bits(32 bits)
}

case class RegFileRead() extends Bundle {
  val valid = Bool()
  val address = UInt(5 bits)
  val data = Bits(32 bits)

}

case class RegFile() extends Area {
  
  val regfile = new Area {
    val mem = Mem(Bits(32 bits), 32)
    val rs1 = mem.readSyncPort
    val rs2 = mem.readSyncPort
    val rd  = mem.writePort
  }
  
  val write = new Area {
    
  }

  
}
