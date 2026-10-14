package view

import gamelib.Drawable
import java.awt.{Color, Graphics2D, BasicStroke, Font}
import java.awt.image.BufferedImage

class ButtonView(
  x: Int,
  y: Int,
  w: Int,
  h: Int,
  image: BufferedImage,
  count: Int,
  isSelected: Boolean
) extends Drawable:

  private val SelectedStroke = new BasicStroke(4)
  private val NormalStroke = new BasicStroke(1)
  private val TextFont = new Font("Arial", Font.BOLD, 16)

  private val ColorSelected = Color.GREEN
  private val ColorBorder = Color.BLACK
  private val ColorText = Color.WHITE
  private val ColorShadow = Color.BLACK

  override def draw(g: Graphics2D): Unit =
    // Achtergrond voor als plaatje transparant is
    g.setColor(Color.GRAY)
    g.fillRect(x, y, w, h)
    // Icoontje
    g.drawImage(image, x, y, w, h, null)
    // Count van power-up
    drawCounterText(g)
    // Rand van selectie
    drawBorder(g)

  private def drawCounterText(g: Graphics2D): Unit =
    g.setFont(TextFont)
    val text = s"x$count"
    // Positie rechtsonder
    val textX = x + w - 21
    val textY = y + h - 5
    // Teken "Drop Shadow" (zwart) voor leesbaarheid
    g.setColor(ColorShadow)
    g.drawString(text, textX + 1, textY + 1)
    g.setColor(ColorText)
    g.drawString(text, textX, textY)

  private def drawBorder(g: Graphics2D): Unit =
    if isSelected then
      g.setColor(ColorSelected)
      g.setStroke(SelectedStroke)
    else
      g.setColor(ColorBorder)
      g.setStroke(NormalStroke)

    g.drawRect(x, y, w, h)
