package rovers

/**
 *  Classes for instructions
 */
sealed trait Instruction
case object SpinLeft extends Instruction
case object SpinRight extends Instruction
case object MoveForward extends Instruction