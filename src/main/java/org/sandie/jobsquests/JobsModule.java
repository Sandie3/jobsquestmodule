package org.sandie.jobsquests;

import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.container.Job;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import me.blackvein.quests.Quests;

import java.io.File;
import java.util.*;

public class JobsModule extends JavaPlugin {

    private static final Quests quests = (Quests) Bukkit.getServer().getPluginManager().getPlugin("Quests");
    private static final String moduleName = "jobs";
    private static final Map.Entry<String, Short> moduleItem = new AbstractMap.SimpleEntry<>("IRON_SWORD", (short)0);

    public static Quests getQuests() {
        return quests;
    }

    public static String getModuleName() {
        return moduleName;
    }

    public static Map.Entry<String, Short> getModuleItem() {
        return moduleItem;
    }

    @Override
    public void onEnable() {
        getLogger().severe(ChatColor.RED + "Move this jar to your " + File.separatorChar + "Quests" + File.separatorChar
                + "modules folder!");
        getServer().getPluginManager().disablePlugin(this);
        setEnabled(false);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static String getSuggestions() {
        final List<Job> suggestionList = Jobs.getJobs();
        final StringBuilder text = new StringBuilder("\n");
        for (int i = 0; i < suggestionList.size(); i++) {
            final String name = suggestionList.get(i).getJobFullName();
            text.append(ChatColor.AQUA).append(name);
            if (i < (suggestionList.size() - 1)) {
                text.append(ChatColor.GRAY).append(", ");
            }
        }
        return text.toString();
    }

}
