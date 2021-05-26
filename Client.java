import java.net.Socket;

// import java.net.*;
import java.io.*;
class CLient{

    Socket socket;
    BufferedReader br;
    PrintWriter out;

    public CLient(){

        try {
            System.out.println("Sending request to server ");
            socket= new Socket("127.0.0.1",7777); // (ip,port);
            System.out.println("connection done..");

            // for input stream  
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // for output  stream  
        out = new PrintWriter(socket.getOutputStream());

        startReading();
        startWriting();
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }
    }

    public void startReading(){
        //thread  - read  data from from user and give it .

        Runnable r1 = ()->{

            System.out.println("reader started...");

            try {
            while(true){
                String msg = br.readLine();
                if(msg.equals("exit")){
                    System.out.println("Server terminated the chat");
                    break;
                }
                System.out.println("Server : " + msg);
               } 
            }catch (Exception e) {
                   //TODO: handle exception
                //    e.printStackTrace();
                System.out.println("Connection Closed");
               }
        };
        
        new Thread(r1).start();
    }
    
    public void startWriting(){
        //thread  - user get data and then send it to the client.
        
        Runnable r2 = ()->{
            System.out.println("Writer started..");

            try {
            while (true && socket.isClosed()) {
                    BufferedReader br1= new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();
                    if(content.equals("exit")){
                        socket.close();
                       break;
                       }
                } 
                System.out.println("Connection Closed");
            }catch (Exception e) {
                    //TODO: handle exception
                    // e.printStackTrace();
                    System.out.println("Connection Closed");
                }
                // System.out.println("Connection Closed");
        };

        new Thread(r2).start();
    }
    public static void main(String[] args) {
        System.out.println("this is Client..");
        new CLient();
    }
}