package dev.joinaurora.YAIKP;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class YAIKP extends JavaPlugin {
    FileConfiguration config = getConfig();

    //This is a small disaster. I'm sure someone with this skill thing could make this less of a nightmare, but why?
    @Override
    public void onEnable() {
        config.addDefault("ClearSafeTime", 15);
        config.addDefault("TPSCheckInterval", 30);
        config.options().copyDefaults(true);
        saveConfig();
        getLogger().info("[YAIKP] Plugin enabled!");
        Objects.requireNonNull(this.getCommand("kit")).setExecutor(new KillDrops());
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            long sec;
            long current;
            int ticks;
            double tps;

            @Override
            public void run() {
                sec = (System.currentTimeMillis() / 1000);

                if (current == sec) {
                    ticks++;
                } else {
                    // Calculates TPS every second.
                    current = sec;
                    tps = (tps == 0 ? ticks : ((tps + ticks) / 2));
                    ticks = 0;
                }
                if (sec == config.getInt("TPSCheckInterval") * 60L) {
                    Bukkit.broadcastMessage("[YAIKP] All dropped items will be cleared in 15 seconds.");
                    //iterates through every world.
                    for (World w : Bukkit.getWorlds()) {
                        //iterates through every entity in the world
                        for (Entity e : w.getEntities()) {
                            //checks if the item is a drop or not.
                            if (e instanceof Item) {
                                e.remove(); // kills the drop. For fun.
                            }
                        }
                    }
                }
            }
        }, 0, 1);
    }
    @Override
    public void onDisable() {
        getLogger().info("[YAIKP] Night night!");
    }

}
