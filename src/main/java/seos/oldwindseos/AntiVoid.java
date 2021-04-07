package seos.oldwindseos;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.ArrayList;
import java.util.List;

public class AntiVoid implements Listener {

    Utils utils = new Utils();

    @EventHandler
    @SuppressWarnings("deprecation")
    public void onVoidDamage(EntityDamageEvent event){
        if ((event.getEntity() instanceof Player) && (event.getCause() == EntityDamageEvent.DamageCause.VOID)){
            Player player = (Player) event.getEntity();
            World world = player.getLocation().getWorld();
            List<String> worldList = new ArrayList<String>();
            worldList.add("test");
            worldList.add("test_nether");
            if(utils.inWorld(world, worldList)){
                Location loc = Bukkit.getServer().getWorld("test").getSpawnLocation();
                player.teleport(loc);
            }
        }

    }

}
