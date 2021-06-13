package tc.oc.occ.nitro;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.bukkit.configuration.Configuration;
import tc.oc.occ.nitro.data.NitroUser;

public class NitroConfig {

  private boolean enabled;

  private String token;
  private String server;
  private String nitroRole;

  private List<NitroUser> nitroUsers;

  private String alertChannel;
  private String mainChannel;

  private String redemptionCommand;
  private String removalCommand;

  public NitroConfig(Configuration config) {
    reload(config);
  }

  public void reload(Configuration config) {
    this.enabled = config.getBoolean("enabled");
    this.token = config.getString("token");
    this.server = config.getString("server");
    this.nitroRole = config.getString("nitro-role");
    this.alertChannel = config.getString("channel-alerts");
    this.mainChannel = config.getString("channel-main");
    this.redemptionCommand = config.getString("redemption-command");
    this.removalCommand = config.getString("remove-command");

    List<String> nitroData = config.getStringList("nitro-boosters");
    this.nitroUsers =
        nitroData.stream()
            .filter(str -> str != null && !str.isEmpty())
            .map(NitroUser::of)
            .collect(Collectors.toList());
  }

  public boolean isEnabled() {
    return enabled;
  }

  public String getToken() {
    return token;
  }

  public String getServer() {
    return server;
  }

  public String getNitroRole() {
    return nitroRole;
  }

  public String getAlertChannel() {
    return alertChannel;
  }

  public String getMainChannel() {
    return mainChannel;
  }

  public List<NitroUser> getUsers() {
    return nitroUsers;
  }

  public String getRedemptionCommand(String playerId) {
    return String.format(redemptionCommand, playerId);
  }

  public String getRemovalCommand(String playerId) {
    return String.format(removalCommand, playerId);
  }

  public NitroUser addNitro(String discordId, UUID playerId) {
    NitroUser user = new NitroUser(discordId, playerId);
    this.nitroUsers.add(user);
    return user;
  }

  public Optional<NitroUser> getUser(String discordId) {
    return nitroUsers.stream()
        .filter(user -> user.getDiscordId().equalsIgnoreCase(discordId))
        .findAny();
  }

  public void removeNitro(NitroUser user) {
    nitroUsers.remove(user);
  }

  public void save(Configuration config) {
    config.set(
        "nitro-boosters",
        nitroUsers.stream().map(NitroUser::toString).collect(Collectors.toList()));
  }
}
