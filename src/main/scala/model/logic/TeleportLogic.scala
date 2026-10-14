package model.logic

import model.{GamePiece, GameState, Grid}
import util.GameConfig

import scala.util.Random

object TeleportLogic:

  def tryExecute(row: Int, col: Int, grid: Grid[GamePiece], gameState: GameState): Boolean =
    val currentPlayer = gameState.currentPlayer

    grid.get(row, col) match
      case Some(piece) if piece.color == currentPlayer.color =>

        val validSpots = for
          r <- 0 until GameConfig.numRows
          c <- 0 until GameConfig.numColumns
          // Het vakje moet leeg zijn
          if grid.get(r, c).isEmpty
          // Mag geen schuilplaats zijn
          if !GameConfig.isShelter(r, c)
        yield (r, c)

        if validSpots.nonEmpty then
          // Kies een willekeurige plaats
          val (newR, newC) = validSpots(Random.nextInt(validSpots.length))
          println(s"Stuk van ($row, $col) geteleporteerd naar ($newR, $newC)!")

          grid.set(row, col, None) // Oude plek leegmaken
          grid.set(newR, newC, Some(piece)) // Teleporteren naar nieuwe plek

          currentPlayer.useTeleport()
          gameState.switchTurn(grid)
          true
        else
          println("Er zijn geen plekken om naar te teleporteren!")
          false

      case Some(_) =>
        println("Je kan alleen je eigen stukken teleporteren.")
        false

      case None =>
        false
