package model.logic

import model.{GamePiece, GameState, Grid, StandardPiece}

object MoveRestrictionLogic:

  def tryExecute(row: Int, col: Int, grid: Grid[GamePiece], gameState: GameState): Boolean =
    val currentPlayer = gameState.currentPlayer
    
    grid.get(row, col) match
      case Some(piece:StandardPiece) if piece.color != currentPlayer.color =>
        piece.canMoveDiagonally = false
        piece.isMoveRestricted = true
        println(s"Vijand op ($row, $col) kan niet meer diagonaal bewegen.")
        currentPlayer.useMoveRestriction()
        gameState.deselect()
        true
        
      case Some(_) =>
        println("Je moet deze powerup op een VIJAND gebruiken!")
        false

      case None =>
        println("Hier staat geen stuk.")
        false
