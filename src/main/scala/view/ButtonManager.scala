package view

import util.{GameConfig, ImageLoader}
import model.{GameState, Player}
import java.awt.image.BufferedImage

class ButtonManager:
  // Laad Plaatjes
  private val imgIE = ImageLoader.loadImage("/instantEscape.png")
  private val imgMR = ImageLoader.loadImage("/Restriction.png")
  private val imgJB = ImageLoader.loadImage("/jumpBoost.png")
  private val imgTP   = ImageLoader.loadImage("/Teleport.png")
  private val imgYellow = ImageLoader.loadImage("/yellow.png")
  private val imgBrown  = ImageLoader.loadImage("/brown.png")

  private def createButton(x: Int, img: java.awt.image.BufferedImage): GameButton =
    new GameButton(
      x,
      GameConfig.btnY,
      GameConfig.btnW,
      GameConfig.btnH,
      img
    )

  // Knoppen speler geel
  val btnY_IE = createButton(GameConfig.btnRightX_IE, imgIE)
  val btnY_MR = createButton(GameConfig.btnRightX_MR, imgMR)
  val btnY_JB = createButton(GameConfig.btnRightX_JB, imgJB)
  val btnY_TP = createButton(GameConfig.btnRightX_T, imgTP)
  val btnY_LS = createButton(GameConfig.btnRightX_LS, imgBrown)

  // Knoppen speler bruin
  val btnB_IE = createButton(GameConfig.btnLeftX_IE, imgIE)
  val btnB_MR = createButton(GameConfig.btnLeftX_MR, imgMR)
  val btnB_JB = createButton(GameConfig.btnLeftX_JB, imgJB)
  val btnB_TP = createButton(GameConfig.btnLeftX_T, imgTP)
  val btnB_LS = createButton(GameConfig.btnLeftX_LS, imgYellow)

  // Helper om alles in 1x aan de Renderer te geven
  def getAllButtons: List[GameButton] = List(
    btnY_IE, btnY_MR, btnY_JB, btnY_TP, btnY_LS,
    btnB_IE, btnB_MR, btnB_JB, btnB_TP, btnB_LS
  )
  
  def getButtonsByPlayer(currentPlayer: Player, gameState: GameState): (GameButton, GameButton, GameButton, GameButton, GameButton) =
    if currentPlayer == gameState.playerYellow
      then (btnY_IE, btnY_MR, btnY_JB, btnY_TP, btnY_LS)
      else (btnB_IE, btnB_MR, btnB_JB, btnB_TP, btnB_LS)
