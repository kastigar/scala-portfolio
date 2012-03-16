package rovers

/**
 * Object for parsing input string.
 * 
 * Expects that first line is top-right corner
 * coordinates of plateau. Format is &lt;number&gt; &lt;space&gt; &lt;number&gt;.
 * Rest is two-lined rover's data. First line is for rover state (position and heading).
 * Format is &lt;number&gt; &lt;space&gt; &lt;number&gt; &lt;space&gt; ( 'N' | 'S' | 'W' | 'E' ).
 * Second line is a series of instructions. Format is ( 'L' | 'R' | 'M' )...
 */
object PlateauSpec {
  // Extractor for right-top corner coordinates
  val RTCornerSpec = """^(\d+) (\d+)$""".r
  // Extractor for rover's state
  val RoverStateSpec = """^(\d+) (\d+) ([NSWE])$""".r

  // Extractor for heading character
  object DirectionSpec {
    def unapply(str: String): Option[Heading] = str match {
      case "N" => Some(North)
      case "S" => Some(South)
      case "W" => Some(West)
      case "E" => Some(East)
      case _ => None
    }
  }

  // Extractor for instructions line
  object InstructionsSpec {
    val SpecRegex = """^[LRM]+$""".r

    def unapply(str: String): Option[List[Instruction]] =
      try {
        Some(str.toList map {
          case 'L' => SpinLeft
          case 'R' => SpinRight
          case 'M' => MoveForward
          case _ => sys.error("")
        })
      } catch {
        case _ => None
      }
  }

  def unapply(str: String): Option[Plateau] = str.lines.toList match {
    // Check that first line matches RTCornerSpec
    case RTCornerSpec(x, y) :: roversLines =>
      try {
        // match rest lines. First, group it and then...
        val rovers = roversLines grouped 2 map {
          // ...check with appropriate extractors
          case RoverStateSpec(x, y, DirectionSpec(dir)) :: InstructionsSpec(ins) :: Nil =>
            Rover(RoverState(dir, x toInt, y toInt), ins)
          // if doesn't match then throw Exception
          case _ => sys.error("")
        }
        // create plateau instance
        Some(new Plateau(x toInt, y toInt, rovers toList))
      } catch {
        case _ => None
      }
    case _ => None
  } 
}
