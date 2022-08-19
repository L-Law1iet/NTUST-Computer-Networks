import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Iperfer {

    public static void main(String[] args) throws IOException, InterruptedException {

        ArrayList<String> argsList = new ArrayList<String>(Arrays.asList(args));
        int argLength = args.length;
        String Host = "";
        int Port = -1, Time = -1;

        if (argLength == 0) { // 若沒任何引數則Error
            System.out.println("Error: missing or additional arguments");
            System.exit(-1);
        }

        // 分辨各個引數
        for (int i = 0; i < argLength; i += 2) {
            try {
                if (args[i].equals("-c") || (args[i].equals("-s"))) { // 為了讓引數順序不一也能運行
                    i--;
                } else if (args[i].equals("-p")) { // 設定Port
                    Port = Integer.parseInt(args[i + 1]);
                    if (Port < 1024 || Port > 65535) {
                        System.out.println("Error: port number must be in the range 1024 to 65535");
                        System.exit(-1);
                    }
                } else if (args[i].equals("-h")) { // 設定host
                    Host = args[i + 1];
                } else if (args[i].equals("-t")) { // 設定時間(單位:秒)
                    Time = Integer.parseInt(args[i + 1]);
                    if (Time < 0) {
                        System.out.println("Error: time must be greater than 0 Second");
                        System.exit(-1);
                    }
                } else {
                    System.out.println("Error: missing or additional arguments");
                    System.exit(-1);
                }
            } catch (Exception e) {
                System.out.println("Error: missing or additional arguments");
                System.exit(-1);
            }
        }

        // Server端
        if (argLength == 3 && argsList.contains("-s") && argsList.contains("-p")) {
            Iperfer_Server server = new Iperfer_Server(Port);
            server.ConnectWithClient();

        // Client端
        } else if (argLength == 7 && argsList.contains("-c") && argsList.contains("-h") && argsList.contains("-p")
                && argsList.contains("-t")) {
            Iperfer_Client client = new Iperfer_Client(Host, Port, Time);
            client.ConnectWithServer();

        } else {
            System.out.println("Error: missing or additional arguments");
            System.exit(-2);
        }

        System.exit(0);
    }

}
