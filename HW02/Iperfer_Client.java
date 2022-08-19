import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;

public class Iperfer_Client {
    private long totalTime = 0, startTime = 0, endTime = 0, dataTime = 0, bytesSent = 0;
    private Socket Client_Socket;

    public Iperfer_Client(String HostIP, int PortNo, int SendTime) {
        totalTime = SendTime * 1000; // 換算時間為ms
        try {
            Client_Socket = new Socket(HostIP, PortNo);
        } catch (IOException ex) {
            System.out.println("Unable to connect server.");
            System.exit(-1);
        }

    }

    public void ConnectWithServer() throws IOException, InterruptedException {

        DataOutputStream Do = new DataOutputStream(Client_Socket.getOutputStream());
        byte[] DataPacket = ByteBuffer.allocate(1000).putInt(0).array(); // 在每個packet塞入1000bytes且內容全為0
        boolean timeOver = false;

        while (true) {
            // 在每一輪傳送資料時計算傳送時間(ms)
            startTime = System.currentTimeMillis();
            Do.write(DataPacket, 0, DataPacket.length);
            endTime = System.currentTimeMillis();

            dataTime += endTime - startTime;
            timeOver = (dataTime >= totalTime); // 若總傳送時間>=設定時間則停止傳送
            if (timeOver) {
                break;
            }
            bytesSent++;
        }

        Do.close();
        Client_Socket.close();

        dataTime /= 1000; // ms換算為秒

        double rate = bytesSent / dataTime; // 此處rate單位為Kb/s

        System.out.println("sent=" + bytesSent + " KB rate=" + 8 * rate / 1000.0 + " Mbps");

    }

}
