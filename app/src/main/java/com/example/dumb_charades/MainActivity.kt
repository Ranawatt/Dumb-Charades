package com.example.dumb_charades

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dumb_charades.directshare.SharingShortcutsManager

class MainActivity : AppCompatActivity() {

    // Domain authority for our app FileProvider
    private val fileProviderAuthority = "com.example.dumb_charades.directshare.fileprovider"

    // Cache directory to store images
    // This is the same path specified in the @xml/file_paths and accessed from the AndroidManifest
    private val imageCacheDir = "images"

    // Name of the file to use for the thumbnail image
    private val imageFile = "image.png"

    private lateinit var sharingShortcutsManager: SharingShortcutsManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharingShortcutsManager = SharingShortcutsManager().also {
            it.pushDirectShareTargets(this)
        }
    }
}
