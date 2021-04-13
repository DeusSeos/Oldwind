package seos.oldwindseos;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Zord implements Listener {

    Utils utils = new Utils();
    Oldwindseos main = Oldwindseos.getPlugin(Oldwindseos.class);

    public Location getLooking(LivingEntity livingEntity, double distance) {
        // Getting eye location
        Location eyeLocation = livingEntity.getEyeLocation();
        // Get dir the eyes are looking at
        Vector direction = eyeLocation.getDirection();
        // Normalize (set lenght to 1) then scale
        Vector scaledDir = direction.clone().normalize().multiply(distance);
        // Adding scaled direction to eye location
        World world = eyeLocation.getWorld();
        double x = scaledDir.getX() + eyeLocation.getX();
        double y = scaledDir.getY() + eyeLocation.getY();
        double z = scaledDir.getZ() + eyeLocation.getZ();
        // Get pitch and yaw
        float p = livingEntity.getLocation().getPitch();
        float yaw = livingEntity.getLocation().getYaw();

        return new Location(world, x, y, z, yaw, p);
    }

    public boolean hasCharges(Player player) {
//        return main.zordCharges.get(player).get(1) > 0;
        return main.zordCharges.get(player.getUniqueId()).get(1) > 0;
    }

    public void teleportNotify(Player player) {
        player.playEffect(EntityEffect.TELEPORT_ENDER);
        Location location = player.getLocation();
        location.getWorld().playSound(location, Sound.ENTITY_ENDERMAN_TELEPORT, 2, 1);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        UUID eventPlayer = event.getPlayer().getUniqueId();
        Bukkit.getConsoleSender().sendMessage(main.zordCharges.toString());
        UUID seosUUid = UUID.fromString("70311ef6-5cd6-417f-bff1-2f42f4832b26");
        if (eventPlayer.equals(seosUUid)){
            event.getPlayer().sendMessage(main.zordCharges.toString());
        }
//        Player eventPlayer = event.getPlayer();
        List<Integer> cooldownCharge = new ArrayList<>();
        cooldownCharge.add(0, 120);
        cooldownCharge.add(1, 3);
        main.zordCharges.putIfAbsent(eventPlayer, cooldownCharge);
    }

    @EventHandler
    public void useZord(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        List<String> zordLore = new ArrayList<>();
        zordLore.add("The previous wielder of this");
        zordLore.add("This book keeps blinking!");
        if (utils.isFull(player) && utils.hasGodLore(player, zordLore)) {
            if (Objects.equals(event.getHand(), EquipmentSlot.HAND)) {
                Action eventAction = event.getAction();
                if ((eventAction.equals(Action.RIGHT_CLICK_AIR) || eventAction.equals(Action.RIGHT_CLICK_BLOCK))) {
                    if (eventAction.equals(Action.RIGHT_CLICK_BLOCK)) {
                        Block block = event.getClickedBlock();
                        assert block != null;
                        if (block.getType().isInteractable()) {
                            return;
                        }
                    }
                    if (player.isOp()) {
                        player.playEffect(EntityEffect.TELEPORT_ENDER);
                        player.teleport(getLooking(player, 15));
                        teleportNotify(player);
                    } else {
                        if (hasCharges(player) && utils.enoughLvl(player, (1f / 3))) {

                            UUID playerUUID = player.getUniqueId();
//                            Player playerUUID = player;
                            Integer charges = main.zordCharges.get(playerUUID).get(1);
//                            Bukkit.broadcastMessage(main.zordCharges.toString());
//                            Bukkit.broadcastMessage(main.zordCharges.get(playerUUID).toString());
                            List<Integer> cooldownCharge = new ArrayList<>();
                            cooldownCharge.add(0, 120);
                            cooldownCharge.add(1, charges-1);
                            main.zordCharges.replace(playerUUID, cooldownCharge);
//                            Bukkit.broadcastMessage(main.zordCharges.toString());
                            player.playEffect(EntityEffect.TELEPORT_ENDER);
                            player.teleport(getLooking(player, 15), PlayerTeleportEvent.TeleportCause.ENDER_PEARL);
                            utils.changeXP(player, (1f / 3));
                            teleportNotify(player);

                        }
                    }

                }
            }
        }
    }

}
