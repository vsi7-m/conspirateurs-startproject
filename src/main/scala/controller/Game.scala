package controller

import gamelib.*
import model.{GamePiece, GameState, Grid, Player, PowerUpSelection}
import view.{ButtonManager, GameRenderer}
import util.GameConfig

import scala.jdk.CollectionConverters.*

class Game(gui: GUI):
  private val grid = new Grid[GamePiece](GameConfig.numRows, GameConfig.numColumns)
  private val gameState = new GameState(GameConfig.numRows, GameConfig.numColumns)
  
  private val buttonManager = new ButtonManager()
  private val renderer = new GameRenderer(gameState, grid)
  private val panel = gui.getGridPanel

  def startGame(): Unit =
    panel.setPressListener(onClick)
    refreshUI()

  private def onClick(x: Int, y: Int): Unit =
    if gameState.winner.isDefined then return
    val player = gameState.currentPlayer
    
    if handlePowerUpClick(x, y, player) then
      refreshUI()
      return
    
    if handleBoardClick(x, y) then
      refreshUI()

  private def handlePowerUpClick(x: Int, y: Int, player: Player): Boolean =
    val (btnIE, btnRM, btnJB, btnTP, btnLS) = buttonManager.getButtonsByPlayer(player, gameState)

    // Lijst van mogelijke acties koppelen aan knoppen
    // Structuur: (Button, HeeftSpelerPowerUp?, TargetState, Naam)
    val powerUpOptions = List(
      (btnIE, player.hasInstantEscape, PowerUpSelection.InstantEscape, "Instant Escape"),
      (btnRM, player.hasMoveRestriction, PowerUpSelection.MoveRestriction, "Restriction"),
      (btnJB, player.hasJumpBoost, PowerUpSelection.JumpBoost, "Jump Boost"),
      (btnTP, player.hasTeleport, PowerUpSelection.Teleport, "Teleport"),
      (btnLS, player.hasLostSon, PowerUpSelection.LostSon, "Lost Son")
    )

    // Zoek de eerste knop die aangeklikt is
    powerUpOptions.find((btn, _, _, _) => btn.isClicked(x, y)) match
      case Some((_, hasPower, targetState, name)) =>
        togglePowerUp(player, hasPower, targetState, name)
        true
      case None =>
        false

  private def togglePowerUp(
    player: Player,
    hasPowerUp: Boolean,
    targetState: PowerUpSelection,
    name: String
  ): Unit =
    if hasPowerUp then
      if gameState.powerUpSelection == targetState then
        gameState.deselect()
        println(s"$name is nu UIT")
      else
        gameState.deselect()
        gameState.powerUpSelection = targetState
        println(s"$name is nu AAN")
    else
      println(s"Speler heeft geen $name meer over!")
  
  private def handleBoardClick(x: Int, y: Int): Boolean =
    // Check of de klik binnen het bord valt (boven de UI balk)
    if y >= GameConfig.boardHeight then return false

    val col = (x - GameConfig.offsetWidth) / GameConfig.cellWidth
    val row = y / GameConfig.cellHeight

    // Bounds check
    if row < 0 || row >= GameConfig.numRows || col < 0 || col >= GameConfig.numColumns then
      return false

    // Delegeer logica naar TurnHandler afhankelijk van de fase
    if gameState.isPlacementPhase then
      TurnHandler.handlePlacement(row, col, grid, gameState)
    else
      TurnHandler.handleMovement(row, col, grid, gameState)

  private def refreshUI(): Unit =
    panel.clear()
    
    val allDrawables = renderer.getDrawables(buttonManager)
    panel.addDrawables(allDrawables.asJava)

    if gameState.winner.isDefined then println(s"WINNAAR: ${gameState.winner.get}")
