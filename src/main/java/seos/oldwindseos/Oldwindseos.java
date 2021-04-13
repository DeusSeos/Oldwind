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

    public HashMap<UUID, List<Integer>> zordCharges = new HashMap<>();

    public HashMap<UUID, UUID> blitzArrows = new HashMap<>();

    public void addPlayers() {
        List<Integer> cooldownCharge = new ArrayList<>();
        cooldownCharge.add(0, 120);
        cooldownCharge.add(1, 3);
        for (Player player : getServer().getOnlinePlayers()) {
            UUID playerUUID = player.getUniqueId();
            if (!zordCharges.containsKey(playerUUID))
                zordCharges.putIfAbsent(playerUUID, cooldownCharge);

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
//            Bukkit.getConsoleSender().sendMessage("Zoop ZOop");
            if (!zordCharges.isEmpty()) {
                for (UUID key : zordCharges.keySet()) {
                    Integer ticks = zordCharges.get(key).get(0);
                    Integer charges = zordCharges.get(key).get(1);
                    if (charges < 3) { // charges aren't full
                        zordCharges.get(key).set(0, ticks - 1);
                        if (ticks <= 0) {
                            zordCharges.get(key).set(0, 120);
                            zordCharges.get(key).set(1, charges + 1);
                            if(Bukkit.getServer().getPlayer(key) != null) {
                                try{
                                    Player player = Bukkit.getServer().getPlayer(key);
                                    Bukkit.getConsoleSender().sendMessage("Player: " + player.getName() + " Charges: " + zordCharges.get(key).get(1));
                                    Location location = player.getLocation();
                                    location.getWorld().spawnParticle(Particle.PORTAL, location, 50);
                                    location.getWorld().playSound(location, Sound.ENTITY_ENDERMAN_AMBIENT, 2, 1);
                                } catch (NullPointerException exception){
                                    Bukkit.getConsoleSender().sendMessage("Error in runZord(). Something went wrong during getPlayer");
                                    Bukkit.getConsoleSender().sendMessage(exception.getMessage());
                                }

                            } else {
                                String playerName = Bukkit.getServer().getOfflinePlayer(key).getName();
                                Bukkit.getConsoleSender().sendMessage(playerName + " is probably offline.");
                                Bukkit.getConsoleSender().sendMessage("Player: " + playerName + " Charges: " + zordCharges.get(key).get(1));
                                return;
                            }
                        } else return;
                    }else return;
                }
            }
        }, 0L, 1L);
        // Plugin startup logic
    }

    public void runBlitz() {
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, () -> {
            if (!blitzArrows.isEmpty()) {
                for (UUID arrowID : blitzArrows.keySet()) {
                    Arrow arrow = (Arrow) Bukkit.getEntity(arrowID);
                    if (Bukkit.getEntity(arrowID) != null) {
                        UUID playerUUID = blitzArrows.get(arrowID);
                        Inventory inventory = Bukkit.getPlayer(playerUUID).getInventory();
                        if (inventory.contains(Material.ARROW)) {
                            inventory.removeItem(new ItemStack(Material.ARROW, 4));
                            assert arrow != null;
                            Location location = arrow.getLocation();
                            World world = location.getWorld();
                            Vector vector = arrow.getVelocity();
                            double damage = arrow.getDamage();
                            float spread = 70f;
                            for (int i = 0; i < 4; i++) {
                                Arrow arrow1 = world.spawnArrow(location, vector, 0.6f, spread);
                                arrow1.setShooter(arrow.getShooter());
                                arrow1.setDamage(damage);
                            }
                        } else
                            return;
                    } else {
                        blitzArrows.remove(arrowID);
                        return;
                    }
                }
            } else return;
        }, 0L, 4L);
        // Plugin startup logic
    }

    @Override
    public void onEnable() {
        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new AntiVoid(), this);
        manager.registerEvents(new Zord(), this);
        manager.registerEvents(new Summer(), this);
        //manager.registerEvents(new Baphomet(), this);
        addPlayers();
        runBlitz();
        runZord();
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Oldwind Seos enabled");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
