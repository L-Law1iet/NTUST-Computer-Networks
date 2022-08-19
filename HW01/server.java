import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

public class server {

  public static String returnContentType(String fileName) {
    if (
      fileName.endsWith(".html") || fileName.endsWith(".htm")
    ) return "text/html"; // HTML網頁
    else if (
      fileName.endsWith(".txt") || fileName.endsWith(".java")
    ) return "text/plain"; // 文字型態
    else if (fileName.endsWith(".gif")) return "image/gif"; // GIF圖檔
    else if (
      fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")
    ) return "image/jpeg"; // JPEG圖檔
    else if (fileName.endsWith(".class")) return "application/octec-stream"; // 二進位式檔案
    else return "text/plain"; // 其它不可判別的檔案一律視為文字檔型態
  }

  public static void main(String[] args) throws Exception {
    String requestMessageLine;
    String fileName = ""; // 檔案名稱
    String contentType; // 檔案類型
    int port = 8555; // Port number
    System.out.println("Web Server Starting on Port " + port);
    ServerSocket s = new ServerSocket(port);

    while (true) {
      try {
        Socket serverSocket = s.accept();
        System.out.println("Connection, sending data.");
        BufferedReader in = new BufferedReader(
          new InputStreamReader(serverSocket.getInputStream())
        );
        DataOutputStream outToClient = new DataOutputStream(
          serverSocket.getOutputStream()
        );

        requestMessageLine = in.readLine();
        StringTokenizer tokenizedLine = new StringTokenizer(requestMessageLine);
        if (tokenizedLine.nextToken().equals("GET")) {
          fileName = tokenizedLine.nextToken();

          contentType = returnContentType(fileName);

          if (fileName.equals("/")) { // 網頁預設載入Page1
            fileName = "Page1.html";
            contentType = "text/html";
          } else if (fileName.startsWith("/") == true) fileName =
            fileName.substring(1);
          try { // 找到檔案讀檔
            File file = new File(fileName);
            int numOfBytes = (int) file.length();

            FileInputStream inFile = new FileInputStream(fileName);
            byte[] fileInBytes = new byte[numOfBytes];
            inFile.read(fileInBytes);

            outToClient.writeBytes("HTTP/1.1 200 OK\r\n");
            outToClient.writeBytes("Content-Type: " + contentType + "\r\n");
            outToClient.writeBytes("Content-Length: " + numOfBytes + "\r\n");
            outToClient.writeBytes("\r\n");
            outToClient.write(fileInBytes, 0, numOfBytes);
            inFile.close();
            System.out.println("Sending data completely.");
          } catch (IOException e) { // 找不到檔案
            outToClient.writeBytes("HTTP/1.1 404 File Not Found\r\n");
            outToClient.writeBytes("Content-Type: text/html\r\n");
            outToClient.writeBytes("\r\n");
            outToClient.writeBytes("<H1>404 Not Found</H1>\r\n");
            System.out.println("Sending data completely.");
          }
        } else { // Client端請求尚未實做的方法
          outToClient.writeBytes("HTTP/1.1 501 Not Implemented\r\n");
          outToClient.writeBytes("Content-Type: text/html\r\n");
          outToClient.writeBytes("\r\n");
          outToClient.writeBytes(
            "<H1>HTTP Error 501: Not Implemented</H1>\r\n"
          );
          System.out.println("Sending data completely.");
        }
        serverSocket.close();
        outToClient.flush();
      } catch (NullPointerException e) {
        
      }
    }
  }
}
