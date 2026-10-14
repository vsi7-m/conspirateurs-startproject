# Conspirateurs - Scala OOP Project

Een objectgeoriënteerde implementatie in **Scala 3** van het klassieke bordspel **Conspirateurs**, uitgebreid met een interactieve grafische interface en een modulair power-up systeem.

---

## Over het spel

*Conspirateurs* is een strategisch bordspel voor twee spelers (geel en bruin). Het doel is om als eerste alle 20 eigen pionnen vanuit de centrale landingszone veilig naar de schuilplaatsen langs de rand van het bord te manoeuvreren.

### Spelverloop
1. **Plaatsingsfase:** Spelers plaatsen om de beurt één van hun 20 pionnen in de centrale landingszone (het groen gemarkeerde gebied) tot alle pionnen op het bord staan.
2. **Verplaatsingsfase:** Spelers verplaatsen om de beurt een eigen pion:
   - **Enkele stap:** Één vakje horizontaal, verticaal of diagonaal naar een vrij aangrenzend vakje.
   - **Sprongbeweging:** Springen over een ander aangrenzend stuk (mits het landingsvakje vrij is). Sprongen mogen binnen dezelfde beurt arbitrair vaak herhaald worden (kettingsprongen).
3. **Winstconditie:** De speler die als eerste al diens stukken in een schuilplaats (dubbel gerasterde randvakjes) weet te plaatsen, wint.

---

## Power-ups

Naast reguliere bewegingen beschikt elke speler over een voorraad power-ups die tactisch kunnen worden ingezet om de eigen beweging te bevorderen of de tegenstander te dwarsbomen:

| Power-up | Aantal per speler | Effect |
| :--- | :---: | :--- |
| **Instant ontsnapping** | 2 | Het geselecteerde stuk verlaat het bord onmiddellijk en telt als veilig ontsnapt. |
| **Bewegen beperken** | 3 | Het geselecteerde stuk verliest de mogelijkheid om diagonaal te bewegen. |
| **Springen** | 2 | Het stuk mag gedurende drie zetten twee lege vakjes ver bewegen in plaats van één. |
| **Teleportatie** | 1 | Teleporteert het stuk eenmalig naar een willekeurig vrij vakje (met uitzondering van schuilplaatsen). |
| **Verloren zoon** | 1 | Geeft de tegenspeler tijdelijk een extra stuk dat na 3 rondes weer verdwijnt. |

---

## Architectuur & OOP Ontwerp

Het project is opgezet met een strikte scheiding tussen domeinlogica en de gebruikersinterface:
- **Generiek Grid (`Grid[T]`):** Een parametriseerbaar 17 × 17 rooster dat herbruikbaar is voor willekeurige grid-gebaseerde
