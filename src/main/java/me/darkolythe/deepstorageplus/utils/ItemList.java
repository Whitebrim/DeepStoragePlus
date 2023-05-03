package me.darkolythe.deepstorageplus.utils;

import me.darkolythe.deepstorageplus.DeepStoragePlus;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

import static me.darkolythe.deepstorageplus.dsu.managers.WirelessManager.createReceiver;
import static me.darkolythe.deepstorageplus.dsu.managers.WirelessManager.createTerminal;

public class ItemList {

    private DeepStoragePlus main;

    public ItemStack storageCell1K;
    public ItemStack storageCell4K;
    public ItemStack storageCell16K;
    public ItemStack storageCell64K;
    public ItemStack storageCell256K;
    public ItemStack storageCell1024k;
    public ItemStack storageContainer1K;
    public ItemStack storageContainer4K;
    public ItemStack storageContainer16K;
    public ItemStack storageContainer64K;
    public ItemStack storageContainer256K;
    public ItemStack storageContainer1024k;
    public ItemStack creativeStorageContainer;
    public ItemStack storageWrench;
    public ItemStack sorterWrench;
    public ItemStack receiver;
    public ItemStack terminal;
    public ItemStack speedUpgrade;
    public ItemStack linkModule;

    public Map<String, ItemStack> itemListMap = new HashMap<>();

    public ItemList(DeepStoragePlus plugin) {
        this.main = plugin; // set it equal to an instance of main


        // Item Definitions
        this.storageCell1K = createStorageCell(20001, "storage_cell_1k", ChatColor.WHITE.toString() + LanguageManager.getValue("storagecell") + " " + ChatColor.GRAY.toString() + ChatColor.BOLD.toString() + "1K");

        this.storageCell4K = createStorageCell(20002, "storage_cell_4k", ChatColor.WHITE.toString() + LanguageManager.getValue("storagecell") + " " + ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + "4K");

        this.storageCell16K = createStorageCell(20003, "storage_cell_16k", ChatColor.WHITE.toString() + LanguageManager.getValue("storagecell") + " " + ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "16K");

        this.storageCell64K = createStorageCell(20004, "storage_cell_64k", ChatColor.WHITE.toString() + LanguageManager.getValue("storagecell") + " " + ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "64K");

        this.storageCell256K = createStorageCell(20005, "storage_cell_256k", ChatColor.WHITE.toString() + LanguageManager.getValue("storagecell") + " " + ChatColor.BLUE.toString() + ChatColor.BOLD.toString() + "256K");

        this.storageCell1024k = createStorageCell(20006, "storage_cell_1024k", ChatColor.WHITE.toString() + LanguageManager.getValue("storagecell") + " " + ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "1M");

        this.storageContainer1K = createStorageContainer(20011, "storage_container_1k", ChatColor.WHITE.toString() + LanguageManager.getValue("storagecontainer") + " " + ChatColor.GRAY.toString() + ChatColor.BOLD.toString() + "1K");
        createLore(storageContainer1K, getStorageMaxConfig("1kmax"));

        this.storageContainer4K = createStorageContainer(20012, "storage_container_4k", ChatColor.WHITE.toString() + LanguageManager.getValue("storagecontainer") + " " + ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + "4K");
        createLore(storageContainer4K, getStorageMaxConfig("4kmax"));

        this.storageContainer16K = createStorageContainer(20013, "storage_container_16k", ChatColor.WHITE.toString() + LanguageManager.getValue("storagecontainer") + " " + ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "16K");
        createLore(storageContainer16K, getStorageMaxConfig("16kmax"));

        this.storageContainer64K = createStorageContainer(20014, "storage_container_64k", ChatColor.WHITE.toString() + LanguageManager.getValue("storagecontainer") + " " + ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "64K");
        createLore(storageContainer64K, getStorageMaxConfig("64kmax"));

        this.storageContainer256K = createStorageContainer(20015, "storage_container_256k", ChatColor.WHITE.toString() + LanguageManager.getValue("storagecontainer") + " " + ChatColor.BLUE.toString() + ChatColor.BOLD.toString() + "256K");
        createLore(storageContainer256K, this.getStorageMaxConfig("256kmax"));

        this.storageContainer1024k = createStorageContainer(20016, "storage_container_1m", ChatColor.WHITE.toString() + LanguageManager.getValue("storagecontainer") + " " + ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "1M");
        createLore(storageContainer1024k, getStorageMaxConfig("1mmax"));

        this.creativeStorageContainer = createStorageContainer(20010, "creative_storage_container", ChatColor.DARK_PURPLE.toString() + LanguageManager.getValue("creativestoragecontainer"));
        createLore(creativeStorageContainer, Integer.MAX_VALUE);

        this.storageWrench = createStorageWrench(); //20020
        this.sorterWrench = createSorterWrench(); //20021
        this.linkModule = createLinkModule(); // 20022 - disconnected, 20023 - connected
        this.receiver = createReceiver(); // 20024
        this.terminal = createTerminal(); // 20025
        this.speedUpgrade = createSpeedUpgrade(); // 20026

        itemListMap.put("storage_cell_1k", storageCell1K); // 20001
        itemListMap.put("storage_cell_4k", storageCell4K); // 20002
        itemListMap.put("storage_cell_16k", storageCell16K); // 20003
        itemListMap.put("storage_cell_64k", storageCell64K); // 20004
        itemListMap.put("storage_cell_256k", storageCell256K); // 20005
        itemListMap.put("storage_cell_1024k", storageCell1024k); // 20006
        itemListMap.put("creative_storage_container", creativeStorageContainer); // 20010
        itemListMap.put("storage_container_1k", storageContainer1K); // 20011
        itemListMap.put("storage_container_4k", storageContainer4K); // 20012
        itemListMap.put("storage_container_16k", storageContainer16K); // 20013
        itemListMap.put("storage_container_64k", storageContainer64K); // 20014
        itemListMap.put("storage_container_256k", storageContainer256K); // 20015
        itemListMap.put("storage_container_1024k", storageContainer1024k); // 20016
        itemListMap.put("storage_wrench", storageWrench); // 20020
        itemListMap.put("sorter_wrench", sorterWrench); // 20021
        itemListMap.put("receiver", receiver); // 20024
        itemListMap.put("terminal", terminal); // 20025
        itemListMap.put("speed_upgrade", speedUpgrade); // 20026
        itemListMap.put("link_module", linkModule); // 20022 - disconnected, 20023 - connected
    }

