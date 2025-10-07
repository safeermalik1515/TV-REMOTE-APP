# TV Remote App Flow Diagram

## Application Flow

```
┌─────────────────────────────────────────────────────────────┐
│                      APP LAUNCH                              │
└────────────────────┬────────────────────────────────────────┘
                     │
                     ▼
          ┌──────────────────────┐
          │ Check Configuration? │
          │  hasConfiguration()  │
          └──────────┬───────────┘
                     │
         ┌───────────┴───────────┐
         │                       │
      No │                       │ Yes
         │                       │
         ▼                       ▼
┌─────────────────┐    ┌──────────────────┐
│ Configuration   │    │   MainActivity   │
│   Activity      │    │  (Remote Screen) │
└────────┬────────┘    └────────┬─────────┘
         │                      │
         │                      ├─────┐
         │                      │     │
         │                      │     ▼
         │                      │  ┌──────────────┐
         │                      │  │ Menu Options │
         │                      │  └──────┬───────┘
         │                      │         │
         │                      │  ┌──────┴──────────┐
         │                      │  │                 │
         │                      │  ▼                 ▼
         │                      │  Configure      Switch
         │                      │  Remote         Remote
         │                      │  │               │
         │  ┌───────────────────┘  │               │
         │  │                      │               │
         │  │  ┌───────────────────┘               │
         │  │  │                                   │
         ▼  ▼  ▼                                   │
    ┌────────────────┐                            │
    │ Configuration  │                            │
    │   Activity     │                            │
    │                │                            │
    │ ┌────────────┐ │                            │
    │ │Quick Setup │ │                            │
    │ │  Presets   │ │                            │
    │ └────────────┘ │                            │
    │                │                            │
    │ ┌────────────┐ │                            │
    │ │   Manual   │ │                            │
    │ │   Config   │ │                            │
    │ └────────────┘ │                            │
    │                │                            │
    │ ┌────────────┐ │                            │
    │ │    Test    │ │                            │
    │ │  Buttons   │ │                            │
    │ └────────────┘ │                            │
    │                │                            │
    │ ┌────────────┐ │                            │
    │ │    Save    │ │                            │
    │ └──────┬─────┘ │                            │
    └────────┼───────┘                            │
             │                                    │
             ▼                                    │
    ┌──────────────────┐                         │
    │  Save to Shared  │◄────────────────────────┘
    │   Preferences    │
    └────────┬─────────┘
             │
             ▼
    ┌──────────────────┐
    │   MainActivity   │
    │  (Remote Ready)  │
    └──────────────────┘
```

---

## First Launch Experience

```
User Opens App
      │
      ▼
┌─────────────────┐
│ No Config Found │
└────────┬────────┘
         │
         ▼
┌─────────────────────────┐
│ Configuration Activity  │
│                         │
│ "Welcome! Let's setup   │
│  your TV remote"        │
└────────┬────────────────┘
         │
         ▼
┌─────────────────────────┐
│ Enter Remote Name       │
│ "My Samsung TV"         │
└────────┬────────────────┘
         │
         ▼
┌─────────────────────────┐
│ Choose Quick Setup      │
│ [Samsung] [LG] [Sony]   │
└────────┬────────────────┘
         │
         ▼
┌─────────────────────────┐
│ Test Each Button        │
│ Power  [0x02] [TEST] ✓  │
│ Mute   [0x07] [TEST] ✓  │
│ Vol+   [0x07] [TEST] ✓  │
│ ...                     │
└────────┬────────────────┘
         │
         ▼
┌─────────────────────────┐
│ Save Configuration      │
│ [Save Remote Config]    │
└────────┬────────────────┘
         │
         ▼
┌─────────────────────────┐
│ Configuration Saved!    │
│ Redirect to Remote...   │
└────────┬────────────────┘
         │
         ▼
┌─────────────────────────┐
│ Main Remote Screen      │
│                         │
│ Ready to control TV!    │
└─────────────────────────┘
```

---

