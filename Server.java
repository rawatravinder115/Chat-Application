import java.net.*;
import java.io.*;
class Server{

    ServerSocket server;
    Socket socket;

    BufferedReader br;
    PrintWriter out;
    // Constructor 
    public Server(){
       try {
        server = new ServerSocket(7777);
        System.out.println("server is ready to accept connection");
        System.out.println("waiting...");
        socket= server.accept();
        
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
                    System.out.println("client terminated the chat");
                    socket.close();
                    break;
                }
                System.out.println("client : " + msg);
               }
             } catch (Exception e) {
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
            System.out.println("Writing started..");

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
            }catch (Exception e) {
                    //TODO: handle exception
                    // e.printStackTrace();
                    System.out.println("Connection Closed");
                }
        };

        new Thread(r2).start();
    }
    public static void main(String[] args) {
        System.out.println("this is Server.. going to start Server");
        new Server();
    }
}