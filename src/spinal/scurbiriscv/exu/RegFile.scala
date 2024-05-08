package scurbiriscv.exu

import spinal.core._
import spinal.lib._
import scurbiriscv.frontend.InstructionPacket._

case class RegFileWriteCmd() extends Bundle {
  val valid = Bool()
  val address = UInt(5 bits)
  val data = Bits(32 bits)
}

case class RegFileReadCmd() extends Bundle {
  val validRs1 = Bool()
  val addressRs1 = UInt(5 bits)
  val validRs2 = Bool()
  val addressRs2 = UInt(5 bits)
  
}

case class RegFileReadRsp() extends Bundle {
  val rs1Data = Bits(32 bits)
  val rs2Data = Bits(32 bits)
}


case class RegFile() extends Component{
  
  val io = new Bundle {
    val reads = in port RegFileReadCmd()
    val writes = in port RegFileWriteCmd()
    val readRsp = out port RegFileReadRsp()
  }
  
  val regfile = new Area {
    val mem = Mem(Bits(32 bits), 32)
    val rs1 = mem.readSyncPort
    val rs2 = mem.readSyncPort
    val rd  = mem.writePort
  }
  
  val write = new Area {
    regfile.rd.valid := io.writes.valid 
    regfile.rd.payload.address := io.writes.address
    regfile.rd.payload.data:= io.writes.data
  }
  val read = new Area {
    // io.readRsp.rs1Data := regfile.rs1
    regfile.rs1.cmd.valid := io.reads.validRs1
    regfile.rs2.cmd.valid := io.reads.validRs2
    regfile.rs1.cmd.payload := io.reads.addressRs1
    regfile.rs2.cmd.payload := io.reads.addressRs2
    io.readRsp.rs1Data := regfile.rs1.rsp
    io.readRsp.rs2Data := regfile.rs2.rsp
  }

  
}