    // Helper methods
    public Optional<ItemStack> getItem(String itemName) {
        ItemStack item = null;
        if (itemName == null) {
            return Optional.empty();
        }
        if (itemListMap.containsKey(itemName)) {
            item = itemListMap.get(itemName);
        }
        return Optional.ofNullable(item);
    }

    private int getStorageMaxConfig(String size) {
        if (main.getConfig().getBoolean("countinstacks")) {
            return main.getConfig().getInt(size) * 1024 * 64;
        }
        return main.getConfig().getInt(size) * 1024;
    }

    private ItemStack createStorageCell(int customModelData, String id, String name) {
        ItemStack storageCell = new ItemStack(Material.IRON_NUGGET);
        ItemMeta storageCellMeta = storageCell.getItemMeta();
        storageCellMeta.setDisplayName(name);
        storageCellMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        storageCellMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        storageCellMeta.setCustomModelData(customModelData);
        //storageCellMeta.getPersistentDataContainer().set(new NamespacedKey(this.main, id), PersistentDataType.BYTE, (byte)1);
        storageCell.setItemMeta(storageCellMeta);

        return storageCell;
    }

    private ItemStack createStorageContainer(int customModelData, String id, String name) {
        ItemStack storageCell = new ItemStack(Material.MUSIC_DISC_13);
        ItemMeta storageCellMeta = storageCell.getItemMeta();
        storageCellMeta.setDisplayName(name);
        storageCellMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        storageCellMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        storageCellMeta.setCustomModelData(customModelData);
        //storageCellMeta.getPersistentDataContainer().set(new NamespacedKey(this.main, id), PersistentDataType.BYTE, (byte)1);
        storageCell.setItemMeta(storageCellMeta);

        return storageCell;
    }

