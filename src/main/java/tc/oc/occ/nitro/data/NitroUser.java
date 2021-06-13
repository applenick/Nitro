package tc.oc.occ.nitro.data;

import java.util.UUID;

/** NitroUser - A linked player who has nitro * */
public class NitroUser {

  private String discordId;
  private UUID playerId;

  public NitroUser(String discordId, UUID playerId) {
    this.discordId = discordId;
    this.playerId = playerId;
  }

  public String getDiscordId() {
    return discordId;
  }

  public UUID getPlayerId() {
    return playerId;
  }

  @Override
  public String toString() {
    return String.format("%s:%s", discordId, playerId.toString());
  }

  @Override
  public boolean equals(Object other) {
    return (other instanceof NitroUser)
        && ((NitroUser) other).getDiscordId().equalsIgnoreCase(getDiscordId());
  }

  public static NitroUser of(String data) {
    String[] parts = data.split(":");
    if (parts.length == 2) {
      String discord = parts[0];
      String uuidStr = parts[1];
      return new NitroUser(discord, UUID.fromString(uuidStr));
    }
    return null;
  }
}
