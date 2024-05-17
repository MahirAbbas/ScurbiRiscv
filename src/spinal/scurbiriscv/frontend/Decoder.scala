package scurbiriscv.frontend

import spinal.core._
import spinal.lib._
import scurbiriscv.riscv._
import scurbiriscv.exu._
import spinal.lib.misc.pipeline._

class Decoder() extends Area{
  import Rv32i._
  import Const._
  // import InstructionPacket._
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
        UOP.sext.assignDontCare()

        UOP.regfilereadcmd.validRs1 := True
        UOP.regfilereadcmd.addressRs1 := INSTRUCTION(rs1Range).asUInt
        UOP.regfilereadcmd.validRs2 := True
        UOP.regfilereadcmd.addressRs2 := INSTRUCTION(rs2Range).asUInt
        // UOP.RS1       := INSTRUCTION(rs1Range).asUInt
        // UOP.RS2       := INSTRUCTION(rs2Range).asUInt

        UOP.rd        := INSTRUCTION(rdRange).asUInt
        UOP.is_br     := False
        UOP.alu       := True
        UOP.funct3    := INSTRUCTION(funct3Range)
        UOP.funct7    := INSTRUCTION(funct7Range)
        UOP.is_j_type := False
        UOP.is_u_type := False
        UOP.is_s_type := False
        UOP.is_r_type := True
        UOP.is_i_type := False
      } 
      // I-type
      is(ADDI, SLTI, SLTIU, XORI ,ORI,  ANDI, SLLI, SRLI, SRAI, JALR) {
        UOP.sext   := IMM(INSTRUCTION).i_sext

        UOP.regfilereadcmd.validRs1 := True
        UOP.regfilereadcmd.addressRs1 := INSTRUCTION(rs1Range).asUInt
        UOP.regfilereadcmd.validRs2 := False
        UOP.regfilereadcmd.addressRs2.assignDontCare()
        // UOP.RS1    := INSTRUCTION(rs1Range).asUInt
        // UOP.RS2.assignDontCare()
        
        UOP.rd     := INSTRUCTION(rdRange).asUInt
        UOP.is_br  := False
        UOP.alu    := True
        UOP.funct3 := INSTRUCTION(funct3Range)
        UOP.funct7.assignDontCare()
        UOP.is_j_type := False
        UOP.is_u_type := False
        UOP.is_s_type := False
        UOP.is_r_type := False
        UOP.is_i_type := True
      }
      is(BEQ,BNE ,BLT ,BGE ,BLTU,BGEU ) {
        UOP.sext   := IMM(INSTRUCTION).b_sext

        UOP.regfilereadcmd.validRs1 := True
        UOP.regfilereadcmd.addressRs1 := INSTRUCTION(rs1Range).asUInt
        UOP.regfilereadcmd.validRs2 := True
        UOP.regfilereadcmd.addressRs2 :=  INSTRUCTION(rs2Range).asUInt
        // UOP.RS1    := INSTRUCTION(rs1Range).asUInt
        // UOP.RS2    := INSTRUCTION(rs2Range).asUInt
        
        UOP.rd.assignDontCare()
        UOP.is_br  := True
        UOP.alu    := ???
        UOP.funct3 := INSTRUCTION(funct3Range)
        UOP.funct7.assignDontCare()
        UOP.is_j_type := False
        UOP.is_u_type := False
        UOP.is_s_type := False
        UOP.is_r_type := False
        UOP.is_i_type := False
      }
      is(JAL) {
        UOP.sext   := IMM(INSTRUCTION).j_sext

        UOP.regfilereadcmd.validRs1 := False
        UOP.regfilereadcmd.addressRs1.assignDontCare()
        UOP.regfilereadcmd.validRs2 := False
        UOP.regfilereadcmd.addressRs2.assignDontCare()
        // UOP.RS1.assignDontCare()
        // UOP.RS2.assignDontCare()

        UOP.rd     := INSTRUCTION(rdRange).asUInt
        UOP.is_br  := False
        UOP.alu    := ???
        UOP.funct3.assignDontCare()
        UOP.funct7.assignDontCare()
        UOP.is_j_type := True
        UOP.is_u_type := False
        UOP.is_s_type := False
        UOP.is_r_type := False
        UOP.is_i_type := False
      }
      is(LUI,AUIPC) {
        UOP.sext   := INSTRUCTION(31 downto 12).asSInt

        UOP.regfilereadcmd.validRs1 := False
        UOP.regfilereadcmd.addressRs1.assignDontCare()
        UOP.regfilereadcmd.validRs2 := False
        UOP.regfilereadcmd.addressRs2.assignDontCare()
        // UOP.RS1.assignDontCare()
        // UOP.RS2.assignDontCare()

        UOP.rd     := INSTRUCTION(rdRange).asUInt
        UOP.is_br  := False
        UOP.alu    := ???
        UOP.funct3.assignDontCare()
        UOP.funct7.assignDontCare()
        UOP.is_j_type := False
        UOP.is_u_type := True
        UOP.is_s_type := False
        UOP.is_r_type := False
        UOP.is_i_type := False
      }
      is(LB,LH ,LW ,LBU,LHU) {
        UOP.sext   := IMM(INSTRUCTION).i_sext

        UOP.regfilereadcmd.validRs1 := True
        UOP.regfilereadcmd.addressRs1 := INSTRUCTION(rs1Range).asUInt
        UOP.regfilereadcmd.validRs2 := False
        UOP.regfilereadcmd.addressRs2.assignDontCare()
        // UOP.RS1    := INSTRUCTION(rs1Range).asUInt
        // UOP.RS2.assignDontCare()

        UOP.rd     := INSTRUCTION(rdRange).asUInt
        UOP.is_br  := False
        UOP.alu    := ???
        UOP.funct3 := INSTRUCTION(funct3Range)
        UOP.funct7.assignDontCare()
        UOP.is_j_type := False
        UOP.is_u_type := False
        UOP.is_s_type := False
        UOP.is_r_type := False
        UOP.is_i_type := False
      }
      is(SB,SH,SW) {
        UOP.sext   := IMM(INSTRUCTION).s_sext

        UOP.regfilereadcmd.validRs1 := True
        UOP.regfilereadcmd.addressRs1 := INSTRUCTION(rs1Range).asUInt
        UOP.regfilereadcmd.validRs2 := True
        UOP.regfilereadcmd.addressRs2 :=  INSTRUCTION(rs2Range).asUInt
        // UOP.RS1    := INSTRUCTION(rs1Range).asUInt
        // UOP.RS2    := INSTRUCTION(rs2Range).asUInt

        UOP.rd.assignDontCare()
        UOP.is_br  := False
        UOP.alu    := ???
        UOP.funct3 := INSTRUCTION(funct3Range)
        UOP.funct7.assignDontCare()
        UOP.is_j_type := False
        UOP.is_u_type := False
        UOP.is_s_type := True
        UOP.is_r_type := False
        UOP.is_i_type := False
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
        UOP.sext   := S"0"
        // Uop.rs1    := U"0"
        // Uop.rs2.assignDontCare()
        UOP.rd     := U"0"
        UOP.is_br  := False
        UOP.alu    := True
        UOP.funct3 := INSTRUCTION(funct3Range)
        UOP.funct7 := INSTRUCTION(funct7Range)
      }
    }
    
  }
        
}       
