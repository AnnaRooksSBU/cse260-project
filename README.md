# Breakout project
This game is based off the game "Breakout" published by Atari, Inc. The basic goal of the game is to destroy all the bricks on the field, while returning the ball with the paddle.

## Running the Game
The game is already packaged into a [Java Archive (JAR) file](bin/build.jar). You can download this file and run as an executable (process may vary depending on system). On most systems, you can execute `java -jar build.jar` from the directory that [build.jar](bin/build.jar) was downloaded in.

## Playing the Game
The paddle is controlled by the mouse. There are two types of bricks: power bricks and tough bricks. Tough bricks have toughness, changing their color depending on the number of times they need to be hit to destroy. Power bricks have one only toughness, and also grant a power up comprised of a single letter when destroyed.

To use the current power up, left mouse click.

All information in this section, as well as the list of power ups, can be viewed in game by clicking the "About" button.

### List of Power Ups
| Color   | `char` | Power Description            |
| :-----: | :----: | :--------------------------: |
| White   |  `C`   | Clears the nearest row       |
| Red     |  `D`   | Damages all bricks by 1      |
| Green   |  `L`   | One life is added            |
| Yellow  |  `P`   | Ball's power is doubled      |
| L. Blue |  `R`   | Paddle's width is increased  |
| Blue    |  `S`   | Speed of the ball is reduced |

## Level Editing
While there are currently three levels built into the game, the game can be fairly easily modified to contain custom levels instead. The levels are stored in [the `lvs` folder](src/brickbreak/lvs/).

Without modification to the source, there can only be three levels, named `0`, `1`, and `2`. The names of the level files, the number of levels, and the order of the levels can be changed in the [Level class](src/brickbreak/Level.java), as they are stored as a simple `String` array.

### Level Formatting
The level files are simple text files, containing 10 lines of 10 characters each. Example:
```
----------
-99--99--D
88--88--88
7--77--C7-
--66--66--
-55--L5--5
44--44--44
3--R3--33-
--22--22--
-P1--11--1
```
The `-` characters represent empty spaces in the field, each number `n` represents a regular brick with `n` toughness, and the remaining characters represent power bricks.

Power bricks are associated with the power from the table above, but if there is an unknown power, the brick will display as a pink power brick, but has no effect.

Without somewhat heavy modification, it is not easy to have a edit a level with dimensions other than 10x10 bricks, or custom power bricks. These may be documented later, although you are free to try on your own.
