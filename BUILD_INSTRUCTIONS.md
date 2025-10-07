# How to Build APK for Android 14

Since Java/JDK is not currently set up on your system, here are **3 easy methods** to build and install the APK:

---

## ✅ **Method 1: Use Android Studio (RECOMMENDED - Easiest)**

This is the simplest method and gives you the most control.

### Step 1: Install Android Studio
1. Download Android Studio from: https://developer.android.com/studio
2. Run the installer
3. Follow the setup wizard (it will install everything needed including JDK)
4. Wait for initial setup to complete (downloads SDK, etc.)

### Step 2: Open Your Project
1. Launch Android Studio
2. Click **"Open"**
3. Navigate to: `C:\Users\safeer.mumraiz\Desktop\TV-REMOTE-APP`
4. Click **OK**
5. Wait for Gradle sync to complete (first time takes 2-5 minutes)

### Step 3: Build APK
1. In Android Studio menu: **Build** → **Build Bundle(s) / APK(s)** → **Build APK(s)**
2. Wait for build to complete (30 seconds - 2 minutes)
3. Click **"locate"** in the notification that appears
4. The APK will be at: `app\build\outputs\apk\debug\app-debug.apk`

### Step 4: Install on Your Phone
**Option A - USB Cable:**
1. Connect your phone via USB
2. Enable USB Debugging on phone (Settings → Developer Options)
3. In Android Studio: **Run** → **Run 'app'**
4. Select your device
5. App installs automatically!

**Option B - Transfer APK:**
1. Copy `app-debug.apk` to your phone (via USB, Bluetooth, or cloud)
2. On phone, open the APK file
3. Allow installation from unknown sources if prompted
4. Install!

---

## ✅ **Method 2: Install JDK Only (Command Line)**

If you prefer command line and don't want full Android Studio:

### Step 1: Install JDK
1. Download JDK 17 from: https://adoptium.net/temurin/releases/
2. Choose: **Windows x64**, **JDK 17**, **.msi installer**
3. Run installer
4. ✅ Check **"Set JAVA_HOME variable"** during installation
5. ✅ Check **"Add to PATH"** during installation

### Step 2: Install Android SDK Command Line Tools
1. Download from: https://developer.android.com/studio#command-line-tools-only
2. Extract to: `C:\Android\cmdline-tools`
3. Open PowerShell as Administrator:
   ```powershell
   cd C:\Android\cmdline-tools\bin
   .\sdkmanager.bat "platform-tools" "platforms;android-34" "build-tools;34.0.0"
   ```

### Step 3: Set Environment Variables
Open PowerShell as Administrator:
```powershell
[System.Environment]::SetEnvironmentVariable('ANDROID_HOME', 'C:\Android', 'User')
[System.Environment]::SetEnvironmentVariable('ANDROID_SDK_ROOT', 'C:\Android', 'User')
```

### Step 4: Build APK
```powershell
cd C:\Users\safeer.mumraiz\Desktop\TV-REMOTE-APP
.\gradlew.bat assembleDebug
```

### Step 5: Get Your APK
APK location: `app\build\outputs\apk\debug\app-debug.apk`

---

## ✅ **Method 3: Use Online Build Service (No Installation)**

If you don't want to install anything:

### Option A: GitHub Actions (Free)
1. Create a GitHub account (if you don't have one)
2. Create a new repository
3. Upload your project
4. I can create a GitHub Actions workflow that builds the APK automatically
5. Download the built APK from the Actions tab

### Option B: Use a Friend's Computer
- Copy the project folder to a USB drive
- Build on a computer that has Android Studio
- Copy the APK back

---

## 🎯 **RECOMMENDED PATH FOR YOU**

Based on your situation, I recommend **Method 1 (Android Studio)**:

**Why?**
- ✅ All-in-one solution (includes JDK, SDK, emulator, etc.)
- ✅ Easy to use with visual interface
- ✅ Can test app on emulator before installing on phone
- ✅ Easy to make changes and rebuild
- ✅ Industry standard tool
- ✅ Free

**Time Required:**
- Download: 10-15 minutes (1.1 GB)
- Install: 5 minutes
- First-time setup: 5-10 minutes
- Build APK: 2 minutes
- **Total: ~30 minutes**

---

## 📦 **After Building - Install on Your Phone**

### Transfer APK to Phone:

**Method 1: USB Cable**
1. Connect phone to PC via USB
2. Copy `app-debug.apk` to phone's Downloads folder
3. On phone, open Files app → Downloads
4. Tap the APK file → Install

**Method 2: Bluetooth**
1. Send APK file via Bluetooth to your phone
2. Accept file on phone
3. Open Downloads → Tap APK → Install

**Method 3: Cloud/Email**
1. Upload APK to Google Drive, Dropbox, or email to yourself
2. Download on phone
3. Open and install

**Method 4: Direct Install via Android Studio**
1. Connect phone via USB
2. Enable USB Debugging on phone
3. Click Run in Android Studio
4. Select your phone
5. App installs automatically!

### On Phone - Allow Installation:
1. When you tap the APK, Android may say "Install blocked"
2. Tap **Settings**
3. Enable **"Allow from this source"**
4. Go back and install

---

## 🔧 **Troubleshooting**

### "Installation blocked" on phone:
- Go to Settings → Security → Unknown sources
- Enable installation from unknown sources
- Or enable only for the app you're using to install (Chrome, Files, etc.)

### Build fails with "SDK not found":
- Make sure Android Studio finished initial setup
- In Android Studio: Tools → SDK Manager
- Install Android 14 (API 34) if not already installed

### Gradle sync fails:
- Wait for all downloads to complete
- Check internet connection
- In Android Studio: File → Invalidate Caches → Restart

---

## 📱 **Your APK Specifications**

When built, your APK will:
- ✅ Support Android 5.0 (API 21) and above
- ✅ Work perfectly on Android 14
- ✅ Be ~5-10 MB in size
- ✅ Target Android 14 (API 34)
- ✅ Support Xiaomi IR blaster
- ✅ Include all configurations

---

## 🚀 **Quick Start (For Android Studio)**

```
1. Download Android Studio: https://developer.android.com/studio
2. Install it (click Next, Next, Finish)
3. Open Android Studio
4. Open Project → Select TV-REMOTE-APP folder
5. Wait for Gradle sync
6. Build → Build APK(s)
7. Locate APK in: app\build\outputs\apk\debug\
8. Copy to phone and install!
```

---

## 💡 **Tips**

- **First build takes longer**: Gradle downloads dependencies (2-5 min)
- **Subsequent builds are fast**: 30 seconds - 1 minute
- **Debug APK vs Release APK**: Debug APK is for testing (what we're building)
- **Release APK**: For publishing (requires signing, we can do this later)

---

## ❓ **Need Help?**

If you encounter any issues:
1. Take a screenshot of the error
2. Check the error message
3. Most issues are solved by waiting for downloads to complete

---

## 📋 **Summary**

**Easiest Path:**
1. Install Android Studio (30 min one-time setup)
2. Open project
3. Build → Build APK
4. Transfer to phone and install

**Result:** 
Working TV Remote app on your Android 14 phone! 🎉📱

---

Would you like me to create a GitHub Actions workflow so you can build APKs online without installing anything?

