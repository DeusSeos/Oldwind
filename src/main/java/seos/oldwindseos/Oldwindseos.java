package seos.oldwindseos;

import org.bukkit.*;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class Oldwindseos extends JavaPlugin {

    /***ToDo
     * Baphomet
     * Aegis
     * Indra
     * Elder Branch
     */

    public HashMap<Player, List<Integer>> zordCharges = new HashMap<>();

    public HashMap<UUID, Player> blitzArrows = new HashMap<>();

    public void addPlayer() {
        List<Integer> cooldownCharge = new ArrayList<>();
        cooldownCharge.add(0, 7);
        cooldownCharge.add(1, 3);
        for (Player player : getServer().getOnlinePlayers()) {
            if (!zordCharges.containsKey(player))
                zordCharges.put(player, cooldownCharge);
            else
                return;
        }
    }

    /***
     * TODO fix this
     */
    public void runZord() {
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, () -> {
            if (!zordCharges.isEmpty()) {
                for (Player key : zordCharges.keySet()) {
                    Integer seconds = zordCharges.get(key).get(0);
                    Integer charges = zordCharges.get(key).get(1);
                    if (charges < 3) { // charges aren't full
                        zordCharges.get(key).set(0, seconds - 1);
                        if (seconds <= 0) {
                            zordCharges.get(key).set(0, 7);
                            zordCharges.get(key).set(1, charges + 1);
                            Location location = key.getLocation();
                            location.getWorld().spawnParticle(Particle.PORTAL, location, 50);
                            location.getWorld().playSound(location, Sound.ENTITY_ENDERMAN_AMBIENT, 2, 1);
                        } else return;
                    }else return;
                }
            }
        }, 0L, 20l);
        // Plugin startup logic
    }

    public void runBlitz() {
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, () -> {
            if (!blitzArrows.isEmpty()) {
                for (UUID arrowID : blitzArrows.keySet()) {
                    Arrow arrow = (Arrow) Bukkit.getEntity(arrowID);
                    if (Bukkit.getEntity(arrowID) != null) {
                        Player player = blitzArrows.get(arrowID);
                        Inventory inventory = player.getInventory();
                        if (inventory.contains(Material.ARROW)) {
                            inventory.removeItem(new ItemStack(Material.ARROW, 1));
                            Location location = arrow.getLocation();
                            World world = location.getWorld();
                            Vector vector = arrow.getVelocity();
                            float spread = 70f;
                            Arrow arrow1 = world.spawnArrow(location, vector, 0.6f, spread);
                            arrow1.setShooter(arrow.getShooter());
                        } else

                            return;

                    } else {
                        blitzArrows.remove(arrowID);
                        return;
                    }

                }
            } else return;
        }, 0L, 1L);
        // Plugin startup logic

    }


    @Override
    public void onEnable() {
        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new AntiVoid(), this);
        manager.registerEvents(new Zord(), this);
        manager.registerEvents(new Summer(), this);
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "Enabling Zord");
        addPlayer();
        runBlitz();
        runZord();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
