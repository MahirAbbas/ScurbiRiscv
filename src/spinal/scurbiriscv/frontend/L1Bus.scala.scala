package scurbiriscv.frontend

import spinal.core._
import spinal.lib._
import spinal.lib.pipeline.Stageable

case class FetchL1Cmd() extends Bundle {
  val address = UInt(32 bits)
  val io = Bool()
}


case class FetchL1Rsp() extends Bundle {
  val data = UInt(32 bits)
}

case class FetchL1Bus() extends Bundle with IMasterSlave {
  val cmd = Stream(FetchL1Cmd())
  val rsp = Stream(FetchL1Rsp())
  
  override def asMaster(): Unit = {
    master(cmd)
    slave(rsp)
  }
  
}
