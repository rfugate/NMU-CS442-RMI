public interface ServerInterface extends java.rmi.Remote
{
	public int register(ClientInterface client) throws java.rmi.RemoteException;
	public void changeNum(int delta, int playerNum) throws java.rmi.RemoteException;
	public int getNum() throws java.rmi.RemoteException;
	public void status(int playerNum) throws java.rmi.RemoteException;
}
