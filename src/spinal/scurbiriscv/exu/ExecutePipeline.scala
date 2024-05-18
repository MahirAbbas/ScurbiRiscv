package scurbiriscv.exu


import spinal.core._
import spinal.lib._
import spinal.lib.misc.pipeline._
import scurbiriscv.Global.PC


object ExecutePipeline extends AreaObject {
  val REGREADRSP = Payload(RegFileReadRsp()) 
  val BRANCH = Payload(Bool())
  val BRANCH_TARGET = Payload(UInt(32 bits))
  val ALU_RESULT = Payload(Bits(32 bits))
  // val RS1DATA = Payload(Bits(32 bits))
  // val RS2DATA = Payload(Bits (32 bits))
  val RD_DATA = Payload(Bits(32 bits))
  val RD = Payload(UInt(5 bits))
  
}

object Values extends AreaObject {

}

case class ExecutePipeline() extends Area {
  
  import scurbiriscv.frontend.InstructionPacket._
  import scurbiriscv.frontend.Decode._
  import scurbiriscv.exu.ExecutePipeline._
  


  val regFile = new RegFile()
  val alu = new IntAlu()
  val branchUnit = new BranchUnit()
  
  
  val regRead = Node()
  val execute = Node()

  
  val registerReadArea = new regRead.Area {
    regFile.io.reads := UOP.regfilereadcmd
    REGREADRSP := regFile.io.readRsp

  }

  val executeArea = new execute.Area {
    val is_rs2 = UOP.is_br || UOP.is_s_type || UOP.is_r_type
    val is_sext = UOP.is_i_type || UOP.is_s_type || UOP.is_br || UOP.is_u_type || UOP.is_j_type

  // val sext            = (SInt(32 bits))
  // // use something like val is_rs2 = Bool()
  // val rs1             = (UInt(5 bits))
  // val rs2             = (UInt(5 bits))
  // val rd              = (UInt(5 bits))
  // val is_br           = (Bool())
  // val alu             = (Bool())
  // val funct3          = (Bits(3 bits))
  // val funct7          = (Bits(7 bits))
  // val is_i_type       = Bool()
  // val is_r_type       = Bool()
  // val is_j_type       = (Bool())
  // val is_u_type       = (Bool())
  // val is_s_type       = (Bool())
  // val regfilereadcmd  = (RegFileReadCmd())

    
    val aluExecute = new Area {
      when(UOP.alu) {
        alu.io.src1 := REGREADRSP.rs1Data
        alu.io.src2 := is_rs2.mux(
          True -> REGREADRSP.rs2Data,
          False -> UOP.sext.asBits
        )
        alu.io.funct3 := UOP.funct3
        alu.io.funct7 := UOP.funct7

        RD_DATA := alu.io.alu_result
        RD := UOP.rd
      }

    }
    
    val branch = new Area {
      when(UOP.is_br) {
        branchUnit.io.rs1 := REGREADRSP.rs1Data
        branchUnit.io.rs2 := REGREADRSP.rs2Data
        branchUnit.io.funct3 := UOP.funct3
        branchUnit.io.pc := PC
        branchUnit.io.imm := UOP.sext.asBits
        // val should_branch = branchUnit.io.branch
        BRANCH_TARGET := branchUnit.io.branch_target
        BRANCH := True
      }
    }
    val jump = new Area {
      when(UOP.is_j_type) {
        RD := UOP.rd
        RD_DATA := PC.asBits
        BRANCH := True
        BRANCH_TARGET := (PC + UOP.sext.asUInt)
      }
    }

    val mem = new Area {
      
    }
      
    


  }



  // TODO: Take microOp, 
  // read Registers, (Pipeline Node)
  // execute/branch, (Another Node)
  // sort out hazards 

  
}
