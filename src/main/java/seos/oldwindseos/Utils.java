package seos.oldwindseos;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Utils {

    /***
     * @param player
     * @return If both hands are full.
     ***/
    public boolean isFull(Player player) {
        ItemStack mainHand = player.getInventory().getItemInMainHand();
        ItemStack offHand = player.getInventory().getItemInOffHand();
        return (offHand.getType() != Material.AIR && mainHand.getType() != Material.AIR);
    }

    public boolean hasGodLore(Player player, List<String> lores, String slot) {
        if (slot.toLowerCase(Locale.ROOT).contains("both")){
            ItemStack mainHand = player.getInventory().getItemInMainHand();
            ItemStack offHand = player.getInventory().getItemInOffHand();
            List<String> sLores = new ArrayList<>();
            sLores.add("");
            try {
                sLores.addAll(Objects.requireNonNull(mainHand.getItemMeta().getLore()));
                sLores.addAll(Objects.requireNonNull(offHand.getItemMeta().getLore()));
            } catch (NullPointerException exception){

            }
            return sLores.containsAll(lores);
        } else if (slot.toLowerCase(Locale.ROOT).contains("main")){
            ItemStack mainHand = player.getInventory().getItemInMainHand();
            List<String> sLores = new ArrayList<>();
            sLores.add("");
            try {
                sLores.addAll(Objects.requireNonNull(mainHand.getItemMeta().getLore()));
            } catch (NullPointerException exception){

            }
            return sLores.containsAll(lores);
        } else if (slot.toLowerCase(Locale.ROOT).contains("off")) {
            ItemStack offHand = player.getInventory().getItemInOffHand();
            List<String> sLores = new ArrayList<>();
            sLores.add("");
            try {
                sLores.addAll(Objects.requireNonNull(offHand.getItemMeta().getLore()));
            } catch (NullPointerException exception) {

            }
            return sLores.containsAll(lores);
        }else
            return false;
    }

    /***
     *
     * @param player
     * @return if items have the lore required
     */



    @SuppressWarnings("deprecation")
    public boolean hasGodLore(Player player, List<String> lores) {
        ItemStack mainHand = player.getInventory().getItemInMainHand();
        ItemStack offHand = player.getInventory().getItemInOffHand();
        List<String> sLores = new ArrayList<>();
        sLores.add("");
        try {
            sLores.addAll(Objects.requireNonNull(mainHand.getItemMeta().getLore()));
            sLores.addAll(Objects.requireNonNull(offHand.getItemMeta().getLore()));
        } catch (NullPointerException exception){

        }
        return sLores.containsAll(lores);
    }

    /***
     * Changes xp of player by a percentage of a full bar
     ***/
    public void changeXP(Player player, float percent) {
        int currentLevel = player.getLevel();
        float barProgress = player.getExp();
        float toRemove = barProgress - (percent);
        if (toRemove < 0) {
            player.setLevel(currentLevel - 1);
            player.setExp(toRemove + 1f);
        } else
            player.setExp(toRemove);
    }

    public boolean enoughLvl(Player player, float percent) {
        if (player.getLevel() < 1) {
            float barProgress = player.getExp();
            float toRemove = barProgress - percent;
            if (toRemove < 0) {
                player.sendMessage(ChatColor.RED + "Not enough XP");
                return false;
            }
        }
        return true;
    }

    public boolean inWorld(World world, List<String> worldList){
        String worldName = world.getName();
        return (worldList.contains(worldName));
    }

}

