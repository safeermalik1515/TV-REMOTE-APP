# TV Remote App for Android

A modern Android TV remote control app that uses the IR blaster on Xiaomi devices to control your television.

## Features

- **Power Control**: Turn TV on/off
- **Mute**: Toggle audio mute
- **Navigation**: Up, Down, Left, Right directional controls
- **OK Button**: Select/confirm actions
- **Volume Control**: Volume up and down
- **Channel Control**: Channel up and down
- **Source**: Change input source
- **Configuration Screen**: Customize IR codes for any TV brand
- **Multiple Remote Profiles**: Save and switch between different TV configurations
- **Quick Setup Presets**: Pre-configured codes for Samsung, LG, and Sony TVs
- **Test Mode**: Test each button individually during configuration
- **Persistent Storage**: Saves your configuration permanently

## Requirements

- Android device with IR blaster (Xiaomi devices supported)
- Android 5.0 (API 21) or higher
- TV that supports standard IR remote protocols (NEC protocol)

## Installation

1. Clone this repository
2. Open the project in Android Studio
3. Build and run on your Xiaomi device with IR blaster

## Device Compatibility

This app is specifically designed for Xiaomi devices that have an IR blaster, including:
- Xiaomi Mi series
- Redmi series (with IR support)
- POCO series (with IR support)

## How It Works

The app uses the Android `ConsumerIrManager` API to transmit IR signals through the device's IR blaster. It implements the NEC protocol, which is commonly used in TV remotes.

### NEC Protocol

The app uses the NEC infrared transmission protocol with:
- Carrier frequency: 38kHz (configurable)
- Standard command patterns for common TV operations
- Configurable address and command codes

## First Time Setup

On first launch, the app will automatically open the **Configuration Screen**:

1. **Enter Remote Name**: Give your remote a name (e.g., "Living Room TV")
2. **Choose Quick Setup**: Select Samsung, LG, or Sony preset
3. **Test Buttons**: Click TEST next to each button to verify it works
4. **Save Configuration**: Click "Save Remote Configuration"

### For Malaysia-Made Chinese Samsung TVs

If the Samsung preset doesn't work perfectly:

1. Load Samsung preset as starting point
2. Test each button individually
3. For non-working buttons, try alternative hex codes:
   - Power: Try `0x02`, `0x12`, or `0x98`
   - Volume: Try `0x0E/0x0D` or `0x16/0x17`
   - Channel: Try `0x48/0x49` or `0x20/0x21`
4. Save configuration once all buttons work

See [CONFIGURATION_GUIDE.md](CONFIGURATION_GUIDE.md) for detailed instructions.

## Managing Multiple Remotes

### Adding New Remotes

1. From main screen, tap menu (⋮) → "Configure Remote"
2. Set up the new TV configuration
3. Save with a unique name

### Switching Between Remotes

1. From main screen, tap menu (⋮) → "Switch Remote"
2. Select the remote you want to use
3. App reloads with selected configuration

## Customization

The app includes a built-in configuration screen where you can:
- Adjust IR frequency (usually 38000 Hz)
- Set device address in hex format
- Configure each button's command code
- Test individual buttons before saving
- Save multiple TV profiles

### Supported TV Brands

Pre-configured presets available:
- **Samsung**: Including Asian market variants
- **LG**: Standard LG TVs
- **Sony**: Sony Bravia and others
- **Custom**: Manual configuration for any TV brand

## Project Structure

```
app/
├── src/main/
│   ├── java/com/tvremote/app/
│   │   ├── MainActivity.kt           # Main remote control UI
│   │   ├── ConfigurationActivity.kt  # Configuration screen
│   │   ├── IRController.kt           # IR transmission controller
│   │   ├── RemoteConfig.kt           # Data model and storage manager
│   │   └── RemoteConfigManager.kt    # SharedPreferences manager
│   ├── res/
│   │   ├── layout/
│   │   │   ├── activity_main.xml         # Remote control UI
│   │   │   ├── activity_configuration.xml # Configuration screen UI
│   │   │   └── config_item.xml           # Reusable config item
│   │   ├── menu/
│   │   │   └── main_menu.xml         # Options menu
│   │   ├── values/
│   │   │   ├── colors.xml            # Color definitions
│   │   │   ├── strings.xml           # String resources
│   │   │   └── themes.xml            # App theme
│   │   └── mipmap/                   # App icons
│   └── AndroidManifest.xml           # App manifest
├── build.gradle                       # App-level build config
├── README.md                          # Main documentation
├── CONFIGURATION_GUIDE.md             # Detailed setup guide
└── proguard-rules.pro                # ProGuard rules
```

## UI Design

The app features a modern, dark-themed UI with:
- Color-coded buttons for different functions
- Intuitive layout mimicking physical TV remote
- Haptic feedback for button presses
- Status messages for user feedback

## Permissions

The app requires the following permissions:
- `TRANSMIT_IR`: To send IR signals
- `VIBRATE`: For haptic feedback

## Building

To build the APK:

```bash
./gradlew assembleDebug
```

To build release version:

```bash
./gradlew assembleRelease
```

## Troubleshooting

### IR Not Working

1. **Check device compatibility**: Ensure your device has an IR blaster
2. **Point at TV**: Make sure the IR blaster is pointed at your TV
3. **Distance**: Keep device within 5 meters of TV
4. **Codes**: Try adjusting command codes for your specific TV brand

### App Not Installing

- Ensure Android SDK is properly configured
- Check minimum SDK version compatibility
- Verify USB debugging is enabled

## Screenshots and Usage

### Configuration Screen
- On first launch, automatically opens configuration
- Quick setup presets for Samsung, LG, Sony
- Manual hex code entry for each button
- Individual button testing before saving
- Save multiple remote profiles

### Main Remote Screen
- Color-coded buttons for easy identification
- Haptic feedback on button press
- Real-time status messages
- Remote name displayed in title bar
- Menu access for configuration and switching remotes

## Future Enhancements

Potential features to add:
- Learning mode to capture IR codes from existing remote
- Macro support for multiple commands
- Widget support for quick access
- Multiple device support (AC, Set-top box, etc.)
- Import/Export configurations
- Cloud backup of remote profiles

## License

This project is open source and available for personal and educational use.

## Disclaimer

This app is designed to work with devices that have IR blaster hardware. It will not function on devices without this feature. The effectiveness depends on your TV's compatibility with the NEC protocol.

