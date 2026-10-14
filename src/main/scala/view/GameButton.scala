package view

import gamelib.Drawable
import view.ButtonView

import java.awt.image.BufferedImage

class GameButton(
  val x: Int,
  val y: Int,
  val w: Int,
  val h: Int,
  val image: BufferedImage
):
  // Checkt of een muisklik (mx, my) binnen deze knop valt 
  def isClicked(mx: Int, my: Int): Boolean =
    mx >= x && mx <= x + w && my >= y && my <= y + h

  // Maakt de view aan voor het tekenen 
  def createView(count: Int, isSelected: Boolean): Drawable =
    new ButtonView(x, y, w, h, image, count, isSelected)
    