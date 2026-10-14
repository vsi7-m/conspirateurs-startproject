package model.logic

import model.{GamePiece, GameState, Grid, TemporaryPiece, PlayerColor}

object LostSonLogic:

  def tryExecute(row: Int, col: Int, grid: Grid[GamePiece], gameState: GameState): Boolean =
    val currentPlayer = gameState.currentPlayer
    
    val opponentColor = if currentPlayer.color == model.PlayerColor.Yellow 
      then PlayerColor.Brown
      else PlayerColor.Yellow
    
    if grid.get(row, col).isEmpty then
      // Maak de Lost Son aan
      val newPiece = new TemporaryPiece(opponentColor)
      // Plaats op het bord
      grid.set(row, col, Some(newPiece))
      println("Speler ${currentPlayer.color} plaatst een Lost Son voor $opponentColor op ($row, $col).")
      currentPlayer.useLostSon()
      gameState.switchTurn(grid)
      true
    else
      println("Je moet de Lost Son op een leeg vakje plaatsen.")
      false
