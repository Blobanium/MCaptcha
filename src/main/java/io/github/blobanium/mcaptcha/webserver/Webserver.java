package io.github.blobanium.mcaptcha.webserver;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import io.github.blobanium.mcaptcha.CaptchaUtil;
import io.github.blobanium.mcaptcha.config.Config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class Webserver {
    private static HttpServer server;

    public static void start() throws Exception {
        server = HttpServer.create(new InetSocketAddress(Integer.parseInt(Config.webserver_port)), 0);
        server.createContext("/test", new MyHandler());
        server.createContext("/complete", new MyHandler2());
        server.setExecutor(java.util.concurrent.Executors.newCachedThreadPool()); // creates a default executor
        server.start();
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            byte[] ip = t.getRemoteAddress().getAddress().getAddress();
            String ipToString = CaptchaUtil.convertIPToString(ip);

            String response = "<html>\n" +
                    "\n" +
                    "<head>\n" +
                    "    <title>hCaptcha Demo</title>\n" +
                    "    <script src=\"https://js.hcaptcha.com/1/api.js\" async defer></script>\n" +
                    "</head>\n" +
                    "\n" +
                    "<body>\n" +
                    "    <form action=\"/complete\" method=\"POST\">\n" +
                    "        <input type=\"hidden\" name=\"IP\" value="+ ipToString +" readonly><br>\n" +
                    "        <div class=\"h-captcha\" data-sitekey=\"" + Config.hcaptcha_sitekey +"\"></div><br />\n" +
                    "        <input type=\"submit\"value=\"Verify\" />\n" +
                    "    </form>\n" +
                    "</body>\n" +
                    "\n" +
                    "</html>";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class MyHandler2 implements HttpHandler{

        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "Captcha Complete! You may safely close this tab and return to minecraft!!";
            String method = t.getRequestMethod();
            if(method.equals("POST")){
                System.out.println("Recieved completed capcha, processing");

                InputStreamReader isr = new InputStreamReader(t.getRequestBody(), StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr);
                String formData = br.readLine();
                String parsedResponse = WebserverBackend.post(getResponse(formData));
                processCaptcha(parsedResponse, formData);

            }
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    public static void stop(){
        server.stop(0);
    }

    private static String getResponse(String resp){

        return resp.split("h-captcha-response=")[1];
    }
    private static String[] splitKeys(String resp){
        return resp.split("&");
    }

    private static void processCaptcha(String response, String formdata){
        JsonParser parser = new JsonParser();
        JsonElement jsonTree = parser.parse(response);

        if(jsonTree.isJsonObject()) {
            JsonObject jsonObject = jsonTree.getAsJsonObject();

            JsonElement f1 = jsonObject.get("success");

            System.out.println("Passed=" + f1.getAsBoolean());
            if(f1.getAsBoolean()){
                CaptchaUtil.endCaptcha(splitKeys(formdata)[0].replace("IP=", ""));
            }
        }
    }
}