## Daily Usage Flow

```
User Opens App
      │
      ▼
┌─────────────────┐
│  Config Exists  │
└────────┬────────┘
         │
         ▼
┌─────────────────────────┐
│ Load Configuration      │
│ "Living Room Samsung"   │
└────────┬────────────────┘
         │
         ▼
┌─────────────────────────┐
│ Main Remote Screen      │
│                         │
│  [Power]  [Mute]        │
│                         │
│     [Up]                │
│ [Left][OK][Right]       │
│    [Down]               │
│                         │
│ [Vol+]      [CH+]       │
│ [Vol-]      [CH-]       │
│                         │
│    [Source]             │
└────────┬────────────────┘
         │
      User Presses Button
         │
         ▼
┌─────────────────────────┐
│ Get Command from Config │
│ address: 0x07           │
│ command: 0x02 (Power)   │
│ frequency: 38000        │
└────────┬────────────────┘
         │
         ▼
┌─────────────────────────┐
│ IR Controller           │
│ Generate NEC Pattern    │
└────────┬────────────────┘
         │
         ▼
┌─────────────────────────┐
│ Transmit IR Signal      │
│ via IR Blaster          │
└────────┬────────────────┘
         │
         ▼
┌─────────────────────────┐
│ Provide Feedback        │
│ - Haptic vibration      │
│ - Status message        │
│ - Visual confirmation   │
└─────────────────────────┘
```

---

## Configuration Testing Flow

```
Configuration Screen
      │
      ▼
┌─────────────────────────┐
│ Power [0x02] [TEST]     │
└────────┬────────────────┘
         │
    User clicks TEST
         │
         ▼
┌─────────────────────────┐
│ Parse Hex Code          │
│ "0x02" → int 2          │
└────────┬────────────────┘
         │
         ▼
┌─────────────────────────┐
│ Get Address & Frequency │
│ Address: 0x07           │
│ Frequency: 38000        │
└────────┬────────────────┘
         │
         ▼
┌─────────────────────────┐
│ Send Test Command       │
│ sendCustomCommand(...)  │
└────────┬────────────────┘
         │
      ┌──┴──┐
      │     │
  Success   Fail
      │     │
      ▼     ▼
┌──────┐ ┌─────────┐
│ ✓    │ │ ✗       │
│ Toast│ │ Toast   │
│"Sent"│ │"Failed" │
└──────┘ └─────────┘
      │
      ▼
Continue testing
other buttons
```

---

## Multiple Remote Management

```
Main Screen
      │
      ▼
┌─────────────────────────┐
│ Menu (⋮)                │
└────────┬────────────────┘
         │
    ┌────┴────┐
    │         │
    ▼         ▼
Configure  Switch
Remote     Remote
    │         │
    │         ▼
    │    ┌──────────────────┐
    │    │ Select Remote:   │
    │    │ • Living Room TV │
    │    │ • Bedroom TV     │
    │    │ • Office TV      │
    │    └────────┬─────────┘
    │             │
    │         User Selects
    │             │
    │             ▼
    │    ┌──────────────────┐
    │    │ Load Selected    │
    │    │ Configuration    │
    │    └────────┬─────────┘
    │             │
    │             ▼
    │    ┌──────────────────┐
    │    │ Reload Main      │
    │    │ Activity         │
    │    └──────────────────┘
    │
    ▼
┌─────────────────────────┐
│ Configuration Screen    │
│                         │
│ Create New or           │
│ Edit Current            │
└─────────────────────────┘
```

---

## Data Persistence

