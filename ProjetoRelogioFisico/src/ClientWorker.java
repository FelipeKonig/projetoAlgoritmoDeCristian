import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

public class ClientWorker extends Thread {

	private Socket socket;
	private int port = 2800;

	@Override
	public void run() {

		try {
			System.out.println("Cliente vai enviar mensagem para o servidor porta: " + port + "\n");

			while (true) {

				LocalTime timeNow = LocalTime.now();

				Random random = new Random();
				int delay = random.nextInt(150);

				timeNow = LocalTime.now().minusSeconds(delay);
				String time0 = timeNow.getHour() + ":" + timeNow.getMinute() + ":" + timeNow.getSecond();

				socket = new Socket("localhost", port);

				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				out.writeUTF(time0);
				out.flush();

				System.out.println("Cliente enviou: " + time0 + "\n");

				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
				String responseServer = in.readUTF();
				System.out.println("Servidor respondeu: " + responseServer + "\n");

				processTime(responseServer);

				in.close();
				out.close();

				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		} catch (

		IOException e) {
			e.printStackTrace();
		}
	}

	private void processTime(String responseServer) {

		String[] times = responseServer.split(" | ");

		LocalTime time0Format = stringToTime(times[0]);
		LocalTime time1Format = stringToTime(times[2]);
		LocalTime time2Format = stringToTime(times[4]);

		LocalTime time3Format = time2Format.plusSeconds(5);

		String time3 = time3Format.getHour() + ":" + time3Format.getMinute() + ":" + time3Format.getSecond();

		System.out.println("Time 0: " + times[0]);
		System.out.println("Time 1: " + times[2]);
		System.out.println("Time 2: " + times[4]);
		System.out.println("Time 3: " + time3 + "\n");

		calculateDelay(time0Format, time1Format, time2Format, time3Format);
	}

	private void calculateDelay(LocalTime time0, LocalTime time1, LocalTime time2, LocalTime time3) {

		long calc1 = (ChronoUnit.SECONDS.between(time1, time0)) * -1;
		System.out.println(calc1);
		long calc2 = (ChronoUnit.SECONDS.between(time3, time2)) * -1;
		System.out.println(calc2);

		int finalCalc = (int) (calc1 + calc2) / 2;
		System.out.println("tempo de sincronização: " + finalCalc + " segundos");

		int minutes = 0;
		int seconds = finalCalc;

		while (seconds > 60) {
			minutes++;
			seconds -= 60;
		}
		System.out.println("tempo de sincronização: 00:" + minutes + ":" + seconds + "\n");
	}

	private LocalTime stringToTime(String time) {

		String[] timeItem = time.split(":");
		LocalTime timeFormat = LocalTime.of(Integer.parseInt(timeItem[0]), Integer.parseInt(timeItem[1]),
				Integer.parseInt(timeItem[2]));

		return timeFormat;
	}
}
