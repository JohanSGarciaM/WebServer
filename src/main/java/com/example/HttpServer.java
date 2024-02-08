package com.example;

import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;

public class HttpServer {

    public static void main(String[] args) throws IOException, URISyntaxException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
            System.out.println("Server running in port : " + Integer.toString(35000));

        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }

        boolean running = true;
        while (running) {
            Socket clientSocket = null;
            try {
                System.out.println("Ready to receive ...");
                clientSocket = serverSocket.accept();
                httpClientHtml(clientSocket);

            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);

            }
        }
    }

    /**
     * Method that receive the client socket and clasify the type of extension of the files that are requested and gives the response of the request
     * @param clientSocket The socket pointing to the client
     * @throws IOException In / Out exception made by the Stream
     */

    public static void httpClientHtml(Socket clientSocket) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        OutputStream out = clientSocket.getOutputStream();
        String inputLine, outputLine;

        boolean firstLine = true;

        //Get the path
        String uriStr = "";

        while ((inputLine = in.readLine()) != null) {
            if (firstLine) {
                uriStr = inputLine.split(" ")[1];
                firstLine = false;
            }
            System.out.println("Received: " + inputLine);
            if (!in.ready()) {
                break;
            }
        }

        //Clasify the extension's content-types

        String contentType ;
    
        if(uriStr.endsWith(".png")){
            contentType="image/png";
        }else if(uriStr.endsWith(".html")){
            contentType="text/html";
        }else if(uriStr.endsWith(".js")){
            contentType="text/javascript";
        }else if(uriStr.endsWith(".css")){
            contentType="text/css";
        }else if(uriStr.endsWith(".jpeg")||uriStr.endsWith(".jpg")){
            contentType="image/jpeg";
        }else{
            contentType="application/octet-stream";
        }

        //define the outputLine

        try{
            outputLine = "HTTP/1.1 200 OK\r\n"
                    + "Content-Type:" + contentType + "\r\n"
                    + "\r\n";
        
            Path file = Paths.get("target/classes", uriStr);
            byte[] fileData = Files.readAllBytes((file));
            out.write(outputLine.getBytes());
            out.write(fileData);
        }catch(Exception e){
            outputLine = httpError();
        }    
        out.close();
        in.close();
        clientSocket.close();
    }

    /**
     * Method which define the Error schema if the file does not exist in the directory
     * @return the String with the 400 not found schema
     */
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
}