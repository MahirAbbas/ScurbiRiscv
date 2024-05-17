package scurbiriscv.exu


import spinal.core._
import spinal.lib._
import spinal.lib.misc.pipeline._


object ExecutePipeline extends AreaObject {
  val REGREADRSP = Payload(RegFileReadRsp()) 
  val BRANCH = Payload(Bool())
  val BRANCH_TARGET = Payload(UInt(32 bits))
  val ALU_RESULT = Payload(Bits(32 bits))
  // val RS1DATA = Payload(Bits(32 bits))
  // val RS2DATA = Payload(Bits (32 bits))
  
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
    val is_rs2 = Bool()
    val is_sext = Bool()
    is_rs2 := UOP.is_br || UOP.is_s_type || UOP.is_r_type
    is_sext := UOP.is_i_type || UOP.is_s_type || UOP.is_br ||UOP.is_u_type || UOP.is_j_type


    
    val aluExecute = new Area {
      alu.io.src1 := REGREADRSP.rs1Data
      alu.io.src2 := is_rs2.mux(
        True -> REGREADRSP.rs2Data,
        False -> UOP.sext.asBits
      )
      alu.io.funct3 := UOP.funct3
      alu.io.funct7 := UOP.funct7

      ALU_RESULT := alu.io.alu_result

    }
    
    val branch = new Area {
      
    }
    


  }



  // TODO: Take microOp, 
  // read Registers, (Pipeline Node)
  // execute/branch, (Another Node)
  // sort out hazards 

  
}
