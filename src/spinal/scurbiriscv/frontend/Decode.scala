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
  val OP              = ???
  val SEXT            = Payload(SInt(32 bits))
  // val RS1             = Payload(UInt(5 bits))
  // val RS2             = Payload(UInt(5 bits))
  val RD              = Payload(UInt(5 bits))
  val IS_BR           = Payload(Bool())
  val ALU             = Payload(Bool())
  val FUNCT3          = Payload(Bits(3 bits))
  val FUNCT7          = Payload(Bits(7 bits))
  val IS_J_TYPE       = Payload(Bool())
  val IS_U_TYPE       = Payload(Bool())
  val IS_S_TYPE       = Payload(Bool())
  val REGFILEREADCMD  = Payload(RegFileReadCmd())
}


class Decode() extends Area{
  import Rv32i._
  import Const._
  import InstructionPacket._
  import Decode._ 
  
  // val io = new Bundle {
  //   val instruction = in port Bits(32 bits)
  //   val uop = out port InstructionPacket()
  // }
  val decodeNode = Node()

  // val link = StageLink(decode.up, decode.down)
  val decoding = new decodeNode.Area {
    
    // val instruction = Bits(32 bits)
    // INSTRUCTION := up(INSTRUCTION)
    switch(decodeNode(INSTRUCTION)){
      // R-Type ALU
      is(ADD, SUB ,SLL, SLT, SLTU, XOR, SRL, SRA, OR, AND) {
        UOP.SEXT.assignDontCare()

        UOP.REGFILEREADCMD.validRs1 := True
        UOP.REGFILEREADCMD.addressRs1 := INSTRUCTION(rs1Range).asUInt
        UOP.REGFILEREADCMD.validRs2 := True
        UOP.REGFILEREADCMD.addressRs2 := INSTRUCTION(rs2Range).asUInt
        // UOP.RS1       := INSTRUCTION(rs1Range).asUInt
        // UOP.RS2       := INSTRUCTION(rs2Range).asUInt

        UOP.RD        := INSTRUCTION(rdRange).asUInt
        UOP.IS_BR     := False
        UOP.ALU       := True
        UOP.FUNCT3    := INSTRUCTION(funct3Range)
        UOP.FUNCT7    := INSTRUCTION(funct7Range)
        UOP.IS_J_TYPE := False
        UOP.IS_U_TYPE := False
        UOP.IS_S_TYPE := False
      } 
      // I-type
      is(ADDI, SLTI, SLTIU, XORI ,ORI,  ANDI, SLLI, SRLI, SRAI, JALR) {
        UOP.SEXT   := IMM(INSTRUCTION).i_sext

        UOP.REGFILEREADCMD.validRs1 := True
        UOP.REGFILEREADCMD.addressRs1 := INSTRUCTION(rs1Range).asUInt
        UOP.REGFILEREADCMD.validRs2 := False
        UOP.REGFILEREADCMD.addressRs2.assignDontCare()
        // UOP.RS1    := INSTRUCTION(rs1Range).asUInt
        // UOP.RS2.assignDontCare()
        
        UOP.RD     := INSTRUCTION(rdRange).asUInt
        UOP.IS_BR  := False
        UOP.ALU    := True
        UOP.FUNCT3 := INSTRUCTION(funct3Range)
        UOP.FUNCT7.assignDontCare()
        UOP.IS_J_TYPE := False
        UOP.IS_U_TYPE := False
        UOP.IS_S_TYPE := False
      }
      is(BEQ,BNE ,BLT ,BGE ,BLTU,BGEU ) {
        UOP.SEXT   := IMM(INSTRUCTION).b_sext

        UOP.REGFILEREADCMD.validRs1 := True
        UOP.REGFILEREADCMD.addressRs1 := INSTRUCTION(rs1Range).asUInt
        UOP.REGFILEREADCMD.validRs2 := True
        UOP.REGFILEREADCMD.addressRs2 :=  INSTRUCTION(rs2Range).asUInt
        // UOP.RS1    := INSTRUCTION(rs1Range).asUInt
        // UOP.RS2    := INSTRUCTION(rs2Range).asUInt
        
        UOP.RD.assignDontCare()
        UOP.IS_BR  := True
        UOP.ALU    := ???
        UOP.FUNCT3 := INSTRUCTION(funct3Range)
        UOP.FUNCT7.assignDontCare()
        UOP.IS_J_TYPE := False
        UOP.IS_U_TYPE := False
        UOP.IS_S_TYPE := False
      }
      is(JAL) {
        UOP.SEXT   := IMM(INSTRUCTION).j_sext

        UOP.REGFILEREADCMD.validRs1 := False
        UOP.REGFILEREADCMD.addressRs1.assignDontCare()
        UOP.REGFILEREADCMD.validRs2 := False
        UOP.REGFILEREADCMD.addressRs2.assignDontCare()
        // UOP.RS1.assignDontCare()
        // UOP.RS2.assignDontCare()

        UOP.RD     := INSTRUCTION(rdRange).asUInt
        UOP.IS_BR  := False
        UOP.ALU    := ???
        UOP.FUNCT3.assignDontCare()
        UOP.FUNCT7.assignDontCare()
        UOP.IS_J_TYPE := True
        UOP.IS_U_TYPE := False
        UOP.IS_S_TYPE := False
      }
      is(LUI,AUIPC) {
        UOP.SEXT   := INSTRUCTION(31 downto 12).asSInt

        UOP.REGFILEREADCMD.validRs1 := False
        UOP.REGFILEREADCMD.addressRs1.assignDontCare()
        UOP.REGFILEREADCMD.validRs2 := False
        UOP.REGFILEREADCMD.addressRs2.assignDontCare()
        // UOP.RS1.assignDontCare()
        // UOP.RS2.assignDontCare()

        UOP.RD     := INSTRUCTION(rdRange).asUInt
        UOP.IS_BR  := False
        UOP.ALU    := ???
        UOP.FUNCT3.assignDontCare()
        UOP.FUNCT7.assignDontCare()
        UOP.IS_J_TYPE := False
        UOP.IS_U_TYPE := True
        UOP.IS_S_TYPE := False
      }
      is(LB,LH ,LW ,LBU,LHU) {
        UOP.SEXT   := IMM(INSTRUCTION).i_sext

        UOP.REGFILEREADCMD.validRs1 := True
        UOP.REGFILEREADCMD.addressRs1 := INSTRUCTION(rs1Range).asUInt
        UOP.REGFILEREADCMD.validRs2 := False
        UOP.REGFILEREADCMD.addressRs2.assignDontCare()
        // UOP.RS1    := INSTRUCTION(rs1Range).asUInt
        // UOP.RS2.assignDontCare()

        UOP.RD     := INSTRUCTION(rdRange).asUInt
        UOP.IS_BR  := False
        UOP.ALU    := ???
        UOP.FUNCT3 := INSTRUCTION(funct3Range)
        UOP.FUNCT7.assignDontCare()
        UOP.IS_J_TYPE := False
        UOP.IS_U_TYPE := False
        UOP.IS_S_TYPE := False
      }
      is(SB,SH,SW) {
        UOP.SEXT   := IMM(INSTRUCTION).s_sext

        UOP.REGFILEREADCMD.validRs1 := True
        UOP.REGFILEREADCMD.addressRs1 := INSTRUCTION(rs1Range).asUInt
        UOP.REGFILEREADCMD.validRs2 := True
        UOP.REGFILEREADCMD.addressRs2 :=  INSTRUCTION(rs2Range).asUInt
        // UOP.RS1    := INSTRUCTION(rs1Range).asUInt
        // UOP.RS2    := INSTRUCTION(rs2Range).asUInt

        UOP.RD.assignDontCare()
        UOP.IS_BR  := False
        UOP.ALU    := ???
        UOP.FUNCT3 := INSTRUCTION(funct3Range)
        UOP.FUNCT7.assignDontCare()
        UOP.IS_J_TYPE := False
        UOP.IS_U_TYPE := False
        UOP.IS_S_TYPE := True
      }
      // is(ECALL,EBREAK) {
      //   UOP.SEXT   := ???
      //   UOP.RS1    := ???
      //   UOP.RS2    := ???
      //   UOP.RD     := ???
      //   UOP.IS_BR  := ???
      //   UOP.ALU    := ???
      //   UOP.FUNCT3 := ???
      //   UOP.FUNCT7 := ???
      //   UOP.IS_J_TYPE :=  ???
      //   UOP.IS_U_TYPE := ???
      //   UOP.IS_S_TYPE := ???
      // }
      // is(FENCE) {

      // }
      default {
        UOP.SEXT   := S"0"
        // UOP.RS1    := U"0"
        // UOP.RS2.assignDontCare()
        UOP.RD     := U"0"
        UOP.IS_BR  := False
        UOP.ALU    := True
        UOP.FUNCT3 := INSTRUCTION(funct3Range)
        UOP.FUNCT7 := INSTRUCTION(funct7Range)
      }
    }
    
  }
        
}       
        
