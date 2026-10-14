package util

object GameConfig:

  val InitialPieces: Int = 20

  // Aantal power-ups
  val InitialInstantEscape: Int = 2
  val InitialMoveRestriction: Int = 3
  val InitialJumpBoost: Int = 2
  val InitialTeleport: Int = 1
  val InitialLostSon: Int = 1

  // Afmetingen bord
  val boardWidth: Int = 800
  val boardHeight: Int = 800

  // Ruimte zijkanten en onderkant
  val offsetWidth = 260
  val offsetHeight = 100

  // Windowgrootte
  val windowWidth: Int = boardWidth + 2 * offsetWidth
  val windowHeight: Int = boardHeight + offsetHeight

  // Grid
  val numRows: Int = 17
  val numColumns: Int = 17

  // Grid afmetingen
  val cellWidth: Int = boardWidth / numColumns
  val cellHeight: Int = boardHeight / numRows

  // Landingszone 5x9
  val landingRowSize = 5
  val landingColSize = 9
  val landingRowOffset = (numRows - landingRowSize) / 2
  val landingColOffset = (numColumns - landingColSize) / 2
  
  def isInLandingZone(r: Int, c: Int): Boolean =
    r >= landingRowOffset && r < landingRowOffset + landingRowSize &&
      c >= landingColOffset && c < landingColOffset + landingColSize

  // Schuilplaatsen
  def isShelter(r: Int, c: Int): Boolean =
    val onHorizontalEdge = r == 0 || r == numRows - 1
    val onVerticalEdge = c == 0 || c == numColumns - 1

    if !onHorizontalEdge && !onVerticalEdge then false
    else
      // Bepaal de relevante dimensie (lengte en huidige positie)
      val (index, limit) = if onHorizontalEdge then (c, numColumns) else (r, numRows)

      // Plaatsen van schuilplaats
      val isCornerArea = index <= 1 || index >= limit - 2
      val center = limit / 2
      val isMiddleArea = index >= center - 1 && index <= center + 1
      val isOddPosition = index % 2 != 0

      isCornerArea || isMiddleArea || isOddPosition

  // UI Layout
  val btnW: Int = 50
  val btnH: Int = 50
  val btnY: Int = boardHeight + 10
  val btnGap: Int = 10
  
  private def calcLeftX(index: Int): Int = 200 + (index * (btnW + btnGap))
  private def calcRightX(index: Int): Int = windowWidth - 200 - btnW - (index * (btnW + btnGap))

  // Posities buttons van Yellow (volgorde: IE, MR, JB, T, LS)
  val btnLeftX_IE: Int = calcLeftX(0)
  val btnLeftX_MR: Int = calcLeftX(1)
  val btnLeftX_JB: Int = calcLeftX(2)
  val btnLeftX_T: Int = calcLeftX(3)
  val btnLeftX_LS: Int = calcLeftX(4)

  // Posities buttons van Brown (volgorde andersom)
  val btnRightX_IE: Int = calcRightX(0)
  val btnRightX_MR: Int = calcRightX(1)
  val btnRightX_JB: Int = calcRightX(2)
  val btnRightX_T: Int = calcRightX(3)
  val btnRightX_LS: Int = calcRightX(4)


