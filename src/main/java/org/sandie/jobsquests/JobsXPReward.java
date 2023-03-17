package org.sandie.jobsquests;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.container.JobProgression;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.blackvein.quests.CustomReward;

public class JobsXPReward extends CustomReward {

    public double xpLevels;

    public JobsXPReward(){
        setName("XP Reward");
        setAuthor("Sandie");
        setItem("IRON_SWORD", (short)0);
        setDisplay("%XP Amount% %Job name% job XP");
        addStringPrompt("Job name", "- Available Jobs -"
                + JobsModule.getSuggestions(), "ANY");
        addStringPrompt("XP Amount", "Enter the quantity of xp to give", "1");
    }

    @Override
    public String getModuleName() {
        return JobsModule.getModuleName();
    }

    @Override
    public Map.Entry<String, Short> getModuleItem() {
        return JobsModule.getModuleItem();
    }

    @Override
    public void giveReward(final Player player, final Map <String, Object> data) {
        if (data != null) {
            final String jobName = (String)data.getOrDefault("Job name", "ANY");
            try {
                this.xpLevels = Double.parseDouble((String)data.getOrDefault("XP Amount", "1.0"));
            } catch (final NumberFormatException e) {
                // Default to 1
                Bukkit.getLogger().severe("[Jobs Quests Module] Could not get XP levels! " + this.xpLevels + " For player " + player.getName() + " : " + e);
            }
            if (Jobs.getPlayerManager().getJobsPlayer(player) == null) {
                Bukkit.getLogger().severe("[Jobs Quests Module] Could not find jobs player!");
                return;
            }
            if (jobName.equalsIgnoreCase("ANY")) {
                Bukkit.getLogger().severe("[Jobs Quests Module] Cannot add XP to unspecified job");
            } else {
                List<JobProgression> jobs = new ArrayList<>(Jobs.getPlayerManager().getJobsPlayer(player).getJobProgression());
                Jobs.getPlayerManager().getJobsPlayer(player).getArchivedJobs().getArchivedJobs().forEach(jobs::add);
                for (JobProgression oneJob : jobs) {
                    if (oneJob.getJob().getName().equals(jobName)) {
                        Jobs.getPlayerManager().addExperience(Jobs.getPlayerManager().getJobsPlayer(player), oneJob.getJob(), this.xpLevels);
                    }
                }
            }
        }else{
            Bukkit.getLogger().severe("[Jobs Quests Module] Could not get data OR Data is null!");
        }
    }

}
