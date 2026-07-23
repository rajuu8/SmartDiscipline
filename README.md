# Smart Discipline - Self Improvement App 📱

**Master Your Daily Routine | Force Yourself to Follow It | Block Distracting Apps**

A powerful Android app designed to help you stick to your daily routine by forcefully blocking distracting apps during study time. Think of it as your personal digital discipline officer.

---

## 🎯 Features

✅ **Flexible Schedule Creation** - Create custom daily routines with any start/end time  
✅ **Local Storage** - All data stored locally on your phone (no cloud needed)  
✅ **App Blocking** - Automatically blocks social media, gaming, and entertainment apps  
✅ **Device Admin Integration** - Uses Android Device Admin for strict enforcement  
✅ **Time-based Activation** - Routines activate automatically during set times  
✅ **Easy Management** - View, edit, and delete routines anytime  

---

## 📋 What Gets Blocked

- **Social Media**: WhatsApp, Instagram, Facebook, TikTok
- **Gaming**: PUBG Mobile, Free Fire, Generic Games
- **Entertainment**: YouTube, Netflix

---

## 🚀 Getting Started

### Prerequisites
- Android device running API 24 or higher
- GitHub account (to build via GitHub Actions)

### Setup Steps

1. **Clone/Download This Project**
   ```bash
   git clone https://github.com/YOUR-USERNAME/SmartDiscipline.git
   cd SmartDiscipline
   ```

2. **Push to Your GitHub Repository**
   - Create a new GitHub repository
   - Push this code to it

3. **Build via GitHub Actions**
   - Go to **Actions** tab in your GitHub repo
   - Click "Build APK" workflow
   - Click **Run workflow**
   - Wait 5 minutes ⏳
   - Download APK from artifacts

4. **Install on Your Phone**
   - Transfer the APK file to your phone
   - Open file manager → Install APK
   - Or: `adb install app-debug.apk`

5. **First Launch Setup**
   - Click "Enable Device Admin"
   - Grant permission (required for app blocking)
   - Create your first routine

---

## 💡 How to Use

### Create a Routine

1. Tap **"+ Add New Routine"**
2. Enter routine name (e.g., "Morning Study")
3. Set start time and end time
4. Select apps to block
5. Tap **Save Routine**

### Activate a Routine

1. Tap **"View My Routines"**
2. Select a routine
3. Tap **"Start Now"**
4. App blocks start immediately ✅

### Deactivate

Simply tap **"View My Routines"** → Select routine → Click **"Stop"**

---

## 🔧 Project Structure

```
SmartDiscipline/
├── .github/workflows/
│   └── build.yml                 # GitHub Actions workflow
├── app/
│   ├── src/main/
│   │   ├── java/com/discipline/
│   │   │   ├── ui/              # UI Activities
│   │   │   ├── service/         # Background services & app blocking
│   │   │   └── data/            # Data models & storage
│   │   ├── res/
│   │   │   ├── layout/          # XML layouts
│   │   │   ├── values/          # Strings & colors
│   │   │   └── xml/             # Device admin config
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts
├── build.gradle.kts
├── settings.gradle.kts
├── gradlew                       # Gradle wrapper (Unix)
├── gradlew.bat                   # Gradle wrapper (Windows)
└── README.md
```

---

## 📱 System Requirements

- **Minimum SDK**: API 24 (Android 7.0)
- **Target SDK**: API 34 (Android 14)
- **Java**: JDK 17+

---

## ⚙️ Technologies Used

- **Language**: Kotlin
- **UI Framework**: Android AppCompat + Material Design 3
- **Storage**: SharedPreferences + JSON (Gson)
- **Build**: Gradle 8.0
- **CI/CD**: GitHub Actions

---

## 🔐 Permissions Explained

```xml
QUERY_ALL_PACKAGES      → List installed apps to block
PACKAGE_USAGE_STATS     → Monitor app usage
INTERNET                → Future cloud sync (optional)
ACCESS_NETWORK_STATE    → Check internet connection
RECEIVE_BOOT_COMPLETED  → Auto-restart blocking after reboot
```

---

## 🛠️ Customization

### Add More Apps to Block

Edit `AppBlockerService.kt`:

```kotlin
private val blockedAppPackages = listOf(
    "com.whatsapp",
    "com.your.app.package",  // Add here
)
```

### Change App Categories

Edit `ScheduleActivity.kt` and modify the `addAppCategory()` calls.

### Modify UI Colors

Edit `app/src/main/res/values/styles.xml`:

```xml
<color name="primary">#1976D2</color>
<color name="primaryDark">#1565C0</color>
```

---

## ⚠️ Important Notes

1. **Device Admin Required** - The app needs Device Admin permission to enforce blocking
2. **Cannot Bypass** - Once activated, you can't uninstall or disable the app during study time
3. **Local Only** - All data stays on your phone (privacy first!)
4. **Battery Usage** - Background service runs continuously (minimal impact)

---

## 🐛 Troubleshooting

| Issue | Solution |
|-------|----------|
| Apps not blocking | Enable Device Admin in settings |
| APK won't install | Check Android version (min 7.0 required) |
| Workflow fails | Ensure `gradlew` has execute permission |
| Service stops | Device restart will restart the service |

---

## 📝 License

MIT License - You're free to use, modify, and distribute this project.

---

## 🤝 Contributing

1. Fork this repo
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 💬 Support

- GitHub Issues: Report bugs and request features
- Documentation: Check this README for setup help

---

## 🎓 What You'll Learn

Building this app taught me:
- Android Device Admin APIs
- Kotlin coroutines and services
- Gradle multi-module builds
- GitHub Actions CI/CD
- Material Design principles

---

**Made with ❤️ for better discipline**

Happy coding! 🚀
