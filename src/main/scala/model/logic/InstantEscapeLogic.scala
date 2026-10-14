package model.logic

import model.{GamePiece, GameState, Grid}

object InstantEscapeLogic:

  def tryExecute(row: Int, col: Int, grid: Grid[GamePiece], gameState: GameState): Boolean =
    val player = gameState.currentPlayer
    
    // We kijken wat er op het gekozen vakje staat
    grid.get(row, col) match
      case Some(piece) if piece.color == player.color =>
        println(s"Instant Escape toegepast op ($row, $col)")
        // Verwijder stuk van bord
        grid.set(row, col, None)
        player.useInstantEscape()
        gameState.deselect()
        // Check winstconditie
        // Als dit het laatste stuk was dat nog op het bord stond, kan de speler gewonnen hebben
        if gameState.hasWon(player, grid) then
          gameState.winner = Some(player.color)
          println(s"Speler ${player.color} wint door Instant Escape!")
        else
          // Wissel beurt
          gameState.switchTurn(grid)
        true // Actie geslaagd

      case _ =>
        // Fout: Klik op leeg vakje of stuk van tegenstander
        println("Je moet je eigen stuk selecteren voor Instant Escape.")
        false
