package scurbiriscv.frontend

import spinal.core._
import spinal.lib._
import spinal.lib.misc.pipeline._

case class FrontendPipeline() extends Area {
  val pc = new Pc()
  val icache = new ICache()
  val decoder = new Decode()
  
  val c01 = CtrlLink(pc.pcNode, icache.icacheNode)
  val c12 = CtrlLink(icache.icacheNode, decoder.decodeNode)

  val s01 = StageLink(pc.pcNode, icache.icacheNode)
  val s12 = StageLink(icache.icacheNode, decoder.decodeNode)
}