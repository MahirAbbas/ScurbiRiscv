package scurbiriscv.frontend

import spinal.core._
import spinal.lib._
import spinal.lib.misc.pipeline._

import scurbiriscv.Global._


case class JumpCmd() extends Bundle {
  val pc = PC()
}
