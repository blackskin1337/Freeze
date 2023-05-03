package me.dev.nigger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin implements Listener {

    private List<Player> csalok = new ArrayList<>();

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("freeze")) {
            if(args.length != 1) {
                sender.sendMessage(ChatColor.RED + "Használat: /freeze <játékos>");
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if(target == null) {
                sender.sendMessage(ChatColor.RED + "Nincs ilyen játékos!");
                return true;
            }
            if(csalok.contains(target)) {
                csalok.remove(target);
                target.sendMessage(ChatColor.GREEN + "A fagyasztásod fel lett oldva!");
                target.setPlayerListName(target.getPlayerListName());
                return true;
            }
            csalok.add(target);
            target.sendMessage("§7§m--------------------------------");
            target.sendMessage("");
            target.sendMessage("§8[§c!§8] §fLe lettél fagyasztva.");
            target.sendMessage("§8[§c!§8] §fKérlek ne lépj le.");
            target.sendMessage("");
            target.sendMessage("§8[§c!§8] §fAdd meg az AnyDesk kódod!");
            target.sendMessage("§8[§c!§8] §fanydesk.com");
            target.sendMessage("");
            target.sendMessage("§7§m--------------------------------");
            target.setPlayerListName("§b§lFAGYASZTVA §r" + target.getDisplayName());
            return true;
        }
        return false;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if(csalok.contains(player)) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        csalok.remove(player);
    }
    @EventHandler
    public void onClick(InventoryClickEvent event){
        Player player = (Player)event.getWhoClicked();
        if(csalok.contains(player)) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if(event.getEntity() instanceof Player) {
            Player player = (Player)event.getEntity();
            if(csalok.contains(player)) {
                event.setCancelled(true);
            }
        }
        if(event.getDamager() instanceof Player) {
            Player attacker = (Player)event.getDamager();

            if(csalok.contains(attacker)) {
                event.setCancelled(true);
            }
        }
    }
}

