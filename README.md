# Mercury Converter (Desktop Prototype)

> *"Consider this project the **'ancestor'** of my current Mercury Converter Android App. It’s where the core ideas and logic were first coded before migrating to mobile."*

### About
This is a Java Swing application designed to download and convert YouTube videos to MP3 format directly on your desktop. It acts as a GUI wrapper around the powerful `yt-dlp` tool.

This project represents a significant milestone in my learning journey, marking my transition from procedural coding to **Multi-threaded GUI programming**.

### Technical Highlights
* **GUI:** Built with Java Swing (AWT).
* **Process Management:** Uses `ProcessBuilder` to orchestrate external command-line tools (`yt-dlp`).
* **Multi-threading:** Implements background threads (`new Thread`) and `SwingUtilities.invokeLater` to keep the interface responsive during heavy download operations.
* **File I/O:** Automatically handles directory creation (`MercuryFile`) and file verification.

### Prerequisite (Crucial Step)
**This application requires an external executable to function.**
Since this is a lightweight wrapper, it relies on `yt-dlp.exe` for the actual downloading process.

1.  Download the latest `yt-dlp.exe` from the [official repository](https://github.com/yt-dlp/yt-dlp).
2.  Place the `.exe` file strictly in your **Downloads** folder:
    `C:\Users\YOUR_USERNAME\Downloads\yt-dlp.exe`
3.  Run the `Main.java` file.

### Usage
1.  Paste a valid YouTube video URL into the input field.
2.  Click **"Convert and Install"**.
3.  Watch the progress bar (handled by a background thread).
4.  Once "Completed", click the **"File"** button to instantly open the destination folder.

---
*Note: This repository is archived to demonstrate the desktop origins of the MercuryConverter project.*
