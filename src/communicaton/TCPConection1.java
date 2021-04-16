package communicaton;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPConection1 extends Thread{

    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private OnListener observer;
    private boolean conected;

	private static TCPConection1 instanceJ1;

	private TCPConection1() {

	}

	public static TCPConection1 getInstance() {
		if (instanceJ1 == null) {
			instanceJ1 = new TCPConection1();
			instanceJ1.start();
		}
		return instanceJ1;
	}
    
	public void setObserver(OnListener observer) {

		this.observer = observer;

	}
	
    public void run() {
        try {
            ServerSocket server = new ServerSocket(24);
            System.out.println("Esperando...");
            this.conected=false;
            this.socket = server.accept();
            System.out.println("Aceptado!");
            this.conected=true;

            //Reader
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            this.reader = new BufferedReader(isr);
            
            //Writer
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            this.writer = new BufferedWriter(osw);

            while(true) {
                catchMessage();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Mandar un mensaje
    public void sendMessage(String mensaje){
        new Thread(
                ()->{
                    try {
                        writer.write(mensaje+"\n");
                        writer.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        ).start();
    }

    //Recibir mensaje
    public void catchMessage() throws IOException{
        String line = reader.readLine();
        observer.onMessage(1,line);
    }

    public void closeConnection(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        return conected;
    }
}