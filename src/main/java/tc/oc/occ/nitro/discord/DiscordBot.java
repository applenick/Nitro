package tc.oc.occ.nitro.discord;

import java.util.Optional;
import java.util.logging.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.server.Server;
import tc.oc.occ.nitro.NitroConfig;
import tc.oc.occ.nitro.discord.listener.NitroAddAlert;
import tc.oc.occ.nitro.discord.listener.NitroRedeemer;
import tc.oc.occ.nitro.discord.listener.NitroRemoveAlert;

public class DiscordBot {

  private DiscordApi api;

  private final NitroConfig config;
  private final Logger logger;

  public DiscordBot(NitroConfig config, Logger logger) {
    this.config = config;
    this.logger = logger;
    reload();
  }

  public NitroConfig getConfig() {
    return config;
  }

  public void enable() {
    if (config.isEnabled()) {
      logger.info("Enabling Nitro DiscordBot...");
      new DiscordApiBuilder()
          .setToken(config.getToken())
          .setWaitForServersOnStartup(false)
          .setWaitForUsersOnStartup(false)
          .login()
          .thenAcceptAsync(
              api -> {
                setAPI(api);
                api.setMessageCacheSize(1, 60 * 60);
                api.addListener(new NitroRedeemer(this, getConfig()));
                api.addListener(new NitroAddAlert(this, getConfig()));
                api.addListener(new NitroRemoveAlert(this, getConfig()));

                logger.info("Discord Bot (Nitro) is now active!");
              });
    }
  }

  public Optional<Server> getServer() {
    return api.getServerById(config.getServer());
  }

  private void setAPI(DiscordApi api) {
    this.api = api;
  }

  public void disable() {
    if (this.api != null) {
      this.api.disconnect();
    }
    this.api = null;
  }

  public void alert(String message) {
    sendMessage(message, true);
  }

  public void sendMessage(String message, boolean alert) {
    if (api != null) {
      api.getServerById(config.getServer())
          .ifPresent(
              server -> {
                server
                    .getChannelById(alert ? config.getAlertChannel() : config.getMainChannel())
                    .ifPresent(
                        channel -> {
                          channel
                              .asTextChannel()
                              .ifPresent(
                                  text -> {
                                    text.sendMessage(message);
                                  });
                        });
              });
    }
  }

  public void reload() {
    if (this.api != null && !config.isEnabled()) {
      disable();
    } else if (this.api == null && config.isEnabled()) {
      enable();
    }
  }
}
