package tc.oc.occ.nitro.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import tc.oc.occ.nitro.data.NitroUser;

public class NitroUserEvent extends Event {

  private final NitroUser user;

  public NitroUserEvent(NitroUser user) {
    this.user = user;
  }

  public NitroUser getUser() {
    return user;
  }

  private static final HandlerList handlers = new HandlerList();

  @Override
  public HandlerList getHandlers() {
    return handlers;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }
}
