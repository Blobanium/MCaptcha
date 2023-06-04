package io.github.blobanium.mcaptcha;

import io.github.blobanium.mcaptcha.config.Config;
import io.github.blobanium.mcaptcha.webserver.Webserver;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class MCaptcha extends JavaPlugin {

    public FileConfiguration config = this.getConfig();

    @Override
    public void onEnable() {
        // Plugin startup logic

        config.addDefault("server_ip", Bukkit.getIp());
        config.addDefault("webserver_port", "");
        config.addDefault("hcaptcha_sitekey", "");
        config.addDefault("hcaptcha_secret", "");
        config.options().copyDefaults(true);
        saveConfig();

        Config.load(config);

        try {
            Webserver.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        Webserver.stop();
    }
}
