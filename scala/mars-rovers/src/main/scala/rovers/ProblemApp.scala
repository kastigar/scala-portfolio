package rovers

object ProblemApp extends App {

  val data = """|5 5
                |1 2 N
                |LMLMLMLMM
                |3 3 E
                |MMRMMRMRRM""" stripMargin '|'

  val res = Solution.run(data)

  res match {
    case None => println("ERROR: wrong input data")
    case Some(result) => println(result)
  }
}