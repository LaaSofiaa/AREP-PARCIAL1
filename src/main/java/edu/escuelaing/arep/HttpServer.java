package edu.escuelaing.arep;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.*;
import java.io.*;
import java.util.Arrays;

public class HttpServer {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
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

    private static String responseConsulta(String path) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String igual = path.split("=")[1];
        String comando = igual.split("\\(")[0];
        if(comando.startsWith("pi")){
            return String.valueOf(Math.PI);
        }else if(comando.startsWith("Class")){
            String clase = igual.split("\\(")[1].split("\\)")[0];
            System.out.println(clase);
            Class c = Class.forName(clase);
            return Arrays.toString(c.getDeclaredFields())+ Arrays.toString(c.getDeclaredMethods());
        }else if(comando.startsWith("invoke")){
            String clase = igual.split("\\(")[1].split("\\)")[0].split(",")[0];
            System.out.println(clase);
            String metodo = igual.split("\\(")[1].split("\\)")[0].split(",")[1];
            System.out.println(metodo);
            Class c = Class.forName(clase);
            System.out.println(c);
            Method m = c.getMethod(metodo);
            System.out.println(m);
            return m.invoke(null).toString();
        }else if(comando.startsWith("unaryInvoke")){
            String clase = igual.split("\\(")[1].split("\\)")[0].split(",")[0];
            //System.out.println(clase);
            String metodo = igual.split("\\(")[1].split("\\)")[0].split(",")[1];
            String tipoParam = igual.split("\\(")[1].split("\\)")[0].split(",")[2];
            System.out.println(tipoParam);
            String valorParam = igual.split("\\(")[1].split("\\)")[0].split(",")[3];
            System.out.println(valorParam);
            //System.out.println(metodo);
            Class c = Class.forName(clase);
            //System.out.println(c);
            Method m = c.getMethod(metodo,getTypeParam(tipoParam));
            //System.out.println(m);
            return m.invoke(null, getCasting(tipoParam,valorParam)).toString();
        }else if(comando.startsWith("binaryInvoke")){
            String clase = igual.split("\\(")[1].split("\\)")[0].split(",")[0];
            //System.out.println(clase);
            String metodo = igual.split("\\(")[1].split("\\)")[0].split(",")[1];
            String tipoParam = igual.split("\\(")[1].split("\\)")[0].split(",")[2];
            System.out.println(tipoParam);
            String valorParam = igual.split("\\(")[1].split("\\)")[0].split(",")[3];
            System.out.println(valorParam);
            String tipoParam1 = igual.split("\\(")[1].split("\\)")[0].split(",")[2];
            System.out.println(tipoParam);
            String valorParam1 = igual.split("\\(")[1].split("\\)")[0].split(",")[3];
            System.out.println(valorParam);
            //System.out.println(metodo);
            Class c = Class.forName(clase);
            //System.out.println(c);
            Method m = c.getMethod(metodo,getTypeParam(tipoParam),getTypeParam(tipoParam1));
            //System.out.println(m);
            return m.invoke(null, getCasting(tipoParam,valorParam),getCasting(tipoParam1,valorParam1)).toString();
        }
        return "error";
    }

    private static Object getCasting(String tipoParam, String valorParam) {
        if (tipoParam.equals("int")) {
            return Integer.valueOf(valorParam);
        } else if (tipoParam.equals("double")){
            return Double.valueOf(valorParam);
        }else if(tipoParam.equals("String")){
            return String.valueOf((valorParam));
        }else{
            return null;
        }
    }

    private static Class<?> getTypeParam(String tipoParam) {
        if (tipoParam.equals("int")) {
            return int.class;
        } else if (tipoParam.equals("double")){
            return double.class;
        }else if(tipoParam.equals("String")){
            return String.class;
        }else{
            return null;
        }
    }
}



