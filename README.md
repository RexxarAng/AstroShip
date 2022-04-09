# Team22
## AstroShip

## Table of contents
* [Game Description](#Game-Description)
* [Setup](#Setup)
* [Technologies](#Technologies)
* [Features](#Features)


## Game Description
Astroship is a fast paced space shooter game supporting single player and local co-op game modes. The main objective of the game is to survive as long as possible to obtain the highest score possible. The game takes inspiration from the arcade style space shooter games and introduces new unique features like space ships firing homing missiles and player ships being equipped with anti-missile weapons to shoot down homing missiles tracking them. To encourage teamplay in co-op mode, players must take their corresponding powerup based on the color to improve their spaceships. In the event that the player took the wrong powerup, the powerup will grant no effect to the playership while being consumed. Over time, the enemy ships will level-up granting them extra shield, points and movement speed making these powerups invaluable to players to stay alive longer. When any playership is destroyed, one life is consumed till the number of remaining lives is zero and it is game over. After which, the player scores will be updated in the top 10 leaderboard if they manage to beat the top 10 records saved in the game's local files.

## Setup
To directly run the game
- Clone the project, or download the zip file to a location of your choice. 
- Unzip the project folder. Go to the "out-windows" folder and run the AstroShip.exe file.

To re-compile the source code to make changes
- Follow the previous steps above to run the game
- Download Intellij IDEA https://www.jetbrains.com/idea/download/#section=windows
- Download Android Studio https://developer.android.com/studio 
- Setup environment variable "ANDROID_SDK_ROOT" with the path to the android sdk
- Open IntelliJ IDEA
  - Open folder -> select the project folder
  - Setup
    - Right click on the project root and click on "module settings"
    - Under "SDKs", click on the + icon 
    - Select Oracle OpenJDK version 11 and select that as the main jdk
    - Select Android SDK, browse to your android sdk location and add it in
  - Run code directly 
    - Right click on the DesktopLauncher class located in the directory desktop/src/com.spaceshooter.desktop and run it
  - To compile into a jar executable
    - Right click on the project root and click on module settings
    - Under artifacts, click on the + icon
    - Click on jar -> module with dependencies
    - For the main class select the desktoplauncher, the rest of the settings can be left on default
    - Now that the new jar configurations is added, under the "Output Layout" tab, click on the + icon 
    - Select "Directory Contents", browse and select the android/assets folder
    - Rename the jar file to "AstroShip.jar"
    - Click on apply on the bottom right.
    - Now its time to build the jar file, click on the top tab "Build" and click on "Build Artifacts" and click on build once a popup appears.
    - By default the output directory for the jar executable file will be located in out/artifacts/AstroShip_jar folder
  
- Issues
  - If an error occurs saying that the the modile must not contain source root folder, go to the module settings
  - Under modules, click each folder under the root folder and remove the source folder pointing to the "android/assets" folder
  - Afterwhich the problem should be solved.
  

## Technologies
Project is created with:
* LibGDX: 1.10.0:

## Features
Different modes of gameplay
- Single Player
- Co-op

Unique anti-missile weapon (railgun)
- Equipped on the player ship with limited ammo
- Fires directly towards homing missiles

Unique weapon
- Homing rocket launcher
  - Fires rocket that homes towards a random target

Different types of powerups
- Shield
  - Increases max capacity of shield, if at max capacity, recovers a shield if currently not at full shield
- Rocket
  - If pick up for the first time, weapon changes to a homing rocket launcher that fires homing rocket at random    enemies
- Ammo
  - Grants 15 ammo to the railgun at the max capacity of 60 ammo         

Leaderboard
- Keep track of the top ten best records with score and time recorded


## Controls

General Shortcut Keys in Game Screen
- 'ESC': Pause Game
- 'Q': Quit Game
- 'R': Restart Game

Player 1 Movement Keys
- 'Up arrow': Up movement
- 'Left arrow': Left movement
- 'Down arrow': Down movement
- 'Right arrow': Right movement
- '/': Fire anti-missile weapon

Player 2 Movement Keys
- 'W': Up movement
- 'A': Left movement
- 'S': Down movement
- 'D': Right movement
- 'F': Fire anti-missile weapon

