package scurbiriscv.frontend

import scurbiriscv.riscv._
import scurbiriscv.exu._
import spinal.core._
import spinal.lib._
import spinal.lib.misc.pipeline._
import spinal.lib.misc.plugin.FiberPlugin
import spinal.core.fiber.Fiber



object Decode extends AreaObject {
  val INSTRUCTION_WIDTH = 32 
  val UOP_WIDTH = Int
  
  val INSTRUCTION = Payload(Bits(32 bits))
  val UOP = Payload(InstructionPacket())
}

case class InstructionPacket() extends Bundle {
  // val op              = ???
  val sext            = (SInt(32 bits))
  // use something like val is_rs2 = Bool()
  val rs1             = (UInt(5 bits))
  val rs2             = (UInt(5 bits))
  val rd              = (UInt(5 bits))
  val is_br           = (Bool())
  val alu             = (Bool())
  val funct3          = (Bits(3 bits))
  val funct7          = (Bits(7 bits))
  val is_i_type       = Bool()
  val is_r_type       = Bool()
  val is_j_type       = (Bool())
  val is_u_type       = (Bool())
  val is_s_type       = (Bool())
  val regfilereadcmd  = (RegFileReadCmd())
}


        
