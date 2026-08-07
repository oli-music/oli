<div align="center">
  <h1>Oli</h1>

  <p><strong>A modern Android music app with ad-free streaming, synced lyrics, offline playback, and an intuitive user experience.</strong></p>

  [![License](https://img.shields.io/github/license/oli-music/oli?style=for-the-badge&color=28a745)](LICENSE)

</div>

---

## Overview

Oli delivers a seamless, ad-free listening experience with offline downloads, real-time synchronized lyrics, and a clean, modern interface.

Oli is a rebrand of [Echo Music](https://github.com/EchoMusicApp/Echo-Music) by Aditya Yadav (iad1tya), itself built on the [Metrolist](https://github.com/MetrolistGroup/Metrolist) architecture. It is distributed under the same GPL-3.0 license as the upstream project — see [Credits](#credits) below.

---

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Installation & Setup](#installation--setup)
  - [Building from Source](#building-from-source)
- [Translations](#translations)
- [Credits](#credits)
- [License](#license)

---

## Features

### Streaming & Playback
- **Ad-Free** — Stream without any interruptions.
- **Lossless Audio** — Support for 16-bit and 24-bit high fidelity FLAC audio.
- **Data Saver Mode** — Reduce data consumption when streaming on cellular networks.
- **Seamless Playback** — Switch effortlessly between audio-only and video modes.
- **Background Playback** — Listen while using other apps or with the screen off.
- **Offline Mode** — Download tracks, albums, and playlists via a dedicated download manager.
- **Crossfade** — Smooth transitions between tracks.
- **Canvas Animations** — Visual animations while playing music.

### Discovery
- **Song Recognition** — Identify songs playing around you using on-device audio recognition.
- **Smart Recommendations** — Personalized suggestions based on your listening history.
- **Comprehensive Browsing** — Explore Charts, Podcasts, Moods, and Genres.

### Lyrics
- **Multiple Lyric Animations** — Choose from various lyric display styles.
- **Word-by-Word Lyrics** — Precise per-word synchronization.
- **AI Translation** — Built-in translation for lyrics in any language.

### Integrations
- **Music Sharing via Odesli** — Share songs as Song.link for cross-platform listening.
- **Set as Ringtone** — Directly set any song as your device ringtone.

### Smart Playback
- **Pause on Mute** — Auto-pause when your device is muted.
- **Resume on Bluetooth** — Playback resumes when headphones or earbuds reconnect.

### Customization
- **UI Density Scale** — Adjust interface spacing to your preference.
- **High Refresh Rate Support** — Smoother UI and animations on supported displays.
- **Hide Player Thumbnail** — Keep the player minimal without album art.
- **Crop Album Art** — Adjust album art display to fit your style.
- **Hide Video Songs / Shorts** — Filter out video content from your feed.

---

## Installation & Setup

### Building from Source

1. **Clone the repository**
   ```bash
   git clone https://github.com/oli-music/oli.git
   cd oli
   ```

2. **Configure Android SDK**
   Create a `local.properties` file:
   ```bash
   echo "sdk.dir=/path/to/your/android/sdk" > local.properties
   ```

3. **Firebase configuration (optional)**
   Firebase is required for analytics and crash reporting. Add your own `google-services.json` if you want this enabled.

4. **Build the application**
   Oli has two build variants: **FOSS** (without Google Play Services / Cast) and **GMS** (with Cast support).

   ```bash
   ./gradlew assembleUniversalFossDebug   # FOSS variant
   ./gradlew assembleUniversalGmsDebug    # GMS variant
   ```

5. **Configure listen-together (optional)**
   `app/server.json` ships with an empty `serverUrl`. Point it at your own deployment of a compatible server (see [Echo-Music-Server](https://github.com/EchoMusicApp/Echo-Music-Server)) if you want real-time listening sessions.

---

## Translations

This fork carries over the translations from Echo Music at the time it was forked. Set up your own localization pipeline (e.g. Weblate) if you plan to maintain translations going forward.

---

## Credits

Oli is a rebranded fork of **Echo Music**, created by **Aditya Yadav ([@iad1tya](https://github.com/iad1tya))**, released under the GPL-3.0 license. All credit for the original architecture, feature set, and the vast majority of the codebase belongs to that project and its contributors.

Echo Music itself is built on the shoulders of:

| Project | Description |
| :--- | :--- |
| [Metrolist](https://github.com/MetrolistGroup/Metrolist) & [Vivi Music](https://github.com/vivizzz007/vivi-music) | Foundational architecture reference |
| [ArchiveTune](https://github.com/koiverse/ArchiveTune) | Material You UI inspiration |
| [Better Lyrics](https://better-lyrics.boidu.dev/) | Lyrics enhancement and synchronization |
| [SimpMusic](https://github.com/maxrave-dev/SimpMusic) | Lyrics implementation reference |
| [Music Recognizer](https://github.com/aleksey-saenko/MusicRecognizer) | Audio recognition |
| [zemer-cipher](https://github.com/ZemerTeam/zemer-cipher) | YouTube cipher deobfuscation and PoToken generation |

If you fork this project further, per GPL-3.0 you must keep this credits section (or an equivalent one) intact and keep your fork's source open.

---

## License

Licensed under [GPL-3.0](LICENSE). Source must remain available for any distributed build, including modified forks.
