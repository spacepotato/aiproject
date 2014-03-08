aiproject
=========

The github repository for the Artificial Intelligence group project 1.

In its current state, the project is able to read input from a text file and store this as relevant information.

Its current process is as follows:

Main Engine generates an instance of the gameboard class
Main Engine passes the text file to the gameboard and calls readTextFile
readTextFile loops through the data and generates an ArrayList of Hexagon objects from this data
The hexagon object contains information about the Hexagons position and value (position is done in a (row, column) format)
The hexagon object also contains reference to the cells adjacent to it below. It will most likely be useful to keep track
of the cells adjacent above and to the sides. 
