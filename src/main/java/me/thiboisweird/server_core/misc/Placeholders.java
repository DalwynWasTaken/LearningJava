package me.thiboisweird.server_core.misc;
import me.thiboisweird.server_core.Server_core;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.jetbrains.annotations.NotNull;

public class Placeholders extends PlaceholderExpansion {
    private final Server_core plugin;

    public Placeholders(Server_core plugin) {
        this.plugin = plugin;
    }
    @Override
    public @NotNull String getIdentifier() {
        return "ThiboHisCore";
    }

    @Override
    public @NotNull String getAuthor() {
        return "ThiboIsWeird";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true; // This is required or else PlaceholderAPI will unregister the Expansion on reload
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if(params.equalsIgnoreCase("name")) {
            return player == null ? null : player.getName();
        }

        if(params.equalsIgnoreCase("plr_online")) {
            try {
                if (player.isOnline()){
                    return ChatColor.translateAlternateColorCodes('&', "&aOnline");
                }
                return ChatColor.translateAlternateColorCodes('&', "&cOffline");
            } catch (NullPointerException e) {
                return ChatColor.translateAlternateColorCodes('&', "&cOffline");
            }

        }

        if(params.equalsIgnoreCase("placeholder2")) {
            return "Placeholder Text 2";
        }

        return null;
    }
}
