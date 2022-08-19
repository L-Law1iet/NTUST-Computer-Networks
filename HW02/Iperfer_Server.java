import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Iperfer_Server {

    private ServerSocket Server_Socket;
    private Socket Client_Socket;
    private DataInputStream di;
    private long totalBytes = 0, totalTime = 0, startTime, endTime;
    private byte[] readData;
    private int readPacket;

    public Iperfer_Server(int listenPort) {
        try {
            Server_Socket = new ServerSocket(listenPort);
        } catch (IOException e) {

        }
    }

    public void ConnectWithClient() throws IOException {

        Client_Socket = Server_Socket.accept(); // 等待client連接
        di = new DataInputStream(Client_Socket.getInputStream());
        readData = new byte[1000];

        startTime = System.currentTimeMillis(); // 開始接收資料時間(ms)
        while (true) { // Server持續接收data
            readPacket = di.read(readData, 0, readData.length);
            if (readPacket == -1) {
                break;
            }
            totalBytes += readPacket;
        }
        totalBytes--;
        endTime = System.currentTimeMillis(); // 結束接收資料時間(ms)

        totalTime = endTime - startTime;

        di.close();
        Server_Socket.close();

        totalTime /= 1000; // ms轉換為秒

        double rate = (totalBytes / 1000) / totalTime; // 此處rate單位為Kb/s

        System.out.println("received=" + totalBytes / 1000 + " KB rate=" + (8 * rate) / 1000.0 + " Mbps");
    }
}
