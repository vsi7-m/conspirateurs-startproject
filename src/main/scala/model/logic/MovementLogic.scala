package model.logic

import model.{GamePiece, Grid}

import scala.collection.mutable

object MovementLogic:
  def canReachByJumping(
    start: (Int, Int), 
    target: (Int, Int), 
    grid: Grid[GamePiece], 
    allowDiagonal: Boolean
  ): Boolean =
    var todo = List(start) // Posities die we nog moeten checken
    var visited = Set[(Int, Int)]() 

    while todo.nonEmpty do
      val current = todo.head
      todo = todo.tail
      if current == target then return true
      
      if !visited.contains(current) then
        visited += current
        val nextJumps = getValidSingleJumps(current, grid, allowDiagonal)
        todo = todo ++ nextJumps
        
    false
  
  private def getValidSingleJumps(
    pos: (Int, Int), 
    grid: Grid[GamePiece], 
    allowDiagonal: Boolean
  ): List[(Int, Int)] =
    val (r, c) = pos

    // Alle 8 richtingen
    val allDirections = List(
      (-1, -1), (-1, 0), (-1, 1),
      (0, -1), (0, 1),
      (1, -1), (1, 0), (1, 1)
    )

    val allowedDirections = 
      if allowDiagonal then allDirections
      else allDirections.filter((dr, dc) => dr == 0 || dc == 0)

    for
      (dr, dc) <- allowedDirections

      midR = r + dr
      midC = c + dc
      landR = r + (dr * 2)
      landC = c + (dc * 2)

      // Check of landingsplek valide is op het bord
      if grid.isValid(landR, landC)
      // Check of er een stuk tussenin staat (om over te springen)
      if grid.get(midR, midC).isDefined
      // Check of de landingsplek leeg is
      if grid.get(landR, landC).isEmpty
      
    yield (landR, landC) // 'yield' verzamelt automatisch alle geldige (landR, landC) paren in een List
