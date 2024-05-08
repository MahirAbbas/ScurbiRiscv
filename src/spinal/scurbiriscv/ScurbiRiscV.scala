package scurbiriscv

import spinal.core._
import scurbiriscv.frontend.Decode._
import scurbiriscv.frontend.Decode
import spinal.lib.misc.pipeline.StageLink

// Hardware definition
// 

case class ScurbiRiscV() extends Component {
  val decode = new Decode()
  
  // val d2e = StageLink(decode.decode.up, decode.decode.down)
}