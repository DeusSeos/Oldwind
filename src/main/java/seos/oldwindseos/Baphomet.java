package seos.oldwindseos;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.server.BroadcastMessageEvent;

import java.util.Collection;

public class Baphomet implements Listener {

    Utils utils = new Utils();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBaphHit(EntityDamageByEntityEvent event) {



        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        for(Player player: players){
            player.swingMainHand();
            player.attack(event.getDamager());
        }


//        if(!event.isCancelled()) {
//            Bukkit.broadcastMessage(event.toString());
//            if (event.getDamager() instanceof Player) {
//                List<String> baphLore = new ArrayList<>();
//                baphLore.add("Satan has his miracles, too.");
//                if (utils.hasGodLore( (Player) event.getDamager(), baphLore, "main")) {
//                    Entity victim = event.getEntity();
//                    List<Entity> nearbyEntities = victim.getNearbyEntities(3, 3, 3);
//                    nearbyEntities.remove(event.getDamager());
//                    nearbyEntities.remove(event.getEntity());
//                    LivingEntity livingEntity = (LivingEntity) event.getDamager();
//                    for (Entity oVictim : nearbyEntities) {
//                        if(oVictim instanceof Damageable){
//                            Bukkit.broadcastMessage(oVictim.getName());
//                            livingEntity.attack(oVictim);
//                        }
//                    }
//                    Location location = event.getEntity().getLocation();
//                    World world = location.getWorld();
//                    world.spawnParticle(Particle.SMOKE_NORMAL, location, 500, 3, 1, 3, 0);
//
//                }
//            }
//        }else
//            return;
//

    }


}
