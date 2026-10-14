package view

import gamelib.Drawable
import model.PowerUpSelection.*
import model.{GamePiece, GameState, Grid, Player}
import util.GameConfig

import java.awt.image.BufferedImage

class GameRenderer(gameState: GameState, grid: Grid[GamePiece]):

  private val boardView = new BoardView

  def getDrawables(buttonManager: ButtonManager): List[Drawable] =
    val pYellow = gameState.playerYellow
    val pBrown = gameState.playerBrown
    val current = gameState.currentPlayer

    val pieceSize = GameConfig.cellWidth

    val stockLeftX = GameConfig.offsetWidth - pieceSize
    val stockRightX = GameConfig.offsetWidth + grid.columns * GameConfig.cellWidth
    val stockY = GameConfig.offsetHeight + 220

    val maxPieces = GameConfig.InitialPieces

    val stockBrown = new StockView(
      stockLeftX, stockY,
      pBrown.piecesLeftToPlace, maxPieces,
      model.PlayerColor.Brown, pieceSize
    )

    val stockYellow = new StockView(
      stockRightX, stockY,
      pYellow.piecesLeftToPlace, maxPieces,
      model.PlayerColor.Yellow, pieceSize
    )

    val powerUpConfigs = List(
      (buttonManager.btnY_IE, buttonManager.btnB_IE, InstantEscape, (p: Player) => p.instantEscape),
      (buttonManager.btnY_MR, buttonManager.btnB_MR, MoveRestriction, (p: Player) => p.moveRestriction),
      (buttonManager.btnY_JB, buttonManager.btnB_JB, JumpBoost, (p: Player) => p.jumpBoost),
      (buttonManager.btnY_TP, buttonManager.btnB_TP, Teleport, (p: Player) => p.teleportation),
      (buttonManager.btnY_LS, buttonManager.btnB_LS, LostSon, (p: Player) => p.lostSon)
    )

    val buttonDrawables = powerUpConfigs.flatMap:
      case (btnY, btnB, selectionType, getCount) =>
        val yActive = (current == pYellow) && (gameState.powerUpSelection == selectionType)
        val bActive = (current == pBrown)  && (gameState.powerUpSelection == selectionType)

        List(
          btnY.createView(getCount(pYellow), yActive),
          btnB.createView(getCount(pBrown), bActive)
        )

    val pieceDrawables =
      for
        row <- 0 until grid.rows
        col <- 0 until grid.columns
        piece <- grid.get(row, col)
      yield
        val isSelected = gameState.selectedPos.contains((row, col))
        val (powerUpIcon: Option[BufferedImage], isGhost: Boolean) = piece match
          case _: model.TemporaryPiece => (Option.empty, true)
          case s: model.StandardPiece =>
            val imgJB = buttonManager.btnY_JB.image
            val imgMR = buttonManager.btnY_MR.image

            val icon: Option[BufferedImage] =
              if s.hasActiveJumpBoost then Some(imgJB)
              else if s.isMoveRestricted then Some(imgMR)
              else Option.empty // gewone None verwijst naar mijn enum
            (icon, false)

        new PieceView(
          piece, row, col, isSelected,
          GameConfig.offsetWidth, GameConfig.offsetHeight,
          powerUpIcon, isGhost
        )

    List(boardView, stockBrown, stockYellow) ++ buttonDrawables ++ pieceDrawables