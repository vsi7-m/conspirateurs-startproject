package view

import gamelib.Drawable
import java.awt.{Graphics2D, Color, BasicStroke}
import model.PlayerColor

class StockView(
  x: Int,
  y: Int,
  pieceCount: Int,
  maxPieces: Int,
  colorType: PlayerColor,
  pieceSize: Int
) extends Drawable:

  private val Padding = 6
  private val BorderStroke = new BasicStroke(3)

  private val ColorYellow = new Color(255, 191, 70)
  private val ColorBrown = new Color(164, 74, 0)
  private val ColorBorder = new Color(68, 68, 68)

  override def draw(g: Graphics2D): Unit =
    // Bepaal de kleur
    val mainColor = colorType match
      case PlayerColor.Yellow => ColorYellow
      case PlayerColor.Brown  => ColorBrown

    val drawSize = pieceSize - (Padding * 2)
    val verticalOverlap = pieceSize / 2

    // Bereken startpositie
    val missingPieces = maxPieces - pieceCount
    val currentTopY = y + (missingPieces * verticalOverlap)

    // Zet de stijlen vast voor de loop
    g.setStroke(BorderStroke)

    // Teken de stapel
    for i <- (0 until pieceCount).reverse do
      // Elk volgend stuk wordt iets lager getekend dan de 'top'
      val drawY = currentTopY + (i * verticalOverlap)

      // Vullen
      g.setColor(mainColor)
      g.fillOval(x + Padding, drawY + Padding, drawSize, drawSize)

      // Rand
      g.setColor(ColorBorder)
      g.drawOval(x + Padding, drawY + Padding, drawSize, drawSize)
