package model

import model.logic.MovementLogic

abstract class GamePiece(val color: PlayerColor):
  def canMove(from: (Int, Int), to: (Int, Int), grid: Grid[GamePiece]): Boolean


// Gewoon stuk dat power-ups kan hebben
class StandardPiece(color: PlayerColor) extends GamePiece(color):
  var canMoveDiagonally: Boolean = true
  var isMoveRestricted: Boolean = false

  // JumpBoost logica: actief voor aantal beurten
  private var jumpBoostTurns: Int = 0
  def hasActiveJumpBoost: Boolean = jumpBoostTurns > 0

  def activateJumpBoost(): Unit =
    jumpBoostTurns = 3 // Duurt 3 beurten
  
  /** Roep dit aan NADAT het stuk gezet is */
  def tickTurn(): Unit =
    if jumpBoostTurns > 0 then jumpBoostTurns -= 1

  override def canMove(from: (Int, Int), to: (Int, Int), grid: Grid[GamePiece]): Boolean =
    val (r1, c1) = from
    val (r2, c2) = to

    if r1 == r2 && c1 == c2 then return false // Niet bewegen mag niet
    if grid.get(r2, c2).isDefined then return false // Doel moet leeg zijn

    val dr = (r2 - r1).abs
    val dc = (c2 - c1).abs
    val isStraight = dr == 0 || dc == 0
    val isDiagonal = dr == dc
    val distance = math.max(dr, dc)
    
    // Reeksen van sprongen mogen wel recht en diagonaal gaan, anders niet
    if !isStraight && !isDiagonal && distance <= 2 then return false

    // Als diagonaal en we mogen niet diagonaal
    if isDiagonal && !canMoveDiagonally then
      println("Freeze! Geen diagonale moves toegestaan.")
      return false

    if distance == 1 then
      return true // 1 stap naar leeg vakje mag altijd

    if distance == 2 then
      return canJumpOverOne(r1, c1, r2, c2, grid)
    
    if distance > 2 then
      return MovementLogic.canReachByJumping(from, to, grid, canMoveDiagonally)
    false

  private def canJumpOverOne(r1: Int, c1: Int, r2: Int, c2: Int, grid: Grid[GamePiece]): Boolean =
    val midR = (r1 + r2) / 2
    val midC = (c1 + c2) / 2
    val midPiece = grid.get(midR, midC)

    // Normale sprong: Er staat een stuk in het midden
    if midPiece.isDefined then return true

    // JumpBoost sprong: Midden is leeg, maar we hebben boost
    if midPiece.isEmpty && hasActiveJumpBoost then return true

    false 


class TemporaryPiece(color: PlayerColor) extends StandardPiece(color):
  // Levensduur in beurten (van de eigenaar)
  private var roundsLeft: Int = 3

  def age(): Boolean =
    roundsLeft -= 1
    roundsLeft <= 0
