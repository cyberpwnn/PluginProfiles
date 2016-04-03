package com.glacialrush.pluginprofiles;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class PluginProfiles extends JavaPlugin
{
	private static PluginProfiles instance;
	private ProfileContainer pc;
	
	public void onEnable()
	{
		instance = this;
		this.pc = new ProfileContainer();
	}
	
	public void onDisable()
	{
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if(command.getName().equals("pluginprofiles"))
		{
			if(sender.hasPermission("pluginprofiles.use"))
			{
				if(args.length == 0)
				{
					sender.sendMessage(ChatColor.GREEN + "/profile swap <name>" + ChatColor.YELLOW + " - Unloads Current Profile, and loads the new one.");
					sender.sendMessage(ChatColor.GREEN + "/profile drop" + ChatColor.YELLOW + " - Unloads the profile loaded (if any)");
					sender.sendMessage(ChatColor.GREEN + "/profile info" + ChatColor.YELLOW + " - Lists the current loaded profile (and plugins within)");
					sender.sendMessage(ChatColor.GREEN + "/profile list" + ChatColor.YELLOW + " - Lists all plugins, indicating the loaded one.");
				}
				
				else if(args.length == 1)
				{
					if(args[0].equalsIgnoreCase("info"))
					{
						if(pc.getSelectedProfile() == null)
						{
							sender.sendMessage(ChatColor.RED + "No selected profile. Use /pp list");
							return true;
						}
						
						sender.sendMessage(ChatColor.YELLOW + "Profile: " + pc.getSelectedProfile().getFolder().getPath());
						sender.sendMessage(ChatColor.GREEN + "Plugins(" + pc.getSelectedProfile().getPlugins().size() + "): " + pc.getSelectedProfile().getPlugins().toString(", "));
					}
					
					else if(args[0].equalsIgnoreCase("drop"))
					{
						if(pc.getSelectedProfile() == null)
						{
							sender.sendMessage(ChatColor.RED + "No selected profile. Use /pp list");
							return true;
						}
						
						pc.drop();
						sender.sendMessage(ChatColor.GREEN + "Unloaded Profile.");
					}
					
					else if(args[0].equalsIgnoreCase("list"))
					{
						if(pc.getSelectedProfile() == null)
						{
							File df = PluginProfiles.getInstance().getDataFolder();
							File pr = new File(df, "profiles");
							
							for(File i : pr.listFiles())
							{
								sender.sendMessage(ChatColor.YELLOW + i.getName());
							}
						}
						
						else
						{
							for(File i : pc.getSelectedProfile().getFolder().getParentFile().listFiles())
							{
								if(pc.getSelectedProfile().getFolder().equals(i))
								{
									sender.sendMessage(ChatColor.GREEN + i.getName() + " (LOADED)");
								}
								
								else
								{
									sender.sendMessage(ChatColor.YELLOW + i.getName());
								}
							}
						}
					}
					
					else if(args[0].equalsIgnoreCase("swap"))
					{
						sender.sendMessage(ChatColor.RED + "/profile swap <PluginProfiles/profiles/THE FOLDER TARGET NAME>");
					}
					
					else
					{
						sender.sendMessage(ChatColor.RED + "Use /profile for help");
					}
				}
				
				else if(args.length > 1)
				{
					if(args[0].equalsIgnoreCase("swap"))
					{
						String f = "";
						
						for(int i = 1; i < args.length; i++)
						{
							f = f + " " + args[i];
						}
						
						f = f.substring(1);
						
						pc.setProfile(f, sender);
					}
				}
			}
			
			else
			{
				sender.sendMessage(ChatColor.RED + "No Permission.");
			}
			
			return true;
		}
		
		return false;
	}
	
	public static PluginProfiles getInstance()
	{
		return instance;
	}
}
