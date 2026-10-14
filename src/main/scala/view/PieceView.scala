package view

import gamelib.Drawable

import java.awt.{BasicStroke, Color, Graphics2D}
import model.{GamePiece, PlayerColor}
import util.GameConfig

import java.awt.image.BufferedImage

class PieceView(
  piece: GamePiece,
  row: Int,
  col: Int,
  isSelected: Boolean,
  offX: Int,
  offY: Int,
  activeIcon: Option[BufferedImage],
  isTransparent: Boolean = false
) extends Drawable:

  private val Padding = 6
  private val BorderStroke = new BasicStroke(4)
  private val SelectionStroke = new BasicStroke(4)

  private val ColorYellow = new Color(255, 191, 70)
  private val ColorBrown = new Color(164, 74, 0)
  private val ColorBorder = new Color(68, 68, 68)
  private val ColorSelection = new Color(153, 153, 238) // Paarsachtig

  override def draw(g: Graphics2D): Unit =
    // Bereken posities
    val x = (col * GameConfig.cellWidth) + offX
    val y = row * GameConfig.cellHeight

    // Grootte van het cirkeltje
    val width = GameConfig.cellWidth - (Padding * 2)
    val height = GameConfig.cellHeight - (Padding * 2)
    val drawX = x + Padding
    val drawY = y + Padding

    val baseColor = piece.color match
      case PlayerColor.Yellow => ColorYellow
      case PlayerColor.Brown => ColorBrown
    
    if isTransparent then // voor Lost Son
      g.setColor(new Color(baseColor.getRed, baseColor.getGreen, baseColor.getBlue, 128))
    else
      g.setColor(baseColor)

    g.fillOval(drawX, drawY, width, height)

    // Teken power-up icoontje
    activeIcon.foreach: icon =>
      val iconSize = (width * 0.6).toInt // iets kleiner
      val iconOffset = (width - iconSize) / 2
      g.drawImage(icon, drawX + iconOffset, drawY + iconOffset, iconSize, iconSize, null)


    // Teken de rand om het stuk
    g.setStroke(BorderStroke)
    if isTransparent then
      g.setColor(new Color(ColorBorder.getRed, ColorBorder.getGreen, ColorBorder.getBlue, 128))
    else
      g.setColor(ColorBorder)

    g.drawOval(drawX, drawY, width, height)

    // Teken de selectie-indicator
    if isSelected then
      g.setColor(ColorSelection)
      g.setStroke(SelectionStroke)
      // We tekenen de selectie iets ruimer om het stuk heen
      val extraRoom = 2
      g.drawOval(x + extraRoom, y + extraRoom, GameConfig.cellWidth - (extraRoom * 2), GameConfig.cellHeight - (extraRoom * 2))
