package edu.escuelaing.arep;

import java.net.*;
import java.io.*;
public class HttpServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(45000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 45000.");
            System.exit(1);
        }

        boolean running= true;
        while (running){
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            PrintWriter out = new PrintWriter(
                    clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            String inputLine, outputLine;

            boolean isFirstLine = true;
            String request ="";
            while ((inputLine = in.readLine()) != null) {
                //System.out.println("Recibí: " + inputLine);
                if(isFirstLine){
                    request = inputLine;
                    isFirstLine = false;
                }
                if (!in.ready()) {break; }
            }

            String path = request.split(" ")[1];
            System.out.println(path);
            System.out.println(request);

            if(path.startsWith("/compreflex")){
                outputLine= "HTTP/1.1 200 OK\r\n"
                        + "Content-Type: text/html\r\n"
                        + "\r\n"
                        + responseConsulta(path);
            }else{
                outputLine ="HTTP/1.1 200 OK\r\n"
                        + "Content-Type: text/html\r\n"
                        + "\r\n"
                        + "<!DOCTYPE html>" +
                                "<html>" +
                                "<head>" +
                                "<meta charset=\"UTF-8\">" +
                                "<title>HttpServer</title>\n" +
                                "</head>" +
                                "<body>" +
                                "<h1>Funciona Else HttpServer</h1>" +
                                "</body>" +
                                "</html>";
            }
            out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    }

    private static String responseConsulta(String path) {
        return "error";
    }
}
