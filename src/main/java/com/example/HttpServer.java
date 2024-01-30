package com.example;


import java.net.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
 
public class HttpServer {
 
    public static void main(String[] args) throws IOException, URISyntaxException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
 
        boolean running = true;
        while (running) {
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
 
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            String inputLine, outputLine;
            
            boolean firstLine = true;
            String uriStr = "";
 
            while ((inputLine = in.readLine()) != null) {
                if(firstLine){
                    uriStr = inputLine.split(" ")[1];
                    firstLine = false;
                }
                System.out.println("Received: " + inputLine);
                if (!in.ready()) {
                    break;
                }
            }
            URI fileuri = new URI(uriStr);
            System.out.println("Path : " + fileuri.getPath());

            try{
                outputLine = htttpClientHtml(fileuri.getPath());
            }catch(Exception e){
                e.printStackTrace();
                outputLine = httpError();
            }
            out.println(outputLine);
 
            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    }
    
     private static String httpError() {
        String outputLine = "HTTP/1.1 400 Not Found\r\n"
                    + "Content-Type:text/html\r\n"
                    + "\r\n"
                    + "<!DOCTYPE html>\n"
                    + "<html>\n"
                    + "    <head>\n"
                    + "        <title>Error Not found</title>\n"
                    + "        <meta charset=\"UTF-8\">\n"
                    + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                    + "    </head>\n"
                    + "    <body>\n"
                    + "        <h1>Error</h1>\n"
                    + "    </body>\n";
        return outputLine;
                
     }
    
    public static String htttpClientHtml(String path) throws IOException{
        String outputLine = "HTTP/1.1 200 OK\r\n"
        + "Content-Type:text/html\r\n"
        + "\r\n";
        // El archivo que invoca es el que se genera al compilar en target/classes
        Path file = Paths.get("target/classes"+ path); 
        Charset charset = Charset.forName("UTF-8");
        BufferedReader reader = Files.newBufferedReader(file, charset);
        String line = null;
        while ((line = reader.readLine()) != null){
            System.out.println(line);
            outputLine = outputLine + line;
        }
        return outputLine;
    }
   
}