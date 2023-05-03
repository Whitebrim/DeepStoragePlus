package me.darkolythe.deepstorageplus.dsu.listeners;

import me.darkolythe.deepstorageplus.DeepStoragePlus;
import me.darkolythe.deepstorageplus.utils.ItemList;
import me.darkolythe.deepstorageplus.utils.LanguageManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class WrenchListener implements Listener {

    private DeepStoragePlus main;
    public WrenchListener(DeepStoragePlus plugin) {
        main = plugin;
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    private void onWrenchUse(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();

            Block block = event.getClickedBlock();
            if (player.getInventory().getItemInMainHand().hasItemMeta() &&
                    player.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData() &&
                    (player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 20020 // Storage Wrench
                    || player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 20021)) { // Sorter Wrench
                if (block != null && block.getType() == Material.CHEST) {
                    if (player.hasPermission("deepstorageplus.create")) {
                        if (!event.isCancelled()) {
                            event.setCancelled(true);
                            if (isInventoryEmpty(block)) {
                                if (sizeOfInventory(block) == 54) {
                                    if (player.getInventory().getItemInMainHand().hasItemMeta() &&
                                            player.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData() &&
                                            player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 20020) { // Storage Wrench
                                        createDSU(block);
                                        player.getInventory().getItemInMainHand().setAmount(0);
                                        player.sendMessage(DeepStoragePlus.prefix + ChatColor.GREEN + LanguageManager.getValue("dsucreate"));
                                    } else if (player.getInventory().getItemInMainHand().hasItemMeta() &&
                                            player.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData() &&
                                            player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 20021) { // Sorter Wrench
                                        createSorter(block);
                                        player.getInventory().getItemInMainHand().setAmount(0);
                                        player.sendMessage(DeepStoragePlus.prefix + ChatColor.GREEN + LanguageManager.getValue("sortercreate"));
                                    }
                                } else {
                                    player.sendMessage(DeepStoragePlus.prefix + ChatColor.RED + LanguageManager.getValue("chestmustbedouble"));
                                }
                            } else {
                                player.sendMessage(DeepStoragePlus.prefix + ChatColor.RED + LanguageManager.getValue("chestmustbeempty"));
                            }
                        }
                    } else {
                        player.sendMessage(DeepStoragePlus.prefix + ChatColor.RED + LanguageManager.getValue("nopermission"));
                    }
                } else if (block != null && block.getType() == Material.GRASS_BLOCK) { // Handle using the "shovel" to make dirt paths
                    event.setCancelled(true);
                }
            }


            if (player.getInventory().getItemInMainHand().hasItemMeta() &&
                    player.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData() &&
                    (player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 20022 || // Link module disconnected
                    player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 20023)) { // Link module connected
                if (block != null && block.getType() == Material.CHEST) {
                    if (!event.isCancelled()) {
                        event.setCancelled(true);
                        if (isDSU(block) || isSorter(block)) {
                            ItemMeta linkModuleMeta = player.getInventory().getItemInMainHand().getItemMeta();
                            linkModuleMeta.setCustomModelData(20023);
                            linkModuleMeta.setLore(Arrays.asList(ChatColor.BLUE + String.format("%s %s %s %s", block.getWorld().getName(), block.getX(), block.getY(), block.getZ())));
                            player.getInventory().getItemInMainHand().setItemMeta(linkModuleMeta);
                            player.sendMessage(DeepStoragePlus.prefix + ChatColor.GREEN + LanguageManager.getValue("linked"));
                        } else {
                            player.sendMessage(DeepStoragePlus.prefix + ChatColor.RED + LanguageManager.getValue("cantlink"));
                        }
                    }
                } else if (player.getInventory().getItemInMainHand().hasItemMeta() &&
                        player.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData() &&
                        player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 20023){ // Link module disconnected
                        ItemMeta linkModuleMeta = player.getInventory().getItemInMainHand().getItemMeta();
                        linkModuleMeta.setCustomModelData(20022);
                        linkModuleMeta.setLore(Arrays.asList(""));
                        player.getInventory().getItemInMainHand().setItemMeta(linkModuleMeta);
                        player.sendMessage(DeepStoragePlus.prefix + ChatColor.GREEN + LanguageManager.getValue("unlinked"));
                }
            }
        }
    }

    private static boolean isInventoryEmpty(Block block) {
        Inventory inv = getInventoryFromBlock(block);
        for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) != null) {
                return false;
            }
        }
        return true;
    }

    private static int sizeOfInventory(Block block) {
        Inventory inv = getInventoryFromBlock(block);
        return inv.getSize();
    }

    private void createDSU(Block block) {
        Chest chest = (Chest) block.getState();
        chest.setCustomName(DeepStoragePlus.DSUname);
        chest.update();
    }

    private boolean isDSU(Block block) {
        Chest chest = (Chest) block.getState();
        if (chest.getInventory().getHolder() instanceof DoubleChest) {
            DoubleChest doubleChest = (DoubleChest) chest.getInventory().getHolder();
            Chest leftChest = (Chest) doubleChest.getLeftSide();
            Chest rightChest = (Chest) doubleChest.getRightSide();
            return (leftChest.getCustomName() != null && leftChest.getCustomName().equals(DeepStoragePlus.DSUname)) ||
                    (rightChest.getCustomName() != null && rightChest.getCustomName().equals(DeepStoragePlus.DSUname));
        }
        return chest.getCustomName() != null && chest.getCustomName().equals(DeepStoragePlus.DSUname);
    }

    private void createSorter(Block block) {
        Chest chest = (Chest) block.getState();
        chest.setCustomName(DeepStoragePlus.sortername);
        chest.update();
    }

    private boolean isSorter(Block block) {
        Chest chest = (Chest) block.getState();
        if (chest.getInventory().getHolder() instanceof DoubleChest) {
            DoubleChest doubleChest = (DoubleChest) chest.getInventory().getHolder();
            Chest leftChest = (Chest) doubleChest.getLeftSide();
            Chest rightChest = (Chest) doubleChest.getRightSide();
            return (leftChest.getCustomName() != null && leftChest.getCustomName().equals(DeepStoragePlus.sortername)) ||
                    (rightChest.getCustomName() != null && rightChest.getCustomName().equals(DeepStoragePlus.sortername));
        }
        return chest.getCustomName() != null && chest.getCustomName().equals(DeepStoragePlus.sortername);
    }

    private static Inventory getInventoryFromBlock(Block block) {
        BlockState bs = block.getState();
        Chest chest = (Chest) bs;
        return chest.getInventory();
    }
}
