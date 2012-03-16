package rovers

/**
 * Rover representation (initial state and instructions from NASA)
 */
case class Rover(state: RoverState, instructions: List[Instruction])