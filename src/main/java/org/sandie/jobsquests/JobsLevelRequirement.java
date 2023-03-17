package org.sandie.jobsquests;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.container.JobProgression;

import me.blackvein.quests.CustomRequirement;

public class JobsLevelRequirement extends CustomRequirement {

    public int jobLevels;

    public JobsLevelRequirement(){
        setName("Level Requirement");
        setAuthor("Sandie");
        setItem("IRON_SWORD", (short)0);
        addStringPrompt("Job", "- Available Jobs -"
                + JobsModule.getSuggestions(), "ANY");
        addStringPrompt("Levels", "Enter the quantity of levels needed", "1");
        setDisplay("You need a higher job level!");
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
    public boolean testRequirement(Player player, Map<String, Object> data) {
        if (data != null) {
            final String jobName = (String)data.getOrDefault("Job", "ANY");
            this.jobLevels = 1;
            try {
                this.jobLevels = Integer.parseInt((String)data.getOrDefault("Levels", "1"));
            } catch (final NumberFormatException e) {
                // Default to 1
                Bukkit.getLogger().severe("[Jobs Quests Module] Could not get levels! " + this.jobLevels + " For player " + player.getName() + " : " + e);
                return false;
            }
            if (Jobs.getPlayerManager().getJobsPlayer(player) == null) {
                Bukkit.getLogger().severe("[Jobs Quests Module] Could not find jobs player!");
                return false;
            }
            if (jobName.equalsIgnoreCase("ANY")) {
                Bukkit.getLogger().severe("[Jobs Quests Module] Job is unspecified For player " + player.getName());
                return false;
            } else {
                List<JobProgression> jobs = new ArrayList<>(Jobs.getPlayerManager().getJobsPlayer(player).getJobProgression());
                Jobs.getPlayerManager().getJobsPlayer(player).getArchivedJobs().getArchivedJobs().forEach(jobs::add);
                for (JobProgression oneJob : jobs) {
                    if (oneJob.getJob().getName().equals(jobName)) {
                        return oneJob.getLevel() >= this.jobLevels;
                    }
                }
            }
        }
        return false;
    }
}
