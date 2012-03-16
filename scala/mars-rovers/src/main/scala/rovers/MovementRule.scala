package rovers

/**
 * Movement rules
 * 
 * AllowCross - rovers can take same positions (default)
 * DenyCross - rover can take occupied position
 * 
 * Can be implicitly passed to Solution.run method
 */
sealed trait MovementRule
case object AllowCross extends MovementRule
case object DenyCross extends MovementRule