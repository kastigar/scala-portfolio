package rovers

import scala.annotation.tailrec

/**
 * Class for plateau
 */
class Plateau(topY: Int, rightX: Int, rovers: List[Rover]) {

  // Contains runtime states of rovers
  private var curStates: Map[RoverState, RoverState] = Map.empty

  // Check if position is not out of plateau
  private def inBound(x: Int, y:Int): Boolean = x >= 0 && x <= rightX && y >= 0 && y <= topY
  // Check if position is occupied by another rover
  private def isBusy(x: Int, y:Int): Boolean  = curStates.values exists { s => s.x == x && s.y == y }

  // Check if position is available according to MovementRule
  private def available(x: Int, y:Int)(implicit rule: MovementRule) = rule match {
    case AllowCross => inBound(x, y)
    case DenyCross  => inBound(x, y) && !isBusy(x, y)
  }

  // Get new rover state by provided instructions
  private def moveRover(state: RoverState, instructions: List[Instruction])(implicit rule: MovementRule) = {

    @tailrec
    def doStep(state: RoverState, instructions: List[Instruction]): RoverState = (state, instructions) match {
      // No instructions? Stop
      case (state, Nil) => state
      // Change heading of rover
      case (RoverState(heading, x, y), SpinLeft :: ins)  => doStep(RoverState(heading.left,  x, y), ins)
      // Change heading of rover
      case (RoverState(heading, x, y), SpinRight :: ins) => doStep(RoverState(heading.right, x, y), ins)
      // Move rover according to heading
      case (RoverState(heading, x, y), MoveForward :: ins) => heading match {
        case North if available(x, y+1) => doStep(RoverState(heading, x, y+1), ins)
        case East  if available(x+1, y) => doStep(RoverState(heading, x+1, y), ins)
        case South if available(x, y-1) => doStep(RoverState(heading, x, y-1), ins)
        case West  if available(x-1, y) => doStep(RoverState(heading, x-1, y), ins)
        // If position is not available then skip instruction
        case _ => doStep(state, ins)
      }
    }

    doStep(state, instructions)
  }

  def finalStates(implicit rule: MovementRule) = {
    // Reset current states
    curStates = rovers map (r => r.state -> r.state) toMap

    rovers map { rover =>
      // get new state
      val newState = moveRover(rover.state, rover.instructions)
      // update rover state
      curStates = curStates updated (rover.state, newState)
      newState
    }
  }
}

