NMU-CS442-RMI
=============
<b>Northern Michigan University <br />
Advanced Network Programming<br />
Author:</b> Robert Fugate<br />
<b>Purpose:</b> Enable processes on different machines to work together to solve a common issue.

<p>This assignment contains two programs, a client and a server. The clients play the game 'subtract'. The server keeps track of the state of the game, and sends changes to all the clients. </p>

Rules to subtract:<br />
	1. The total starts at 25. <br />
	2. Anyone can at any time subtract a number (1 .. 5) from the total. <br />
	3. No player can go twice in a row. <br />
	4. The winner is the last person to do a subtraction. <br />
	5. You cannot make the total go below zero. <br />

The client connects to the server.<br />
It shows the current total, updating whenever needed.<br />
It communicates all subtracts to the server.<br />
It shows if you are currently winning or losing<br />

The server accepts connections from the client (no more than 100).<br />
It updates the total to all valid clients.<br />
It handles client disconnects.<br />

Source: http://cs.nmu.edu/~rappleto/Classes/CS442/RMI/tic-tac-toe-assignment.txt
