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
