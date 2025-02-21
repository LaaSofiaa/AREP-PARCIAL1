package edu.escuelaing.arep;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpFachada {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }

        boolean running= true;
        while (running) {
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
            String request = "";
            while ((inputLine = in.readLine()) != null) {
                //System.out.println("Recibí: " + inputLine);
                if (isFirstLine) {
                    request = inputLine;
                    isFirstLine = false;
                }
                if (!in.ready()) {
                    break;
                }
            }

            String path = request.split(" ")[1];
            System.out.println(path);
            System.out.println(request);


            if(path.startsWith("/cliente")) {
                outputLine= getStaticFile();
            }else if(path.startsWith("/consulta")){
                outputLine= connectHttpServer(path);
            }else{
                outputLine ="HTTP/1.1 200 OK\r\n"
                        + "Content-Type: text/html\r\n"
                        + "\r\n"
                        + "<!DOCTYPE html>" +
                        "<html>" +
                        "<head>" +
                        "<meta charset=\"UTF-8\">" +
                        "<title>HttpFachada</title>\n" +
                        "</head>" +
                        "<body>" +
                        "<h1>Funciona Else HttpFachada</h1>" +
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

    private static String getStaticFile() throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("src/main/java/resources/index.html"));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n"
                +response.toString();
    }

    private static String connectHttpServer(String path) {
        return "";
    }
}
