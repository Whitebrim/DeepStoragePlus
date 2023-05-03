package me.darkolythe.deepstorageplus.dsu.managers;

import me.darkolythe.deepstorageplus.DeepStoragePlus;
import me.darkolythe.deepstorageplus.dsu.StorageUtils;
import me.darkolythe.deepstorageplus.utils.LanguageManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.List;

public class WirelessManager {

    public static ItemStack createTerminal() {
        ItemStack terminal = new ItemStack(Material.FLOWER_BANNER_PATTERN);
        ItemMeta meta = terminal.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setDisplayName(ChatColor.AQUA + LanguageManager.getValue("terminal"));
        meta.setLore(Arrays.asList(ChatColor.GRAY + "---------------------",
                                   ChatColor.RED.toString() + ChatColor.BOLD.toString() + LanguageManager.getValue("unlinked"),
                                   ChatColor.GRAY.toString() + LanguageManager.getValue("clicktolink"),
                                   ChatColor.GRAY + "---------------------",
                                   ChatColor.AQUA.toString() + LanguageManager.getValue("terminal")));
        meta.setCustomModelData(20025);
        //meta.getPersistentDataContainer().set(new NamespacedKey(DeepStoragePlus.getInstance(), "terminal"), PersistentDataType.BYTE, (byte)1);
        terminal.setItemMeta(meta);
        return terminal;
    }

    public static ItemStack createReceiver() {
        ItemStack receiver = new ItemStack(Material.IRON_NUGGET);
        ItemMeta meta = receiver.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setDisplayName(ChatColor.AQUA + LanguageManager.getValue("receiver"));
        meta.setCustomModelData(20024);
        //meta.getPersistentDataContainer().set(new NamespacedKey(DeepStoragePlus.getInstance(), "receiver"), PersistentDataType.BYTE, (byte)1);
        receiver.setItemMeta(meta);
        return receiver;
    }

    public static void updateTerminal(ItemStack terminal, int x, int y, int z, World world) {
        ItemMeta meta = terminal.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        String range = DeepStoragePlus.maxrange == -1 ? "none" : String.valueOf(DeepStoragePlus.maxrange);

        meta.setLore(Arrays.asList(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + LanguageManager.getValue("linked"),
                                   ChatColor.GRAY.toString() + "X: " + ChatColor.RED.toString() + x,
                                   ChatColor.GRAY.toString() + "Y: " + ChatColor.RED.toString() + y,
                                   ChatColor.GRAY.toString() + "Z: " + ChatColor.RED.toString() + z,
                                   ChatColor.GRAY.toString() + LanguageManager.getValue("world") + ": " + ChatColor.RED.toString() + world.getName(),
                                   ChatColor.GRAY.toString() + LanguageManager.getValue("maxdistance") + ": " + ChatColor.RED.toString() + range,
                                   "",
                                   ChatColor.GRAY + LanguageManager.getValue("shiftswap"),
                                   ChatColor.AQUA.toString() + LanguageManager.getValue("terminal")));
        terminal.setItemMeta(meta);
        //terminal.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
    }

    public static Inventory getWirelessDSU(ItemStack terminal, Player player) {
        ItemMeta meta = terminal.getItemMeta();
        List<String> lore = meta.getLore();
        int x = Integer.parseInt(lore.get(1).replaceAll("^[^_]*:", "").replace(" ", "").replace(ChatColor.RED.toString(), ""));
        int y = Integer.parseInt(lore.get(2).replaceAll("^[^_]*:", "").replace(" ", "").replace(ChatColor.RED.toString(), ""));
        int z = Integer.parseInt(lore.get(3).replaceAll("^[^_]*:", "").replace(" ", "").replace(ChatColor.RED.toString(), ""));
        int range = -1;
        if (!lore.get(5).contains("none") && lore.get(5).length() > 1) {
            range = Integer.parseInt(lore.get(5).replaceAll("^[^_]*:", "").replace(" ", "").replace(ChatColor.RED.toString(), ""));
        }

        String world = ChatColor.GRAY.toString() + LanguageManager.getValue("world") + ": " + player.getWorld().getName();
        String newWorld = ChatColor.GRAY.toString() + LanguageManager.getValue("world") + ": " + ChatColor.RED.toString() + player.getWorld().getName();
        if (!world.equals(lore.get(4)) && !newWorld.equals(lore.get(4))) {
            player.sendMessage(DeepStoragePlus.prefix + ChatColor.RED + LanguageManager.getValue("cantopenin") + " " + ChatColor.GRAY + player.getWorld().getName());
            return null;
        }

        Block block = player.getWorld().getBlockAt(x, y, z);
        if (block.getType() != Material.CHEST) {
            player.sendMessage(DeepStoragePlus.prefix + ChatColor.RED + LanguageManager.getValue("dsunolongerthere"));
            return null;
        }

        if (block.getLocation().distance(player.getLocation()) > range && range != -1) {
            player.sendMessage(DeepStoragePlus.prefix + ChatColor.RED + LanguageManager.getValue("toofar"));
            return null;
        }

        Chest chest = (Chest) block.getState();
        if (StorageUtils.isDSU(chest.getInventory()) || StorageUtils.isSorter(chest.getInventory())) {
            return chest.getInventory();
        } else {
            player.sendMessage(DeepStoragePlus.prefix + ChatColor.RED + LanguageManager.getValue("dsunolongerthere"));
        }

        return null;
    }

    public static boolean isWirelessTerminal(ItemStack item) {
        if (item != null && item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if (meta.hasCustomModelData() && meta.getCustomModelData() == 20025) {
                return true;
            }
        }
        return false;
    }
}
