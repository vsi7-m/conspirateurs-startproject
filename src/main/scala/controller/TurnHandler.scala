package controller

import model.logic.*
import model.{GamePiece, GameState, Grid, PowerUpSelection, Player}
import util.GameConfig

object TurnHandler:

  // --- FASE 1: PLAATSEN ---
  def handlePlacement(row: Int, col: Int, grid: Grid[GamePiece], gameState: GameState): Boolean =
    val player = gameState.currentPlayer

    if gameState.powerUpSelection != PowerUpSelection.None then
      println("Je kan geen power-ups gebruiken tijdens de plaatsingsfase!")
      gameState.deselect()
      return true

    if GameConfig.isInLandingZone(row, col) && grid.get(row, col).isEmpty then
      if player.hasPieces then
        val newPiece = player.takePiece()
        grid.set(row, col, Some(newPiece))
        player.addPiece(newPiece)
        gameState.switchTurn(grid)
        println(s"Stuk geplaatst op ($row, $col)")
        true // Succes -> Refresh UI
      else
        println("Speler heeft geen stukken meer om te plaatsen.")
        false
    else
      println("Ongeldige plaatsing!")
      false

  // --- FASE 2: VERPLAATSEN ---
  def handleMovement(row: Int, col: Int, grid: Grid[GamePiece], gameState: GameState): Boolean =
    val player = gameState.currentPlayer
    gameState.powerUpSelection match
      case PowerUpSelection.None =>
        handleNormalMove(row, col, grid, gameState)
      case PowerUpSelection.InstantEscape =>
        executePowerUp(InstantEscapeLogic.tryExecute(row, col, grid, gameState), gameState)
      case PowerUpSelection.MoveRestriction =>
        executePowerUp(MoveRestrictionLogic.tryExecute(row, col, grid, gameState), gameState)
      case PowerUpSelection.JumpBoost =>
        executePowerUp(JumpBoostLogic.tryExecute(row, col, grid, gameState), gameState)
      case PowerUpSelection.Teleport =>
        executePowerUp(TeleportLogic.tryExecute(row, col, grid, gameState), gameState)
      case PowerUpSelection.LostSon =>
        executePowerUp(LostSonLogic.tryExecute(row, col, grid, gameState), gameState)

  private def executePowerUp(success: Boolean, gameState: GameState): Boolean =
    if success then
      println("Power-up toegepast!")
      gameState.powerUpSelection = PowerUpSelection.None
    else
      println("Actie mislukt of ongeldig doelwit.")
      gameState.deselect()
    true

  private def handleNormalMove(row: Int, col: Int, grid: Grid[GamePiece], gameState: GameState): Boolean =
    val player = gameState.currentPlayer

    gameState.selectedPos match
      case None =>
        trySelectPiece(row, col, grid, gameState, player)
      case Some((oldR, oldC)) =>
        tryMovePiece(row, col, oldR, oldC, grid, gameState, player)

  private def trySelectPiece(r: Int, c: Int, grid: Grid[GamePiece], gameState: GameState, player: Player): Boolean =
    grid.get(r, c) match
      case Some(p) if p.color == player.color =>
        gameState.select(r, c)
        println(s"Geselecteerd: ($r, $c)")
        true
      case _ =>
        false

  private def tryMovePiece(newR: Int, newC: Int, oldR: Int, oldC: Int, grid: Grid[GamePiece], gameState: GameState, player: Player): Boolean =
    // Als op zichzelf geklikt wordt: deselecteren
    if oldR == newR && oldC == newC then
      gameState.deselect()
      return true
    
    val piece = grid.get(oldR, oldC).get

    if piece.canMove((oldR, oldC), (newR, newC), grid) then
      // Voer verplaatsing uit
      grid.set(oldR, oldC, None) // Oude plek leeg
      grid.set(newR, newC, Some(piece)) // Nieuwe plek gevuld
      gameState.deselect()

      // Check win conditie
      if gameState.hasWon(player, grid) then
        gameState.winner = Some(player.color)
        println(s"Speler ${player.color} heeft gewonnen!")
      else
        gameState.switchTurn(grid)
        println("Succesvolle zet!")
      true
    else
      println("Ongeldige zet")
      false
