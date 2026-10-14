package model

import util.GameConfig

class Player(val color: PlayerColor):
  
  var piecesLeftToPlace: Int = GameConfig.InitialPieces
  var instantEscape: Int = GameConfig.InitialInstantEscape
  var moveRestriction: Int = GameConfig.InitialMoveRestriction
  var jumpBoost: Int = GameConfig.InitialJumpBoost
  var teleportation: Int = GameConfig.InitialTeleport
  var lostSon: Int = GameConfig.InitialLostSon

  var numberOfOwnedPieces: Int = 0

  def hasPieces: Boolean = piecesLeftToPlace > 0
  def hasInstantEscape: Boolean = instantEscape > 0
  def hasMoveRestriction: Boolean = moveRestriction > 0
  def hasJumpBoost: Boolean = jumpBoost > 0
  def hasTeleport: Boolean = teleportation > 0
  def hasLostSon: Boolean = lostSon > 0

  def takePiece(): GamePiece =
    if piecesLeftToPlace > 0 then
      piecesLeftToPlace -= 1
      new StandardPiece(color)
    else
      throw new Exception("Geen stukken meer!")

  def addPiece(p: GamePiece): Unit = numberOfOwnedPieces += 1

  // Gebruik power-up (verlaag teller)
  def useInstantEscape(): Unit =
    if instantEscape > 0 then instantEscape -= 1

  def useMoveRestriction(): Unit =
    if moveRestriction > 0 then moveRestriction -= 1

  def useJumpBoost(): Unit =
    if jumpBoost > 0 then jumpBoost -= 1

  def useTeleport(): Unit =
    if teleportation > 0 then teleportation -= 1

  def useLostSon(): Unit =
    if lostSon > 0 then lostSon -= 1
