
public class MainServidor extends Thread {

	public static void main(String[] args) {

		ServerWorker server = new ServerWorker();
		server.run();
	}

}
