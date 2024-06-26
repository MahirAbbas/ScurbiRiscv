package scurbiriscv.riscv

import spinal.core._

object Riscv extends AreaObject {
  val XLEN = 64
}

object Rv32i extends AreaObject {
  val LUI                = M"-------------------------0110111"
  val AUIPC              = M"-------------------------0010111"
  //I-Type
  val JALR               =  M"-----------------000-----1100111"
  //J-Type
  val JAL                =  M"-------------------------1101111"
  //B-Type
  val BEQ                =  M"-----------------000-----1100011"
  val BNE                =  M"-----------------001-----1100011"
  val BLT                =  M"-----------------100-----1100011"
  val BGE                =  M"-----------------101-----1100011"
  val BLTU               =  M"-----------------110-----1100011"
  val BGEU               =  M"-----------------111-----1100011"

  val LB                 = M"-----------------000-----0000011"
  val LH                 = M"-----------------001-----0000011"
  val LW                 = M"-----------------010-----0000011"
  val LBU                = M"-----------------100-----0000011"
  val LHU                = M"-----------------101-----0000011"

  val SB                 = M"-----------------000-----0100011"
  val SH                 = M"-----------------001-----0100011"
  val SW                 = M"-----------------010-----0100011"

  val ADDI               = M"-----------------000-----0010011"
  val SLTI               = M"-----------------010-----0010011"
  val SLTIU              = M"-----------------011-----0010011"
  val XORI               = M"-----------------100-----0010011"
  val ORI                = M"-----------------110-----0010011"
  val ANDI               = M"-----------------111-----0010011"
  val SLLI               = M"0000000----------001-----0010011"
  val SRLI               = M"0000000----------101-----0010011"
  val SRAI               = M"0100000----------101-----0010011"

  val ADD                = M"0000000----------000-----0110011"
  val SUB                = M"0100000----------000-----0110011"
  val SLL                = M"0000000----------001-----0110011"
  val SLT                = M"0000000----------010-----0110011"
  val SLTU               = M"0000000----------011-----0110011"
  val XOR                = M"0000000----------100-----0110011"
  val SRL                = M"0000000----------101-----0110011"
  val SRA                = M"0100000----------101-----0110011"
  val OR                 = M"0000000----------110-----0110011"
  val AND                = M"0000000----------111-----0110011"
  
  val FENCE              = M"-----------------000-----0001111"
  val EBREAK             = M"00000000000100000000000001110011"
  val ECALL              = M"00000000000000000000000001110011"
  
  val NOP                = M"00000000000000000000000000010011" // ADDI x0, x0, 0
}

object Rv64i extends AreaObject {
  val LWU                = M"-----------------110-----0000011"
  val LD                 = M"-----------------011-----0000011"
  val SD                 = M"-----------------011-----0100011"

  val SLLI               = M"000000-----------001-----0010011"
  val SRLI               = M"000000-----------101-----0010011"
  val SRAI               = M"010000-----------101-----0010011"

  val ADDIW              = M"-----------------000-----0011011"
  val SLLIW              = M"000000-----------001-----0011011"
  val SRLIW              = M"000000-----------101-----0011011"
  val SRAIW              = M"010000-----------101-----0011011"

  val ADDW               = M"0000000----------000-----0111011"
  val SUBW               = M"0100000----------000-----0111011"
  val SLLW               = M"0000000----------001-----0111011"
  val SRLW               = M"0000000----------101-----0111011"
  val SRAW               = M"0100000----------101-----0111011"
}


