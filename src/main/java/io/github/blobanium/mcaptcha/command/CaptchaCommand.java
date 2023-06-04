package io.github.blobanium.mcaptcha.command;

import io.github.blobanium.mcaptcha.CaptchaUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
public class CaptchaCommand implements CommandExecutor{

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args[0] != null) {
            Player player = Bukkit.getPlayer(args[0]);
            if (CaptchaUtil.Usernames.indexOf(Bukkit.getPlayer(args[0]).getUniqueId()) == -1) {
                CaptchaUtil.startCapcha(player);
                sender.sendMessage("Captcha sent");
                return true;
            } else {
                sender.sendMessage("User is already being captcha'd");
                return true;
            }
        }
        return false;
    }
}
