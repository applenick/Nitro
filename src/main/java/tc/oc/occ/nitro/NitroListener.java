package tc.oc.occ.nitro;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import tc.oc.occ.nitro.discord.DiscordBot;
import tc.oc.occ.nitro.events.NitroUserAddEvent;
import tc.oc.occ.nitro.events.NitroUserRemoveEvent;

public class NitroListener implements Listener {

  private NitroCloudy plugin;
  private DiscordBot api;

  public NitroListener(NitroCloudy plugin, DiscordBot bot) {
    this.plugin = plugin;
    this.api = bot;
  }

  @EventHandler
  public void onNitroAdd(NitroUserAddEvent event) {
    api.alert(
        "<:nitro:842826131581566976> Added `nitro-boost` rank to (`"
            + event.getUser().toString()
            + "`)");
    Bukkit.getServer()
        .dispatchCommand(
            Bukkit.getConsoleSender(),
            api.getConfig().getRedemptionCommand(event.getUser().getPlayerId().toString()));
    api.getConfig().save(plugin.getConfig());
    plugin.saveConfig();
  }

  @EventHandler
  public void onNitroRemove(NitroUserRemoveEvent event) {
    api.alert(
        "<:nitro:842826131581566976> Removed `nitro-boost` rank from (`"
            + event.getUser().toString()
            + "`)");
    Bukkit.getServer()
        .dispatchCommand(
            Bukkit.getConsoleSender(),
            api.getConfig().getRemovalCommand(event.getUser().getPlayerId().toString()));
    api.getConfig().removeNitro(event.getUser());
  }
}
