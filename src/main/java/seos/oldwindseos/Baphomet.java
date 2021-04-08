package seos.oldwindseos;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.ArrayList;
import java.util.List;

public class Baphomet implements Listener {

    Utils utils = new Utils();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBaphHit(EntityDamageByEntityEvent event) {
        if(!event.isCancelled()) {
            Bukkit.broadcastMessage(event.toString());
            if (event.getDamager() instanceof Player) {
                List<String> baphLore = new ArrayList<>();
                baphLore.add("Satan has his miracles, too.");
                if (utils.hasGodLore( (Player) event.getDamager(), baphLore, "main")) {
                    Double damage = event.getDamage();
                    Entity victim = event.getEntity();
                    List<Entity> nearbyEntities = victim.getNearbyEntities(3, 3, 3);
                    nearbyEntities.remove(event.getDamager());
                    nearbyEntities.remove(event.getEntity());
                    for (Entity oVictim : nearbyEntities) {
                        if(oVictim instanceof Damageable){
                            ((Player) event.getDamager()).attack(oVictim);
                        }
                    }
                    Location location = event.getEntity().getLocation();
                    World world = location.getWorld();
                    world.spawnParticle(Particle.SMOKE_NORMAL, location, 500, 3, 1, 3, 0);

                }
            }
        }else
            return;


    }


}
