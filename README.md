# Arcane Ascension

_6 December 2025_

## Plugin Overview

**Arcane Ascension** is a Minecraft Paper plugin that expands vanilla gameplay with a simple attribute-based progression system. Players progress through distinct skill branches to gain permanent buffs and unlock the ability to create personal waypoints.

## Plugin Information

| Field                 | Details                                                                            |
|-----------------------|------------------------------------------------------------------------------------|
| **Platform**          | [Minecraft Paper 1.21.10](https://jd.papermc.io/paper/1.21.10/)                    |
| **Genre**             | RPG / Progression                                                                  |
| **Target Audience**   | Minecraft players seeking attribute enhancements and waypoint management           |
| **Core Features**     | Skill-based progression, attribute buffs, waypoint creation and management         |
| **Reference Systems** | RPG leveling systems, attribute modifiers, custom waypoint mechanics               |

## Gameplay Overview

### Objectives

* Progress through three skill branches to unlock permanent attribute buffs
* Reach the maximum level in each skill to gain their full benefits
* Create and manage personal waypoints for easy travel
* Efficiently track progression across all skills

## Gameplay Flow

| Step  | Action                                                                    |
|-------|---------------------------------------------------------------------------|
| **1** | Receive the Tome of Ascension upon joining the server or using `/help`    |
| **2** | Right-click with the Tome to open the General Menu                        |
| **3** | Click on a Skill Type (Combat, Mining, Farming) to view its Level Menu    |
| **4** | Perform relevant in-game activities to gain skill experience and level up |
| **5** | Unlock attribute buffs automatically as you level up each skill           |
| **6** | Use the Waypoints Menu to create and manage saved locations               |

## Core Systems

### Skill Progression System

* **Three Skill Types:** Combat, Mining, Farming
* **Skill Leveling:** Each skill has 23 levels, gained by performing relevant vanilla activities
* **Attribute Buffs:** Each level provides a permanent buff to a corresponding Minecraft attribute (e.g., +attack damage, +mining speed, +max health)

### Waypoint System

* **Creation:** Players can create waypoints from the General Menu
* **Management:** A paginated list of all personal waypoints
* **Limits:** The number of waypoints a player can create is tied to their total skill levels

### Tome of Ascension

* **Central Interface:** Right-clicking the Tome opens the General Menu
* **Menu Navigation:** Access point for Skill Level Menus and the Waypoints Menu
* **Automatic Distribution:** Given to players on join and via the `/help` command

## Technical Implementation

### User Interface

* **General Menu:** The main hub opened by the Tome, containing buttons for Skills and Waypoints
* **Skill Level Menu:** A separate menu for each skill type (Combat, Mining, Farming) showing progression and unlocked buffs
* **Waypoints Menu:** A paginated menu to view, create, and manage personal waypoints

### Progression Tracking

* **Vanilla Activity Integration:** Tracks existing Minecraft activities (e.g., damage dealt, blocks mined, crops harvested) for skill progression
* **Attribute Modification:** Applies permanent attribute modifiers via the Bukkit API as players level up

### Command System

* **/skill [combat/mining/farming]:** Displays the player's current level and progress in the specified skill.
* **/skills:** Displays the player's current level in all three skills.

## Design Style

| Category              | Description                                                                                      |
|-----------------------|--------------------------------------------------------------------------------------------------|
| **Art Style**         | Vanilla-friendly interface that complements Minecraft's existing aesthetic                       |
| **Visual Philosophy** | Clean menus that feel like natural extensions of vanilla mechanics                               |
| **User Interface**    | Re-skinned chest interfaces maintaining vanilla container familiarity                            |
| **Iconography**       | Uses vanilla or simple custom textures for clear menu navigation                                 |

## Design Guidelines

| Element                 | Notes                                                                            |
|-------------------------|----------------------------------------------------------------------------------|
| **Menu Design**         | Intuitive chest-based menus with clear navigation paths                          |
| **Progression Display** | Clean presentation of current level, next level requirements, and unlocked buffs |
| **Waypoint List**       | Paginated, easy-to-read list of saved locations                                  |

## Progression Arc

### Early Game (Levels 1-8)

* Begin gaining levels through basic gameplay
* Receive initial, small attribute buffs
* Unlock first few waypoint slots

### Mid Game (Levels 9-16)

* Noticeable enhancement to character capabilities
* Increased number of available waypoints
* Significant time savings in travel and tasks

### Late Game (Levels 17-23)

* Powerful attribute buffs that markedly change gameplay
* Maximum number of waypoints for flexible travel
* Character specialization based on highest skill

## MVP (Minimum Viable Product)

**Goal:** Functional prototype demonstrating core progression system

**Must-have Features:**

* Tome of Ascension item and General Menu
* One fully functional skill type (e.g., Combat) with 23 levels and attribute buffs
* Basic progression tracking for that skill
* Waypoint creation and management menu
* `/skill` and `/skills` commands

## Future Expansion Potential

* **Additional Skills:** New skill types like Fishing, Building, or Exploration
* **Skill Trees:** Choices within a skill branch for specialized buffs
* **Waypoint Enhancements:** Naming, icons, or sharing waypoints with other players
* **Global Leaderboards:** Compare skill levels across the server

## Development Considerations

* **Performance:** Efficient tracking of player activity and attribute application
* **Compatibility:** Works alongside other plugins that modify attributes or menus
* **Scalability:** Modular design allowing for easy addition of new skills or features
* **User Experience:** Intuitive systems that provide clear value without complicating core gameplay

## License

Arcane Ascension is licensed under the permissive MIT license. Please see [LICENSE](https://github.com/kol-oss/arcane-ascension/blob/main/LICENSE) for more info.