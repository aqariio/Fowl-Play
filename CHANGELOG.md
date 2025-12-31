## 1.1.2

- Fix geese sometimes not breeding
- Fix pigeon not following its owner
- Lower the minimum version for Fabric loader

## 1.1.1

- Fix birds not being saved to chunk when unloaded in 1.20.1

## 1.1.0

- **Bird AI has been completely overhauled**
    - Birds now adhere to a schedule when determining what their behaviours should be, similar to villagers
        - During the day, birds will fly around, perch, and forage for food
        - At night, perching birds will seek out a tree to sleep in, while waterfowl will sleep on the ground or on
          water
    - Birds now have a height range that they will try to fly between
    - Birds will now decelerate and stop flying when approaching their destination
    - Birds will no longer get trapped in vehicles when flying
    - Improve reliability of bird pathfinding (less midair spinning)
        - Many pathfinding optimizations
        - Fix birds spinning when trying to fly vertically
    - Birds now stop flying when below a certain speed
    - Birds will now tilt their heads when looking at things
    - Fix jerky movement when birds turn
- **Introducing the goose**
    - Geese variants are split into two categories: domestic and wild
    - Naturally spawned geese will have wild variants
        - Canada goose
        - Greylag goose
        - Swan goose
    - Domestic variants are obtained through breeding (this mechanic will be expanded in the future)
        - Emden (from greylag goose)
        - Chinese (from swan goose)
        - Canada geese do not have a domestic variant
    - To breed geese, you must first drop them food to get them to trust you. Afterwards, you can approach without
      scaring them away and feed them directly
    - Domestic geese will no longer naturally avoid players, and can have their wings clipped using shears (to stop them
      from flying)
    - Geese with the name "untitled" will attack any player they see, and can pick up swords
- **Add Scarecrows**
    - Birds see scarecrows as players, so any bird that avoids players will also avoid scarecrows
    - Scarecrows can be attacked like armour stands, activating enchantments such as sweeping edge
    - Scarecrows can be broken by sneaking and attacking it
        - Unlike armour stands, double-clicking scarecrows will do nothing
    - Scarecrows can be destroyed by fire, lava, and explosions
- **Other changes**
    - Fix incompatibility with Perfect Parity
    - Fix seemingly random crash in 1.20.1 when a bird is pathing
    - Add European Portuguese translation
    - Add Japanese translation
    - Add Traditional Chinese translation

## 1.1.0-beta.6

- Fix seemingly random crash in 1.20.1 when a bird is pathing

## 1.1.0-beta.5

- Update canada goose model and texture
- More improvements to bird pathing (less midair spinning)
- Fix jerky movement when birds turn
- Migrate to Mojang mappings

## 1.1.0-beta.4

- Fix incompatibility with PerfectParity
- Stop birds from riding vehicles when flying
- Improve reliability of bird pathfinding (should hopefully result in less midair spinning)
- More pathfinding optimizations
- Fix birds spinning when trying to fly vertically
- Birds now stop flying when below a certain speed

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