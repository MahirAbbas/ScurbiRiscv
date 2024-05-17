package scurbiriscv.schedule

import spinal.core._
import spinal.lib._
// import scurbiriscv.decode._
import scurbiriscv.Global._


object Ages {
  val STAGE = 10
  val NOT_PREDICTION = 1
  val FETCH = 0
  val DECODE = 1000
  // Execution Units
  val EU = 2000
  val TRAP = 3000
}
