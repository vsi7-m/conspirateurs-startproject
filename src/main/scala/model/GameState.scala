package model

import model.Grid
import util.GameConfig

/** Houdt de status van het specifieke Conspirateurs spel bij */
class GameState(val rows: Int, val cols: Int):

  // De twee spelers
  val playerYellow = new Player(PlayerColor.Yellow)
  val playerBrown = new Player(PlayerColor.Brown)

  // Variabele om bij te houden wie aan de beurt is
  var currentPlayer: Player = playerBrown
  var winner: Option[PlayerColor] = None

  // Spelfase: "Plaatsen" of "Verplaatsen"
  var isPlacementPhase: Boolean = true

  var powerUpSelection: PowerUpSelection = PowerUpSelection.None
  var selectedPos: Option[(Int, Int)] = None
  
  def deselect(): Unit =
    selectedPos = None
    powerUpSelection = PowerUpSelection.None

  // Selecteer een vakje
  def select(row: Int, col: Int): Unit = 
    selectedPos = Some((row, col))

  def switchTurn(grid: Grid[GamePiece]): Unit =
    // Wissel de speler
    currentPlayer = if currentPlayer == playerYellow then playerBrown else playerYellow

    ageTemporaryPieces(grid)

    if isPlacementPhase && playerYellow.numberOfOwnedPieces == 20 && playerBrown.numberOfOwnedPieces == 20 then
        isPlacementPhase = false
        println("Plaatsingsfase voorbij")
    deselect()

  private def ageTemporaryPieces(grid: Grid[GamePiece]): Unit =
    for
      row <- 0 until rows
      col <- 0 until cols
    do
      grid.get(row, col) match
        case Some(p: StandardPiece) if p.color == currentPlayer.color =>
          p.tickTurn()
          p match
            case tp: TemporaryPiece =>
              // age() returnt true als stuk moet verdwijnen
              if tp.age() then
                grid.set(row, col, None) // Verwijder stuk
                println(s"De Lost Son op ($row, $col) is weer lost.")
            case _ => // Standaard stuk

        case _ => // Leeg vakje of vijandelijk stuk

  /**
   * Controleert of een speler gewonnen heeft.
   * Dit is waar als alle stukken van een speler op een schuilplaats staan.
   */
  def hasWon(player: Player, grid: Grid[GamePiece]): Boolean =
    if player.hasPieces then return false
    // We gaan elk vakje van het bord af
    for r <- 0 until rows 
        c <- 0 until cols 
    do
        grid.get(r, c) match
          case Some(piece) if piece.color == player.color =>
            if !GameConfig.isShelter(r, c) then return false
          case _ => // Leeg of vijand
    true
