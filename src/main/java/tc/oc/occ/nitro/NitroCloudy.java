package tc.oc.occ.nitro;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import tc.oc.occ.nitro.discord.DiscordBot;

public class NitroCloudy extends JavaPlugin implements Listener {

  private DiscordBot bot;
  private NitroConfig config;

  private static NitroCloudy plugin;

  @Override
  public void onEnable() {
    plugin = this;
    this.saveDefaultConfig();
    this.reloadConfig();
    this.config = new NitroConfig(getConfig());

    this.bot = new DiscordBot(config, getLogger());

    getServer().getPluginManager().registerEvents(new NitroListener(this, bot), this);
  }

  public static NitroCloudy get() {
    return plugin;
  }

  public void callSyncEvent(Event event) {
    getServer().getScheduler().runTask(this, () -> getServer().getPluginManager().callEvent(event));
  }
}
