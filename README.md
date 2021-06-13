# Nitro
A discord -> minecraft bot which allows for Nitro users to claim an in-game rank

This plugin was created for use on https://oc.tc

## Config
```yml
# Discord Config stuff
enabled: true # Enable discord bot?

token: ""       # ID of Discord bot token
server: ""      # ID of discord server
nitro-role: ""  # ID of the nitro role

channel-alerts: ""   # ID of channel where logs from bot are sent
channel-main: ""     # ID of channel where command can be used

redemption-command: "lp user %s parent add nitro-boost"
remove-command: "lp user %s parent remove nitro-boost"

# List of nitro boosters
nitro-boosters:
  - ""
```
