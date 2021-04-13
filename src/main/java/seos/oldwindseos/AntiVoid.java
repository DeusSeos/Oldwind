package seos.oldwindseos;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



public class AntiVoid implements Listener {

    Utils utils = new Utils();

    @EventHandler
    public void onVoidDamage(EntityDamageEvent event){
        if ((event.getEntity() instanceof Player) && (event.getCause() == EntityDamageEvent.DamageCause.VOID)){
            Player player = (Player) event.getEntity();
            World world = player.getLocation().getWorld();
            List<String> worldList = new ArrayList<>();
            worldList.add("test");
            worldList.add("test_nether");
            worldList.add("world");
            if(utils.inWorld(world, worldList)){
                if(player.getBedSpawnLocation() != null){ //check if bed location even exists
                    player.teleport(player.getBedSpawnLocation());

                } else {
                    Location loc = Objects.requireNonNull(Bukkit.getServer().getWorld("world")).getSpawnLocation();
                    Bukkit.broadcastMessage(player.getVelocity().toString());
                    player.teleport(loc);
                }

                player.setFallDistance(0.0F);





            }
        }

    }

}
