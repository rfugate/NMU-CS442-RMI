public interface ClientInterface extends java.rmi.Remote
{
	public void update(int delta, int playerNum, int number) throws java.rmi.RemoteException;
	public void loss() throws java.rmi.RemoteException;
	public void won(int playerNum) throws java.rmi.RemoteException;
	public void status(int clientCount, int turn) throws java.rmi.RemoteException;
	public void isWinning(int playerNum) throws java.rmi.RemoteException;
}
