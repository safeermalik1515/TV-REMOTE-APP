# TV Remote Configuration Guide

This guide will help you configure your TV remote app for different TV brands, including Malaysia-made Chinese Samsung TVs.

## Quick Start

1. **First Launch**: When you first open the app, you'll be taken to the configuration screen automatically.

2. **Quick Setup Presets**: Use the preset buttons for common brands:
   - **Samsung**: For most Samsung TVs
   - **LG**: For LG TVs
   - **Sony**: For Sony TVs

3. **Test Buttons**: After loading a preset, test each button to see if it works with your TV.

4. **Save Configuration**: Once you find working codes, click "Save Remote Configuration" to save it.

## For Malaysia-Made Chinese Samsung TVs

Chinese Samsung TVs manufactured in Malaysia may use different IR codes than standard Samsung TVs. Here's how to configure:

### Method 1: Try Samsung Preset First
1. Click "Samsung" preset button
2. Test each button one by one
3. If some buttons work but others don't, proceed to Method 2

### Method 2: Manual Configuration

#### Understanding IR Codes

IR codes consist of:
- **Frequency**: Usually 38000 Hz (38kHz) for most TVs
- **Address**: A hex value identifying the device type (e.g., 0x07 for Samsung)
- **Command Codes**: Hex values for each button (e.g., 0x02 for Power)

#### Finding Your TV's Codes

**Option 1: Common Chinese Samsung Codes**
Try these alternative Samsung codes commonly used in Asian markets:

```
Frequency: 38000
Address: 0x07 or 0x00

Power: 0x02, 0x12, or 0x98
Mute: 0x07 or 0x0F
Volume Up: 0x07 or 0x0E
Volume Down: 0x0B or 0x0D
Channel Up: 0x12 or 0x48
Channel Down: 0x10 or 0x49
Up: 0x60 or 0x06
Down: 0x61 or 0x08
Left: 0x65 or 0x0A
Right: 0x62 or 0x0B
OK: 0x68 or 0x1C
Source: 0x01 or 0x0B
```

**Option 2: Trial and Error**
1. Start with Samsung preset
2. For each non-working button, try these common alternative codes:
   - Power: Try 0x02, 0x12, 0x15, 0x98
   - Volume: Try 0x07/0x0B, 0x0E/0x0D, 0x16/0x17
   - Channel: Try 0x12/0x10, 0x20/0x21, 0x48/0x49

**Option 3: Use IR Code Databases**
Search online for:
- "Samsung IR codes Malaysia"
- "Chinese Samsung TV IR codes"
- Your specific TV model number + "IR codes"

#### Testing Each Button

1. Enter a hex code (e.g., `0x02`) in the field
2. Click the **TEST** button next to it
3. Point your phone's IR blaster at the TV
4. If it works, move to the next button
5. If not, try alternative codes

#### Common Code Patterns

Most TVs follow patterns:
- Navigation buttons often increment (0x60, 0x61, 0x62...)
- Volume/Channel up/down are usually consecutive pairs
- Address is usually 0x00 or brand-specific (Samsung: 0x07)

## Multiple TV Profiles

### Saving Multiple Remotes

1. Configure and save your first TV remote
2. From the main screen, tap the menu icon (three dots)
3. Select "Configure Remote" to create a new configuration
4. Save with a different name (e.g., "Living Room TV", "Bedroom TV")

### Switching Between Remotes

1. From the main screen, tap the menu icon
2. Select "Switch Remote"
3. Choose the remote you want to use
4. The app will reload with the selected configuration

## Troubleshooting

### Buttons Not Working

1. **Check IR Blaster**: Ensure your Xiaomi device has IR blaster (usually at top edge)
2. **Distance**: Stay within 5 meters of TV
3. **Aim**: Point IR blaster directly at TV's IR receiver
4. **Try Alternative Codes**: Different markets may use different codes
5. **Check Frequency**: Some TVs use 40kHz instead of 38kHz

### Some Buttons Work, Others Don't

- This is common with Chinese market TVs
- Manually test and configure each non-working button
- Try incrementing/decrementing hex values (e.g., if 0x60 works, try 0x61, 0x62...)

### Finding Specific Codes

**Online Resources:**
1. LIRC Database: http://lirc.sourceforge.net/remotes/
2. Search: "[Your TV Model] NEC IR codes"
3. Search: "Samsung Malaysia IR codes"

**NEC Protocol Format:**
- Most TVs use NEC protocol
- Codes are in hex format (0x00 to 0xFF)
- Start burst: 9ms + 4.5ms (handled automatically)

## Advanced: Raw Pattern Entry

For very specific TVs that don't follow NEC protocol:
1. Find the raw IR pattern online
2. Use the `sendRawPattern()` function in code
3. Contact developer for assistance

## Tips for Success

1. **Start with Presets**: Always try preset buttons first
2. **Test Systematically**: Test each button after entering code
3. **Document Working Codes**: Note which codes work
4. **Save Often**: Save configuration after finding working codes
5. **Multiple Attempts**: Some codes may need 2-3 attempts to work
6. **Battery**: Ensure phone has good battery for testing

## Example: Configuring Step by Step

### For Chinese Samsung TV:

1. Open app → Configuration screen appears
2. Enter Remote Name: "Samsung TV Living Room"
3. Click "Samsung" preset
4. Test Power button:
   - Click TEST next to Power field
   - If doesn't work, try 0x12, then 0x98
5. Test each button similarly
6. Once all buttons work, click "Save Remote Configuration"
7. App opens to main remote screen
8. Start controlling your TV!

## Support

If you can't find working codes:
1. Check your TV's manual for IR code information
2. Search for your TV model + "IR codes" online
3. Try codes from similar TV models
4. For Chinese Samsung TVs, try codes labeled "Samsung Asia" or "Samsung CN"

## Common Chinese Market TV Codes

### Alternative Samsung (Malaysia/China):
- Address: 0x00 or 0x07
- Power: 0x98 (common in Chinese models)
- Volume +/-: 0x0E / 0x0D
- Channel +/-: 0x48 / 0x49

### Generic Chinese TV:
- Address: 0x00
- Power: 0x12
- Standard NEC codes usually work

---

**Remember**: The app saves your configuration permanently. Once configured correctly, you won't need to reconfigure unless you want to add a new TV remote.