    private static void createLore(ItemStack container, int storageMax) {
        int maxTypes = 7;
        List<String> lore = new ArrayList<>();

        ItemMeta meta = container.getItemMeta();
        lore.add(ChatColor.GREEN + LanguageManager.getValue("currentstorage") + ": " + 0 + "/" + storageMax);

        lore.add(ChatColor.GREEN + LanguageManager.getValue("currenttypes") + ": " + 0 + "/" + maxTypes);

        for (int i = 0; i < maxTypes; i++) {
            lore.add(ChatColor.GRAY + " - " + LanguageManager.getValue("empty"));
        }

        meta.setLore(lore);
        container.setItemMeta(meta);
    }

    public static ItemStack createStorageWrench() {
        ItemStack storageWrench = new ItemStack(Material.FLOWER_BANNER_PATTERN);
        ItemMeta storageWrenchMeta = storageWrench.getItemMeta();
        storageWrenchMeta.setDisplayName(ChatColor.AQUA.toString() + LanguageManager.getValue("storageloader"));
        storageWrenchMeta.setLore(Arrays.asList(ChatColor.GRAY + LanguageManager.getValue("clickempty"),
                ChatColor.GRAY + LanguageManager.getValue("tocreatedsu"), "", ChatColor.GRAY + LanguageManager.getValue("onetimeuse")));
        storageWrenchMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        storageWrenchMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        storageWrenchMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        storageWrenchMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        storageWrenchMeta.setCustomModelData(20020);
        //storageWrenchMeta.getPersistentDataContainer().set(new NamespacedKey(DeepStoragePlus.getInstance(), "storage_wrench"), PersistentDataType.BYTE, (byte)1);
        storageWrench.setItemMeta(storageWrenchMeta);

        return storageWrench;
    }

    public static ItemStack createSorterWrench() {
        ItemStack sorterWrench = createStorageWrench();
        ItemMeta wrenchmeta = sorterWrench.getItemMeta();
        wrenchmeta.setDisplayName(ChatColor.AQUA.toString() + LanguageManager.getValue("sorterloader"));
        wrenchmeta.setLore(Arrays.asList(ChatColor.GRAY + LanguageManager.getValue("clickempty"),
                ChatColor.GRAY + LanguageManager.getValue("tocreatesorter"), "", ChatColor.GRAY + LanguageManager.getValue("onetimeuse")));
        //wrenchmeta.getPersistentDataContainer().set(new NamespacedKey(DeepStoragePlus.getInstance(), "sorter_wrench"), PersistentDataType.BYTE, (byte)1);
        wrenchmeta.setCustomModelData(20021);
        sorterWrench.setItemMeta(wrenchmeta);

        return sorterWrench;
    }

    public static ItemStack createLinkModule() {
        ItemStack linkModule = createStorageWrench();
        ItemMeta linkMeta = linkModule.getItemMeta();
        linkMeta.setDisplayName(ChatColor.AQUA.toString() + LanguageManager.getValue("linkmodule"));
        linkMeta.setLore(Arrays.asList(ChatColor.GRAY + LanguageManager.getValue("clicktolink")));
        //linkMeta.getPersistentDataContainer().set(new NamespacedKey(DeepStoragePlus.getInstance(), "link_module"), PersistentDataType.BYTE, (byte)1);
        linkMeta.setCustomModelData(20022);
        linkModule.setItemMeta(linkMeta);

        return linkModule;
    }

    public static ItemStack createSpeedUpgrade() {
        ItemStack item = new ItemStack(Material.GLOWSTONE_DUST);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + LanguageManager.getValue("ioupgrade"));
        meta.setLore(Arrays.asList(ChatColor.GRAY + LanguageManager.getValue("clicktoupgrade")));
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setCustomModelData(20026);
        //meta.getPersistentDataContainer().set(new NamespacedKey(DeepStoragePlus.getInstance(), "speed_upgrade"), PersistentDataType.BYTE, (byte)1);
        item.setItemMeta(meta);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);

        return item;
    }
}
