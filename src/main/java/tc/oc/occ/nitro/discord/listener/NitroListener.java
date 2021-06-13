package tc.oc.occ.nitro.discord.listener;

import java.util.List;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.user.User;
import tc.oc.occ.nitro.NitroConfig;
import tc.oc.occ.nitro.discord.DiscordBot;

public abstract class NitroListener {

  protected final NitroConfig config;
  protected final DiscordBot api;

  public NitroListener(DiscordBot api, NitroConfig config) {
    this.config = config;
    this.api = api;
  }

  protected boolean isNitro(User user) {
    if (api.getServer().isPresent()) {
      List<Role> roles = user.getRoles(api.getServer().get());
      return roles.stream().anyMatch(this::isNitro);
    }
    return false;
  }

  protected boolean isNitro(Role role) {
    return role.getIdAsString().equalsIgnoreCase(config.getNitroRole());
  }
}
