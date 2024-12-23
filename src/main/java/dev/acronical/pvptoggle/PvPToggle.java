package dev.acronical.pvptoggle;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.ChatColor;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

public final class PvPToggle extends JavaPlugin {

    @Override
    public void onEnable() {
        versionCheck();
        getServer().getPluginManager().registerEvents(new PluginEvents(), this);
        getCommand("pvp").setExecutor(new PvPCommand());
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[PvPToggle] Plugin has been enabled!");
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[PvPToggle] Plugin has been disabled!");
    }

    private void versionCheck() {
        try {
            String latestVersion = getLatestVersion();
            if (!getDescription().getVersion().equals(latestVersion)) {
                getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "[PvP Toggle] A new version is available!");
                getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "[PvP Toggle] Current version: " + getDescription().getVersion());
                getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "[PvP Toggle] Latest version: " + latestVersion);
                getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "[PvP Toggle] Download it from https://www.acronical.uk/projects");
            } else {
                getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[PvP Toggle] Plugin is up to date!");
                getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[PvP Toggle] Current version: " + getDescription().getVersion());
                getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[PvP Toggle] Latest version: " + latestVersion);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getLatestVersion() throws Exception {
        URL url = new URI("https://api.github.com/repos/AcronicalYT/PvP-Toggle/releases").toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "OutcastsBattleRoyale");
        try {
            connection.connect();
            if (connection.getResponseCode() == 200) {
                Scanner scanner = new Scanner(connection.getInputStream());
                StringBuilder response = new StringBuilder();
                while (scanner.hasNext()) {
                    response.append(scanner.nextLine());
                }
                scanner.close();
                return response.toString().split("\"tag_name\":\"")[1].split("\"")[0];
            } else {
                throw new RuntimeException("Failed to check for updates. Response code: " + connection.getResponseCode() + " " + connection.getResponseMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred while checking for updates.";
        }
    }
}
