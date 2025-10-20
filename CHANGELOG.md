## 1.1.0-beta.3

- Add scarecrow
    - Birds see scarecrows as players, so any bird that avoids players will also avoid scarecrows
    - Scarecrows can be attacked like armour stands, activating enchantments such as sweeping edge
    - Scarecrows can be broken by sneaking and attacking it
        - Unlike armour stands, double-clicking scarecrows will do nothing

- Fix geese not naturally spawning

- Add European Portuguese translation

- Add Japanese translation

## 1.1.0-beta.2

- Fix crash on NeoForge

## 1.1.0-beta.1

_IMPORTANT: This is a beta. While there shouldn't be any game breaking bugs, there are some unfinished assets and buggy behaviours._

- Birds now adhere to a schedule when determining what their behaviours should be, similar to villagers

- Bird AI has been completely overhauled:
    - During the day, birds will fly around, perch, and forage for food
    - At night, perching birds will seek out a tree to sleep in, while waterfowl will sleep on the ground or on water
    - Birds now have a height range that they will try to fly between
    - Birds will now decelerate and stop flying when approaching their destination

- Add goose (currently missing sounds)
    - Geese variants are split into two categories: domestic and wild
    - Wild variants can only be found in naturally spawned geese
        - Greylag goose (unfinished)
        - Canada goose
        - Swan goose (unfinished)
    - Domestic variants are obtained through breeding
        - Emden goose
        - Chinese goose
    - Geese with the name "untitled" will attack any player they see

- Optimize bird perch pathfinding

- Birds will now tilt their heads when looking at things

- Add traditional chinese translations

- Update some translations

## 1.0.0

- Add more pigeon variants (name a pigeon "Martha" for a special variant)

- Add midair spawning for all birds

- Add brazilian portuguese translation

- Many changes to bird behaviours — expect some bugs!

- Fix missing renderer crash with SimpleHats