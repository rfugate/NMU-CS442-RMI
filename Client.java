import java.io.*;

public class Client extends java.rmi.server.UnicastRemoteObject implements ClientInterface
{
	static ServerInterface server;
	int myNumber = 0;
	volatile int count = 1;
	volatile int gameOver = 0;
	volatile boolean myTurn = true;

	public static void main(String args[]) throws Exception
	{
		new Client();
	}

	public void update(int delta, int playerNum, int number) throws java.rmi.RemoteException
	{
		System.out.println("\tPlayer " + (playerNum+1) + " subtracted: " + delta +". Number is now " + number);
		if(playerNum != myNumber) myTurn = true;
		return;
	}

	public void won(int playerNum) throws java.rmi.RemoteException{
		System.out.println("Player " + (playerNum + 1) + " lost. You won!");
		gameOver = 1;
	}

	public void loss() throws java.rmi.RemoteException{
		System.out.println("You lose!");
		gameOver = 1;
	}

	public void isWinning(int playerNum) throws java.rmi.RemoteException{
		if(playerNum == myNumber){
			System.out.println("\tYou are winning!");
		}else{
			System.out.println("\tYou are losing!");
		}
	}

	public void status(int clientCount, int turn) throws java.rmi.RemoteException{
		count = clientCount;
		if(turn == 1) myTurn = true;
		return;
	}

	public Client() throws Exception
	{
		server = (ServerInterface)java.rmi.Naming.lookup("//euclid.nmu.edu:32585/number");
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		int playerNum = server.register(this);
		myNumber = playerNum;
		System.out.println("Player: " + (playerNum + 1) + " Current Value: " + server.getNum());
		while (gameOver == 0)
		{
			System.out.println("Enter a number to subtract (1 - 5): ");
			int delta = Integer.parseInt(stdin.readLine());
			server.status(playerNum);
			if(myTurn){
				if((delta > 0) && (delta < 6)){
					server.changeNum(delta, playerNum);
					if(count > 1) myTurn = false;
				}
				else{
					System.out.println("Incorrect Value.");
				}
			}
			else{
				System.out.println("\tYou must wait for another player to go.");
			}
		}
		System.out.println("Game Over!");
	}
}
