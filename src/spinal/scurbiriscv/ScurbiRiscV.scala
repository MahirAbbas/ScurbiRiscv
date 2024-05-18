package scurbiriscv

import spinal.core._
import scurbiriscv.frontend.Decode._
import scurbiriscv.frontend.Decoder
import spinal.lib.misc.pipeline.StageLink

// Hardware definition
// 

case class ScurbiRiscV() extends Component {
  val decode = new Decoder()
  
  // val d2e = StageLink(decode.decode.up, decode.decode.down)
}