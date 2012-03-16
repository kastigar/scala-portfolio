package rovers

/**
 * classes for headings.
 */
sealed trait Heading {
  val left: Heading
  val right: Heading
}

case object North extends Heading {
  val left = West
  val right = East
}

case object East extends Heading {
  val left = North
  val right = South
}

case object South extends Heading {
  val left = East
  val right = West
}

case object West extends Heading {
  val left = South
  val right = North
}