```
┌─────────────────────────┐
│ RemoteConfig Object     │
│                         │
│ id: "123456789"         │
│ name: "Living Room"     │
│ address: 0x07           │
│ powerCode: 0x02         │
│ ...                     │
└────────┬────────────────┘
         │
         ▼
┌─────────────────────────┐
│ RemoteConfigManager     │
│ saveCurrentConfig()     │
└────────┬────────────────┘
         │
         ▼
┌─────────────────────────┐
│ Gson Serialization      │
│ Object → JSON String    │
└────────┬────────────────┘
         │
         ▼
┌─────────────────────────┐
│ SharedPreferences       │
│                         │
│ Key: "current_config"   │
│ Value: "{...json...}"   │
└────────┬────────────────┘
         │
    Persistent
    Storage
         │
         ▼
┌─────────────────────────┐
│ App Restart             │
└────────┬────────────────┘
         │
         ▼
┌─────────────────────────┐
│ Load from Storage       │
│ getCurrentConfig()      │
└────────┬────────────────┘
         │
         ▼
┌─────────────────────────┐
│ Gson Deserialization    │
│ JSON → RemoteConfig     │
└────────┬────────────────┘
         │
         ▼
┌─────────────────────────┐
│ Configuration Restored  │
│ Remote ready to use!    │
└─────────────────────────┘
```

---

## Error Handling Flow

```
Button Press
      │
      ▼
┌─────────────────┐
│ Check IR        │
│ Available?      │
└────┬────────────┘
     │
  ┌──┴──┐
  No    Yes
  │     │
  ▼     ▼
Error   Continue
  │     │
  │     ▼
  │  ┌─────────────────┐
  │  │ Get Config      │
  │  └────┬────────────┘
  │       │
  │       ▼
  │  ┌─────────────────┐
  │  │ Valid Config?   │
  │  └────┬────────────┘
  │       │
  │    ┌──┴──┐
  │    No    Yes
  │    │     │
  │    ▼     ▼
  │  Error  Send IR
  │    │     │
  │    │     ▼
  │    │  ┌─────────────┐
  │    │  │ Success?    │
  │    │  └────┬────────┘
  │    │       │
  │    │    ┌──┴──┐
  │    │    No    Yes
  │    │    │     │
  │    │    ▼     ▼
  │    │  Error Success
  │    │    │     │
  └────┴────┴─────┘
       │
       ▼
┌──────────────────┐
│ Show Feedback    │
│ • Toast message  │
│ • Status text    │
│ • Vibration      │
└──────────────────┘
```

---

## Component Interaction

```
┌─────────────────────────────────────────────────────────┐
│                    MainActivity                          │
│                                                          │
│  • UI Management                                         │
│  • Button Click Handlers                                │
│  • Menu Actions                                          │
└────────┬───────────────────────┬────────────────────────┘
         │                       │
         ▼                       ▼
┌──────────────────┐    ┌──────────────────┐
│ RemoteConfig     │    │ IRController     │
│ Manager          │    │                  │
│                  │    │ • Send Commands  │
│ • Load Config    │    │ • NEC Protocol   │
│ • Save Config    │    │ • IR Transmit    │
│ • Manage List    │    └──────────────────┘
└────────┬─────────┘
         │
         ▼
┌──────────────────┐
│ SharedPreferences│
│                  │
│ • JSON Storage   │
│ • Persistent     │
└──────────────────┘


┌─────────────────────────────────────────────────────────┐
│              ConfigurationActivity                       │
│                                                          │
│  • Configuration UI                                      │
│  • Preset Loading                                        │
│  • Individual Testing                                    │
│  • Save Validation                                       │
└────────┬───────────────────────┬────────────────────────┘
         │                       │
         ▼                       ▼
┌──────────────────┐    ┌──────────────────┐
│ RemoteConfig     │    │ IRController     │
│ Manager          │    │                  │
│                  │    │ • Test Commands  │
│ • Save New       │    │ • Custom Send    │
│ • Get Presets    │    └──────────────────┘
└──────────────────┘
```

---

## Summary

The app follows a clean architecture with:
- **Configuration first**: Ensures setup before use
- **Persistent storage**: Never lose your settings
- **Flexible testing**: Test before committing
- **Multi-profile**: Switch between TVs easily
- **Error handling**: Graceful failures with feedback
- **User-friendly**: Guided setup with presets

All flows are optimized for the best user experience! 🎉

