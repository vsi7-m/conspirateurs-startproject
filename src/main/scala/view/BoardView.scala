package view

import gamelib.Drawable

import java.awt.{BasicStroke, Color, Graphics2D}
import model.GameState
import controller.Game
import util.GameConfig // Of waar je de window constanten hebt staan

class BoardView extends Drawable:
  private val WindowBackgroundColor = new Color(36, 36, 36) // Donkergrijs
  private val BoardBackgroundColor = new Color(136, 136, 136) // Grijs 
  private val GridLineColor = new Color(68, 68, 68) // Donkergrijs 
  private val LandingZoneColor = new Color(0, 130, 0) // Groen

  private val ShelterStroke = new BasicStroke(3) 
  private val GridStroke = new BasicStroke(4)
  private val LandingZoneStroke = new BasicStroke(5) 
  
  override def draw(g: Graphics2D): Unit =
    drawBackgrounds(g)
    drawShelters(g)
    drawGridLines(g)
    drawLandingZone(g)

  private def drawBackgrounds(g: Graphics2D): Unit =
    // Achtergrond rond bord
    g.setColor(WindowBackgroundColor)
    g.fillRect(0, 0, GameConfig.windowWidth, GameConfig.windowHeight)

    // Bordspel
    g.setColor(BoardBackgroundColor)
    g.fillRect(
      GameConfig.offsetWidth,
      0,
      GameConfig.boardWidth,
      GameConfig.boardHeight
    )

  // Schuilplaatsen tekenen
  private def drawShelters(g: Graphics2D): Unit =
    g.setColor(GridLineColor)
    g.setStroke(ShelterStroke)

    for
      r <- 0 until GameConfig.numRows
      c <- 0 until GameConfig.numColumns
      // Check of dit vakje een shelter is
      if GameConfig.isShelter(r, c)
    do
      val x = c * GameConfig.cellWidth + GameConfig.offsetWidth
      val y = r * GameConfig.cellHeight
      
      // Extra vierkantje rond schuilplaats
      g.drawRect(x + 6, y + 6, GameConfig.cellWidth - 12, GameConfig.cellHeight - 12)

  // Grid tekenen
  private def drawGridLines(g: Graphics2D): Unit =
    g.setColor(GridLineColor)
    g.setStroke(GridStroke)

    val offX = GameConfig.offsetWidth
    val totalW = GameConfig.boardWidth
    val totalH = GameConfig.boardHeight

    // Verticale lijnen
    for i <- 0 to GameConfig.numColumns do
      val x = i * GameConfig.cellWidth + offX
      g.drawLine(x, 0, x, totalH)

    // Horizontale lijnen
    for j <- 0 to GameConfig.numRows do
      val y = j * GameConfig.cellHeight
      g.drawLine(offX, y, totalW + offX, y)

  // Groen rechthoek rond landingszone
  private def drawLandingZone(g: Graphics2D): Unit =
    g.setColor(LandingZoneColor)
    g.setStroke(LandingZoneStroke)

    val x = GameConfig.landingColOffset * GameConfig.cellWidth + GameConfig.offsetWidth
    val y = GameConfig.landingRowOffset * GameConfig.cellHeight
    val w = GameConfig.landingColSize * GameConfig.cellWidth
    val h = GameConfig.landingRowSize * GameConfig.cellHeight

    g.drawRect(x, y, w, h)