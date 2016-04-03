package com.glacialrush.pluginprofiles;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import net.md_5.bungee.api.ChatColor;

public class ProfileContainer
{
	private Profile selectedProfile;
	private FileConfiguration fc;
	
	public ProfileContainer()
	{
		File df = PluginProfiles.getInstance().getDataFolder();
		File pr = new File(df, "profiles");
		File cf = new File(df, "persistance.yml");
		fc = new YamlConfiguration();
		selectedProfile = null;
		
		if(!pr.exists())
		{
			pr.mkdirs();
		}
		
		if(!cf.exists())
		{
			try
			{
				cf.createNewFile();
			} 
			
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		
		else
		{
			try
			{
				fc.load(cf);
				if(fc.contains("last"))
				{
					String mx = fc.getString("last");
					
					if(mx.equals(""))
					{
						return;
					}
					
					setProfile(mx, Bukkit.getConsoleSender());
				}
			} 
			
			catch(IOException | InvalidConfigurationException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void drop()
	{
		if(selectedProfile != null)
		{
			selectedProfile.stop();
			selectedProfile = null;
			save();
		}
	}
	
	public void save()
	{
		File df = PluginProfiles.getInstance().getDataFolder();
		File cf = new File(df, "persistance.yml");
		fc.set("last", selectedProfile == null ? "" : selectedProfile.getFolder().getName());
		try
		{
			fc.save(cf);
		} 
		
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void setProfile(String profile, CommandSender sender)
	{
		File df = PluginProfiles.getInstance().getDataFolder();
		File pr = new File(df, "profiles");
		File file = null;
		
		for(File i : pr.listFiles())
		{
			if(i.isDirectory())
			{
				if(profile.equalsIgnoreCase(i.getName()))
				{
					file = i;
				}
			}
		}
		
		if(file == null)
		{
			sender.sendMessage(ChatColor.RED + "Cannot find profile. (" + profile + ")");
			return;
		}
		
		if(selectedProfile != null)
		{
			sender.sendMessage(ChatColor.GREEN + "Stopping Profile: " + selectedProfile.getFolder().getName());
			selectedProfile.stop();
		}
		
		Profile p = new Profile(file);
		sender.sendMessage(ChatColor.GREEN + "Starting Profile: " + p.getFolder().getName());
		p.start();
		selectedProfile = p;
		sender.sendMessage(ChatColor.GREEN + "Hotswap Complete!");
		save();
	}

	public Profile getSelectedProfile()
	{
		return selectedProfile;
	}

	public void setSelectedProfile(Profile selectedProfile)
	{
		this.selectedProfile = selectedProfile;
	}
}
