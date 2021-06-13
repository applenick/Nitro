package tc.oc.occ.nitro.events;

import tc.oc.occ.nitro.data.NitroUser;

/** NitroUserAddEvent - Called when a new NitroUser is created * */
public class NitroUserAddEvent extends NitroUserEvent {

  public NitroUserAddEvent(NitroUser user) {
    super(user);
  }
}
