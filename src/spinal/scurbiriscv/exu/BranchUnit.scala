package scurbiriscv.exu

import spinal.core._



class BranchUnit() extends Component {
  val io = new Bundle {
    val rs1 = in port Bits(32 bits)
    val rs2 = in port Bits(32 bits)
    val funct3 = in port Bits(3 bits)
    val imm = in port Bits(32 bits)
    val pc = in port UInt(32 bits)
    val branch = out port Bool()
    val branch_target = out port UInt(32 bits)
  }
  io.branch_target := io.imm.asUInt + io.pc
  
  switch(io.funct3) {
    // BEQ
    is(B"000") {
      io.branch := (io.rs1 === io.rs2)
    }
    // BNE
    is(B"001") {
      io.branch := !(io.rs1 === io.rs2) 
    }
    // BLT
    is(B"100") {
      io.branch := (io.rs1.asSInt < io.rs2.asSInt) 
    }
    // BGE
    is(B"101") {
      io.branch := (io.rs1.asSInt >= io.rs2.asSInt)
    }
    // BLTU
    is(B"110") {
      io.branch := (io.rs1.asUInt < io.rs2.asUInt) 
    }
    // BGEU
    is(B"111") {
      io.branch := (io.rs1.asUInt >= io.rs2.asUInt)
    }
  }
  
}