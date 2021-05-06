import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Random;

public class ServerWorker extends Thread {

	private static Socket s;
	private static ServerSocket ss;
	private static int port = 2800;

	@Override
	public void run() {
		try {
			ss = new ServerSocket(port);

			System.out.println("\nServidor iniciou na porta: " + port + "\n");

			while (true) {

				s = ss.accept();

				ObjectInputStream in = new ObjectInputStream(s.getInputStream());
				String time0 = in.readUTF();
				System.out.println("Cliente enviou:" + time0 + "\n");

				System.out.println("Servidor processando \n");

				LocalTime timeNow = LocalTime.now();
				String time1 = timeNow.getHour() + ":" + timeNow.getMinute() + ":" + timeNow.getSecond();

				Random random = new Random();
				int wait = random.nextInt(60000);
				wait = wait / 1000;
				System.out.println("tempo de espera: " + wait + " segundos");
				
				timeNow = timeNow.plusSeconds(wait);
				String time2 = timeNow.getHour() + ":" + timeNow.getMinute() + ":" + timeNow.getSecond();

				String response = time0 + " | " + time1 + " | " + time2;
				System.out.println("Servidor enviou: " + response + "\n");

				ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());

				out.writeUTF(response);
				out.flush();
				in.close();
				out.close();

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
