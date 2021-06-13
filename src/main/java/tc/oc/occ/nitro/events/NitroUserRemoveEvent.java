package tc.oc.occ.nitro.events;

import tc.oc.occ.nitro.data.NitroUser;

/** NitroUserRemoveEvent - Called when an existing nitro user is removed * */
public class NitroUserRemoveEvent extends NitroUserEvent {

  public NitroUserRemoveEvent(NitroUser user) {
    super(user);
  }
}
