package tc.oc.occ.nitro.discord.listener;

import org.javacord.api.event.server.role.UserRoleRemoveEvent;
import org.javacord.api.listener.server.role.UserRoleRemoveListener;
import tc.oc.occ.nitro.NitroCloudy;
import tc.oc.occ.nitro.NitroConfig;
import tc.oc.occ.nitro.discord.DiscordBot;
import tc.oc.occ.nitro.events.NitroUserRemoveEvent;

public class NitroRemoveAlert extends NitroListener implements UserRoleRemoveListener {

  public NitroRemoveAlert(DiscordBot api, NitroConfig config) {
    super(api, config);
  }

  @Override
  public void onUserRoleRemove(UserRoleRemoveEvent event) {
    if (isNitro(event.getRole())) {
      config
          .getUser(event.getUser().getIdAsString())
          .ifPresent(
              nitro -> {
                NitroCloudy.get().callSyncEvent(new NitroUserRemoveEvent(nitro));
              });
    }
  }
}
