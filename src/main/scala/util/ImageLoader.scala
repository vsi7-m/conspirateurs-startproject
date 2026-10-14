package util

import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import scala.collection.mutable
import java.awt.Color

object ImageLoader:
  private val imageCache = mutable.Map[String, BufferedImage]()
  // Deze functie laadt een plaatje uit de resources map
  def loadImage(path: String): BufferedImage =
    imageCache.getOrElseUpdate(path, loadFromDisk(path))

  private def loadFromDisk(path: String): BufferedImage =
    try
      val resource = getClass.getResource(path)
      if resource == null then
        throw new IllegalArgumentException(s"Resource niet gevonden: $path")
      ImageIO.read(resource)
    catch
      case e: Exception =>
        println(s"Kon afbeelding niet laden: $path. Fout: ${e.getMessage}")
        createErrorImage()

  private def createErrorImage(): BufferedImage =
    val width = 50
    val height = 50
    val img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
    val g = img.createGraphics()

    g.setColor(Color.MAGENTA)
    g.fillRect(0, 0, width, height)

    // Teken een zwart kruis voor extra duidelijkheid
    g.setColor(Color.BLACK)
    g.drawLine(0, 0, width, height)
    g.drawLine(0, height, width, 0)

    g.dispose()
    img