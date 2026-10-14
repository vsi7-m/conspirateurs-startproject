package model

import scala.reflect.ClassTag // Om Classtag error te vermijden

/** Het logische grid in dit spel */
class Grid[T: ClassTag](val rows: Int, val columns: Int):
  // Interne representatie van bord
  private val matrix: Array[Array[Option[T]]] =
    Array.fill(this.rows, this.columns)(None)

  def isValid(row: Int, col: Int): Boolean =
    row >= 0 && row < rows && col >= 0 && col < columns

  /** Verander een cel op een bepaalde positie */
  def set(row: Int, column: Int, content: Option[T]): Unit =
    this.matrix(row)(column) = content

  /** Verkrijg een cel op een bepaalde positie */
  def get(row: Int, column: Int): Option[T] =
    this.matrix(row)(column)
