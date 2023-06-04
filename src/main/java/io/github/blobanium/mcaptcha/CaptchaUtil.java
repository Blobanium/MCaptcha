package io.github.blobanium.mcaptcha;

import io.github.blobanium.mcaptcha.config.Config;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CaptchaUtil {
    public static int id = 0;
    private static List<String> IPAdresses = new ArrayList<>(); //Required for freeing someone out of a CAPTCHA Challenge.
    public static List<UUID> Usernames = new ArrayList<>();
    private static List<Location> Locations = new ArrayList<>();

    public static void startCapcha(Player player){
        IPAdresses.add(convertIPToString(player.getAddress().getAddress().getAddress()));
        Usernames.add(player.getUniqueId());
        Locations.add(player.getLocation());
        id++;
        System.out.println("Captcha'd :" + convertIPToString(player.getAddress().getAddress().getAddress()) + ", " + player.getDisplayName());
        player.sendTitle("Capcha Required", "Please look in the chat to start the CAPTCHA", 1, 12000, 1);
        player.sendMessage(Component.text("Please click here to start the CAPTCHA").clickEvent(ClickEvent.openUrl("http://" + Config.server_ip + ":" + Config.webserver_port +"/test")));
    }

    public static void onTick(){
        //Freeze the player in a CAPTCHA challenge
        for (int i = 0; Usernames.size() > i; i++){
            Player player = Bukkit.getPlayer(Usernames.get(i));
            if(player.getLocation() != Locations.get(i)){
                player.teleport(Locations.get(i));
            }
        }
    }

    public static void endCaptcha(String ip){
        int indexedIP = IPAdresses.indexOf(ip);

        //Ensure that the ip exists in the list
        if (indexedIP == -1){
            System.err.println("Unable to find a username associated with that IP: " + ip);
        } else {
            UUID username = Usernames.get(indexedIP);
            System.out.println("Username is " + username);
            //Ensure that the ip provided matches the correct username
            if(!convertIPToString(Bukkit.getPlayer(username).getAddress().getAddress().getAddress()).equals(ip)){
                System.out.println("IP Provided did not match the user, attempting to find the right user");
                System.out.println("Expected: {" + ip + "} Found: {" + convertIPToString(Bukkit.getPlayer(username).getAddress().getAddress().getAddress()) + "}");
                //TODO: Find the user that matches the IP
            } else {
                Bukkit.getPlayer(username).sendMessage("You have successfully solved the CAPTCHA!!");
                Bukkit.getPlayer(username).hideTitle();

                Locations.remove(indexedIP);
                Usernames.remove(indexedIP);
                IPAdresses.remove(indexedIP);
            }
        }

    }
    public static String convertIPToString(byte[] ip){
        return ip[0] + "." + ip[1] + "." + ip[2] + "." + ip[3];
    }
}
