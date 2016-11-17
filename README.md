# checkers-cap4104

## Coders:
Hai provided the large majority of the code from a previous project and we worked together to bring it in line with this project's requirements and make other adjustments.

I've divided the 6 remaining tasks into 2 lists. Josh and Peter, please choose one of the task lists to complete and notify the other person of which you chose. Both should be quite easy to finish. Let's try to get these done before Saturday if possible, to give the presenters enough time to create the presentation.

**Task List 1**
* Add to existing menu: button that takes you to rules page (open in browser or pop-up box) (FR 6.2)
* Have an input for player 1 and player 2 to add their names for each new game (FR 7.0)
  * Change all output references to Red to Player’s 1 input name and all references to Black to Player 2’s input name
* Label the sides of the board using the given names
  * Black/player 1 is on top
  * Red/player 2 is on bottom

**Task List 2**
* Show start time of game below the menu (Section 3.4.1)
* Add a counter for the # turns since the current game began (Section 3.4.1)
* Add this as a possible condition for Winning: If no game piece has been removed from the board in 10 turns (Section 3.6.3)
  * The player with the most game pieces remaining wins.
  * If players have the same number of pieces then the game is declared a draw.


### Import the program into Eclipse
You can probably use CodeBlocks or another IDE, but these instructions are for Eclipse. https://www.eclipse.org/downloads

1. Download the whole Github project
2. In Eclipse, choose File > New > Java Project
  * name: Checker Project
  * Click Finish
3. Right click the project and click New > Class
  * name: checker
  * Click Finish
4. This creates a file called checker.java. Paste the contents of our project's checkerproject/src/checkerproject/checker.java into this new file.
5. Run the program

### Adding your changes to Github
After finishing each of your tasks, test your changes and make sure the game still works as it did before. If all is well and you don't have any compile/runtime errors (warnings are fine) you can commit your changes to Github.

First, you need to create a Github account (free). Text me your username so I can add you to the project. Once you're added you can commit your code:

1. Navigate the Github page to get to checkerproject/src/checkerproject/checker.java
2. Click the Edit (pencil) icon at the top right of the Github code editor
3. Paste your updated checker.java files into the Github code editor
4. At the bottom, describe the changes you made and hit the "Commit Changes" button.
5. Text the group that you're done with your tasks.

Note: When the last tasks are finished, I'll make the program into a launchable applet that we'll use as the final product. This lets us run the game on any Java-equipped computer without Eclipse.

Project requirements for reference: https://docs.google.com/document/d/14lRHIl3rn9LLjGrc-XEVDTKZFjyMkWAbQMjPM3nzDJ4


## Presenters:
Here's how to download and run the game so you can get started on the presentation:

1. Download the whole Github project
2. Inside the downloaded folder go to > checkerproject > bin
3. You can open the checkers.html in Firefox or open it from the command line
  * If opening in Firefox, make sure your general Java Control Panel settings allow for programs from file:/// and C:/
    * Screenshots on doing this: http://stackoverflow.com/a/32443427
  * If opening from the command line:
    * make your current directory checkerproject > bin
    * run: appletviewer checkers.html

Note: This isn't the final program: it's completely functional but we're making a few adjustments to meet the project requirements. We're still adding some buttons, labels, and inputs so you may want to hold off on any final screenshots for the presentation for now.
