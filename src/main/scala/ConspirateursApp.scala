import gamelib.*
import controller.Game
import util.GameConfig

object ConspirateursApp:
  def main(args: Array[String]): Unit =
    // Initialisatie van de grafische interface (View)
    val gui = new GUI(
      GameConfig.windowWidth,
      GameConfig.windowHeight,
      GameConfig.numRows,
      GameConfig.numColumns,
      0
    )

    // Start de controller die de spellogica en interactie beheert
    val game = new Game(gui)
    game.startGame()