package scurbiriscv.frontend

import scurbiriscv.riscv._
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
  val OP      = Payload
  val SEXT    = Payload(SInt(32 bits))
  val RS1     = Payload(UInt(5 bits))
  val RS2     = Payload(UInt(5 bits))
  val RD      = Payload(UInt(5 bits))
  val IS_BR   = Payload(Bool())
  val ALU     = Payload(Bool())
  val FUNCT3  = Payload(Bits(3 bits))
  val FUNCT7  = Payload(Bits(7 bits))
}

// Define Decode table
// SingleDecoding to match instruction with const def
// Set the resources used (List) and output that from decode, (and setup microOps?)
// Also set type of OP (R, J , B, I etc.)
// translate micro-ops to Instruction Packet / control signals


class DecodeUnit() extends Area{
  import Rv32i._
  import Const._
  import InstructionPacket._
  
  val io = new Bundle {
    // val instruction = in port Bits(32 bits)
    // val uop = out port InstructionPacket()
  }
  val decode = Node()

  
  val decoding = new decode.Area {
    import Decode._
    val instruction = Bits(32 bits)
    instruction := INSTRUCTION
    switch(instruction){
      is(ADD, SUB ,SLL, SLT, SLTU, XOR, SRL, SRA, OR, AND) {
        UOP.SEXT.assignDontCare()
        UOP.RS1    := instruction(rs1Range).asUInt
        UOP.RS2    := instruction(rs2Range).asUInt
        UOP.RD     := instruction(rdRange).asUInt
        UOP.IS_BR  := False
        UOP.ALU    := True
        UOP.FUNCT3 := instruction(funct3Range)
        UOP.FUNCT7 := instruction(funct7Range)
      } 
      is(ADDI, SLTI, SLTIU, XORI ,ORI,  ANDI, SLLI, SRLI, SRAI) {
        UOP.SEXT   := IMM(instruction).i_sext
        UOP.RS1    := instruction(rs1Range).asUInt
        UOP.RS2.assignDontCare()
        UOP.RD     := instruction(rdRange).asUInt
        UOP.IS_BR  := False
        UOP.ALU    := True
        UOP.FUNCT3.assignDontCare()
        UOP.FUNCT7.assignDontCare()
      }
      default {

        UOP.SEXT   := S"0"
        UOP.RS1    := U"0"
        UOP.RS2.assignDontCare()
        UOP.RD     := U"0"
        UOP.IS_BR  := False
        UOP.ALU    := True
        // UOP.FUNCT3 := io.instruction(funct3Range)
        // UOP.FUNCT7 := io.instruction(funct7Range)
      }
    }
    
  }
        
}       
        
