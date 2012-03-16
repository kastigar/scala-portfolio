package rovers

/**
 * Main object for solution.
 * 
 * Use Solution.run to find a solution.
 * Method gets input string compatible with PlateauSpec
 * and returns List of final rovers' states
 */
object Solution {

  def run(data: String)(implicit rule: MovementRule = AllowCross): Option[String] =
    data match {
      // uses PlateauSpec extractor
      case PlateauSpec(plateau) => Some(
          plateau.finalStates map {s => "%d %d %s" format (s.x, s.y, s.heading.toString.charAt(0))} mkString "\n"
      )
      // if doesn't match PlateauSpec
      case _ => None
    }
}
