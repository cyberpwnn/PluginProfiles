# PluginProfiles
Hotswap Loads of Plugins

This plugin is intended for development, NOT PRODUCTION. Please understand that this should not be used for servers that handle players as a production server. This is for developers using a test server. 

## The Problem
One issue you may have encountered many times, is when you have several projects going at once, all which may or may not conflict with each other (on the same running server) or require different dependencies (such as valult, world edit or something else), however you try to switch a project, but end up having to unload plugins because they cannot be moved, or end up stopping the server all togeather.

## The Solution
Simply Place plugins in subfolders as "profiles". Each subfolder can contain plugins that are NOT enabled with other plugins. This means if you have 3 projects that all depend on different plugins, or cannot coexist on the same runtime environment for some reason, seperate them within profiles. 

## The Structure
```
\---Plugins
  \---PluginProfiles
      \---profiles
          +---A
          |   +---Essentials
          |   +---PluginMetrics
          |   +---React
          |   \---WorldEdit
          \---B
              +---WorldEdit
              +---WorldGuard
              \---WorldGuardTitles
```
In this example you can see there are two profiles named A and B. Each folder has different (or the same) plugins as the other. If you use 

```/profile swap A```

This would load Essentials, React, and WorldEdit. (If another profile was loaded, That profile would disable first.)

```/profile swap B```

This would DISABLE and UNLOAD all of the plugins from A (but not all of the server plugins), then proceed to load all of the plugins from B.

## Same Plugin, Different Configuration
Since the plugins are seperated, if you have two of the same plugins in different profiles, their plugin data folders are also located in the profile folder NOT THE PLUGIN FOLDER. This means that if you have two of the same plugins in differnet profiles, configured differnetly, they will also swap correctly.

## Commands & Permissions
There is only one permission: ```pluginprofiles.use```

###### Commands
* /pluginprofiles (/pp /profile /prof) - Lists the following commands.
* /profile swap <profile> - Hotswaps profile (disabling the previous one)
* /profile list - Lists all profiles (showing the loaded one in green)
* /profile info - Displays the loaded profile
* /profile drop - Drops any loaded profile (disables all plugins in the profile)

## /reload
Reloading the server while testing and developing is very convenient if you use it. To prevent your development process getting more tedious by adding another command (having to load the profile every time you reload), there is a persistance.yml file which stores what profile you had previously loaded. The last profile you had loaded will be loaded when PluginProfiles starts up.

## Performance
Since unloading plugins sometimes can glitch the windows filesystem into thinking the file is still locked, we call System.gc() to fix this problem before proceeding. This can cause some latency (between 100-2000 miliseconds), hence why it is not a great idea to use this in a production environment. However, by doing this, it does cleanly disable the plugins so you can rename them, or delete them. Disabling the plugin does not unload it.
