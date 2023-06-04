package io.github.blobanium.mcaptcha.config;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {
    public static String server_ip;
    public static String webserver_port;
    public static String hcaptcha_sitekey;
    public static String hcaptcha_secret;

    public static void load(FileConfiguration config){
        server_ip = config.getString("server_ip");
        webserver_port = config.getString("webserver_port");
        hcaptcha_sitekey = config.getString("hcaptcha_sitekey");
        hcaptcha_secret= config.getString("hcaptcha_secret");
    }
}
