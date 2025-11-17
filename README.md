# Arcane Ascension

_17 November 2025_

## Plugin Overview

**Arcane Ascension** is a Minecraft Paper plugin that expands vanilla gameplay with a deep magic system, progression through divine sorcery branches, and village management mechanics. Players begin as novice practitioners and can evolve into powerful archmages capable of creating settlements and commanding followers through mystical arts.

## Plugin Information

| Field             | Details                                                                            |
|-------------------|------------------------------------------------------------------------------------|
| Platform          | [Minecraft Paper 1.21.10](https://jd.papermc.io/paper/1.21.10/)                    |
| Genre             | RPG / Magic / Progression                                                          |
| Target Audience   | Minecraft players seeking enhanced RPG elements                                    |
| Core Features     | Branch-based magic system, spell progression, village creation, follower mechanics |
| Reference Systems | Magic plugins, RPG progression systems, custom village mechanics                   |

## Gameplay Overview

### Objectives

* Progress through three divine sorcery branches to unlock powerful abilities
* Learn and master spells of increasing complexity and power
* Establish and manage mystical settlements
* Convert villagers to cult followers for exclusive trading
* Manage mana resources and spell vessels effectively
* Complete the arcane progression to become a master sorcerer

### Story

> The player discovers ancient texts of the Trinitarian Divine Path - three branches of sorcery that have been lost to time. Through dedication to these mystical arts, players can reshape the world around them, creating settlements bound by magic and commanding loyal followers.

## Gameplay Flow

| Step | Action                                                                    |
|------|---------------------------------------------------------------------------|
| 1    | Obtain the Tome of Ascension through command                              |
| 2    | Perform branch-related activities to gain experience                      |
| 3    | Level up sorcery branches and general arcane level                        |
| 4    | Unlock and learn new spells through progression and trading               |
| 5    | Craft star vessels to prepare spells for casting                          |
| 6    | Use spells to enhance abilities, manipulate world, and manage settlements |
| 7    | Convert villagers to followers for exclusive spell trading                |
| 8    | Create and name magical villages as centers of power                      |

## Core Systems

### Arcane Progression System

* **Three Divine Branches:** Celestial Might, Eternal Endurance, Ethereal Celerity

* **General Arcane Level:** Tracks overall mastery, increases mana pool and regeneration

* **Branch Leveling:** Each branch levels through specific vanilla activities (combat, mining, movement)

* **Progression Unlocks:** Higher levels grant access to more powerful spells and abilities

### Spell Management

* **Spell Tome:** Central interface replacing inventory with organized spell management

* **Spell Tiers:** Novice, Adept, Master - with increasing power and mana costs

* **Star Vessels:** Craftable items that store prepared spells for activation

* **Spell Activation:** Right-click with vessel to cast, with duration scaling based on branch level

* **Simultaneous Casting:** Up to 3 active spells at once with visual mana display

### Village Creation & Management

* **Settlement Conjuration:** High-level spell that creates player-named villages

* **Tiered Creation:** Spell level determines village size and defenses (villagers, iron golems)

* **Village Registry:** Track all created settlements with names and coordinates

* **Settlement Limits:** Maximum of 13 player-created villages

### Follower System

* **Conversion Rituals:** Branch-specific spells to convert villagers to followers

* **Tiered Followers:** Novice, Adept, Master followers with different trade offerings

* **Specialized Trading:** Followers sell spells focused on their branch with cross-branch options at higher tiers

* **Permanent Allegiance:** Master-level followers cannot be converted to other branches

## Technical Implementation

### User Interface

* **Tome of Ascension:** Custom inventory interface as central hub

* **Branch Menus:** Separate chest-based menus for each progression path

* **Spell Selection:** Grid-based spell management with branch color coding

* **Village Registry:** Interactive list of created settlements with teleport functionality

### Progression Tracking

* **Vanilla Stat Integration:** Leverages existing Minecraft statistics for branch progression

* **Custom Experience:** Implements arcane level system alongside branch progression

* **Achievement Unlocks:** Milestone rewards at key general level thresholds

### Spell Mechanics

* **Effect System:** Custom potion effects, particle systems, and world manipulation

* **Mana Management:** Boss bar display when holding active star vessels

* **Cooldown Systems:** Balanced spell usage with tier-appropriate restrictions

* **Duration Scaling:** Spell effectiveness increases with relevant branch mastery

## Progression Arc

### Early Game (Levels 1-10)

* Basic branch progression through normal gameplay
* Unlock novice-level utility spells
* Learn fundamental mana management
* Access to follower conversion spells

### Mid Game (Levels 11-20)

* Craft first star vessels
* Establish initial magical settlements
* Develop specialized branch focus
* Access to adept-level spells and followers

### Late Game (Levels 21+)

* Master-level spells and world-altering abilities
* Multiple established villages
* Complex spell combinations
* Maximum branch specialization

## MVP (Minimum Viable Product)

**Goal:** Functional prototype demonstrating core magic system

**Must-have Features:**

* Basic tome interface with one functional branch
* Simple spell learning and vessel system
* One working spell type with mana management
* Basic progression tracking
* Village creation spell (single tier)

## Future Expansion Potential

* **Additional Branches:** New sorcery paths with unique mechanics
* **Ritual Magic:** Multi-player spells requiring coordination
* **Inter-settlement Mechanics:** Trade and conflict between player villages
* **Seasonal Events:** Limited-time spells and progression opportunities

## Development Considerations

* **Performance:** Efficient tracking of player progression and active spells
* **Compatibility:** Works alongside other popular plugins and datapacks
* **Scalability:** Modular design allowing for easy expansion
* **User Experience:** Intuitive systems that enhance rather than complicate vanilla gameplay

## License

Arcane Ascension is licensed under the permissive MIT license. Please see [LICENSE](https://github.com/kol-oss/rise-of-cults/blob/main/LICENSE) for more info.