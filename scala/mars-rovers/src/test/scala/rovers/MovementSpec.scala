package rovers

import org.specs2._

class MovementSpec extends Specification { def is =

  "This is a specification to check the movement restrictions"              ^
                                                                            p^
  "Rover should not cross"                                                  ^
    "top edge"                                                              ! crossEdge(topRover)^
    "right edge"                                                            ! crossEdge(rightRover)^
    "bottom edge"                                                           ! crossEdge(bottomRover)^
    "left edge"                                                             ! crossEdge(leftRover)^
                                                                            endp^
  "If Rover2 is in front of Rover1 then Rover1 should"                      ^
    "change position if AllowCross"                                         ! allowCrossMovement^
    "not change position if DenyCross"                                      ! denyCrossMovement^
                                                                            endp^
  "If Rover3 will be in front of Rover1 then Rover4 should"                 ^
    "change position if AllowCross"                                         ! allowCrossMovement2^
    "not change position if DenyCross"                                      ! denyCrossMovement2^
                                                                            end


  def allowCrossMovement =
    getPlateau(rover1, rover2).finalStates(AllowCross).head must_!= rover1.state

  def denyCrossMovement =
    getPlateau(rover1, rover2).finalStates(DenyCross).head must_== rover1.state

  def allowCrossMovement2 =
    getPlateau(rover3, rover4).finalStates(AllowCross)(1) must_!= rover4.state

  def denyCrossMovement2 =
    getPlateau(rover3, rover4).finalStates(DenyCross)(1) must_== rover4.state

  def crossEdge(rover: Rover) =
    getPlateau(rover).finalStates.head must_== rover.state

  implicit val movementRule = AllowCross
  val rightX = 5
  val topY = 5

  def getPlateau(rovers: Rover*): Plateau = new Plateau(rightX, topY, rovers toList)

  val moveForward = MoveForward :: Nil

  val rover1 = Rover(RoverState(North, 1, 1), moveForward)
  val rover2 = Rover(RoverState(North, 1, 2), Nil)
  
  val rover3 = Rover(RoverState(North, 1, 1), moveForward)
  val rover4 = Rover(RoverState(West, 2, 2), moveForward)

  val topRover    = Rover(RoverState(North, 0, topY), moveForward)
  val rightRover  = Rover(RoverState(East, rightX, 0), moveForward)
  val bottomRover = Rover(RoverState(South, 0, 0), moveForward)
  val leftRover   = Rover(RoverState(West, 0, 0), moveForward)
}
