package seos.oldwindseos;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.ArrayList;
import java.util.List;


public class Summer implements Listener {

    Utils utils = new Utils();
    Oldwindseos main = Oldwindseos.getPlugin(Oldwindseos.class);


    @EventHandler
    public void blitz(EntityShootBowEvent event) {
        if (event.getEntity() instanceof Player) {
            List<String> falkLore = new ArrayList<>();
            falkLore.add("We require more arrows.");
            if (utils.hasGodLore(((Player) event.getEntity()).getPlayer(), falkLore)) {
                Arrow arrow = (Arrow) event.getProjectile();
                main.blitzArrows.putIfAbsent(arrow.getUniqueId(), (Player) arrow.getShooter());
            }
        } else
            return;

    }

    @EventHandler
    public void blitzLand(ProjectileHitEvent event){
        if (event.getEntity() instanceof Arrow){
            Arrow arrow = (Arrow) event.getEntity();
            main.blitzArrows.remove(arrow.getUniqueId());
        } else
            return;
    }

}