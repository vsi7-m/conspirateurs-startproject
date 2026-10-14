package model.logic

import model.{GamePiece, GameState, Grid, StandardPiece}

object JumpBoostLogic:

  def tryExecute(row: Int, col: Int, grid: Grid[GamePiece], gameState: GameState): Boolean =
    val currentPlayer = gameState.currentPlayer
    grid.get(row, col) match
      case Some(piece: StandardPiece) if piece.color == currentPlayer.color =>
        piece.activateJumpBoost()
        println(s"Jump Boost geactiveerd op stuk op ($row, $col)")
        currentPlayer.useJumpBoost()
        gameState.deselect()
        true
        
      case Some(_) =>
        println("Je kan alleen je eigen stukken boosten!")
        false

      case None =>
        println("Hier staat geen stuk.")
        false
