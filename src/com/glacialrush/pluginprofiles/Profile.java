package com.glacialrush.pluginprofiles;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class Profile
{
	private File folder;
	private GList<Plugin> plugins;
	
	public Profile(File folder)
	{
		this.folder = folder;
		this.plugins = new GList<Plugin>();
	}
	
	public void start()
	{
		for(File i : folder.listFiles())
		{
			if(i.getName().endsWith(".jar") && i.isFile())
			{
				Plugin p = IO.load(i);
				
				if(p != null)
				{
					plugins.add(p);
				}
			}
		}
	}
	
	public void stop()
	{
		for(Plugin i : new GList<Plugin>(Bukkit.getPluginManager().getPlugins()))
		{
			if(plugins.contains(i))
			{
				IO.unload(i);
				plugins.remove(i);
			}
		}
	}

	public File getFolder()
	{
		return folder;
	}

	public void setFolder(File folder)
	{
		this.folder = folder;
	}

	public GList<Plugin> getPlugins()
	{
		return plugins;
	}

	public void setPlugins(GList<Plugin> plugins)
	{
		this.plugins = plugins;
	}
}
