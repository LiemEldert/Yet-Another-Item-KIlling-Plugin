package dev.joinaurora.YAIKP;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.configuration.file.FileConfiguration;

public class KillDrops implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin) this, new Runnable() {
            public void run() {
                Bukkit.broadcastMessage("[YAIKP] All dropped items will be cleared in 15 seconds.");
                //iterates through every world.
                for (World w : Bukkit.getWorlds()) {
                    //iterates through every entity in the world
                    for (Entity e : w.getEntities()) {
                        //checks if the item is a drop or not.
                        if (e instanceof Item) {
                            e.remove(); // kills the drop.
                        }
                    }
                }
            }
        }, (15 * 20)); // Multiply by 20 because this is based off of server ticks. Wish it didn't work like that. If a server had bad TPS, which is when this would usually be triggered so the time would be longer then it really should be.

        return true;
    }
}
