import java.io.*;

public class Server extends java.rmi.server.UnicastRemoteObject implements ServerInterface {

	ClientInterface client[] = new ClientInterface[10];
	int clientLocked = 0;
	int number = 25;
	int count = 0;

  	public static void main(String[] args) throws Exception
	{
		new Server();
	}

	public Server() throws Exception
	{
		System.setSecurityManager(new java.rmi.RMISecurityManager());
      		java.rmi.registry.LocateRegistry.createRegistry(32585);
      		java.rmi.Naming.rebind("rmi://:32585/number", this);
	}

	public int register(ClientInterface c) throws java.rmi.RemoteException
	{
		for(int i = 0; i < 10; i++)
		{
			if (client[i] == null)
			{
				System.out.println("Player registered at spot "+(i+1));
				client[i] = c;
				count++;
				return i;
			}
		}
		return 0;
	}

	public void status(int playerNum) throws java.rmi.RemoteException
	{																			//Client while loop calls this
		int turn = 0;															//Purpose is to check to make sure clients are still connected
		for(int i = 0; i < 10; i++){
			if(client[i]!= null){
				try{
					client[i].status(count, turn);
				}catch(Exception e){
					System.out.println("Player " + (i+1) + " disconnected");
					client[i] = null;
					count--;
					if((count == 1) && (clientLocked == playerNum)){			//Checks for the case where only 2 players are playing and you're locked
						turn = 1;												//If player that is unlocked disconnects then it will turn your option back on
						client[playerNum].status(count, turn);					//Testing purposes I guess -- Game wouldn't make sense with just 1 player, but would be nice to be unlocked
					}
				}
			}
		}
	}

	public void changeNum(int delta, int playerNum) throws java.rmi.RemoteException
	{
		boolean myTurn = true;
		boolean notMyTurn = false;
		number -= delta;

		System.out.println("Player " + (playerNum+1) + " subtracted: " + delta + ". Number is now " + number);
		if(number >= 0){
			for(int i = 0; i < 10; i++){
				if(client[i] != null){
					try{														//Try to update if fails then disconnect user and change his spot to null
						System.out.println("\t Sent update to Player " + (i+1));
						client[i].update(delta, playerNum, number);
						client[i].isWinning(playerNum);
						clientLocked = playerNum;								//Current player that is locked from updating
					}catch(Exception e){
						System.out.println("Player " + (i+1) + " disconnected.");
						client[i] = null;
						count--;
					}
				}
			}
		}else{
			client[playerNum].loss();
			System.out.println("Disconnecting Player: " + (playerNum+1) + "...");					//Tells the loser he lost
			client[playerNum] = null;					//Set his spot to null
			count--;
			for(int i = 0; i < 10; i++){				//Tell the rest of the players who lost and that they won
				if(client[i] != null){
					client[i].won(playerNum);
					System.out.println("Disconnecting Player: " + (i+1) + "...");
					client[i] = null;
				}
			}
		}
	}

	public  int getNum() throws java.rmi.RemoteException
	{
		return number;
	}
}
