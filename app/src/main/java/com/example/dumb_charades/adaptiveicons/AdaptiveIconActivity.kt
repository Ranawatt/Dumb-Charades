package com.example.dumb_charades.adaptiveicons

import android.graphics.drawable.AdaptiveIconDrawable
import android.graphics.drawable.ClipDrawable.HORIZONTAL
import android.graphics.drawable.ClipDrawable.VERTICAL
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.View.GONE
import android.widget.ImageView
import android.widget.SeekBar
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.ChangeBounds
import androidx.transition.Fade
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.example.dumb_charades.R
import com.google.android.material.bottomsheet.BottomSheetBehavior

class AdaptiveIconActivity : AppCompatActivity() {

    private val grid by lazy(LazyThreadSafetyMode.NONE) {
        findViewById<RecyclerView>(R.id.grid)
    }
    private val damping by lazy(LazyThreadSafetyMode.NONE) {
        findViewById<SeekBar>(R.id.damping)
    }
    private val stiffness by lazy(LazyThreadSafetyMode.NONE) {
        findViewById<SeekBar>(R.id.stiffness)
    }
    private val velocityTracker = VelocityTracker.obtain()
    private val corners: FloatArray by lazy(LazyThreadSafetyMode.NONE) {
        val density = resources.displayMetrics.density
        floatArrayOf(density * 36f, density * 30f, density * 16f, density * 4f)
    }
    private val gridTouch = View.OnTouchListener { _, motionEvent ->
        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                velocityTracker += motionEvent
                when (orientation) {
                    HORIZONTAL -> velocityX = velocityTracker.xVelocity
                    VERTICAL -> velocityY = velocityTracker.yVelocity
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                velocityTracker += motionEvent
                releaseVelocity(velocityTracker.xVelocity, velocityTracker.yVelocity)
                velocityTracker.clear()
            }
        }
        false
    }
    private var corner = 0
    private var decor = Decor.Wallpaper
    private var orientation = HORIZONTAL
    private var adapter: IconAdapter? = null

    private var springStiffness = 500f
        get() = Math.max(stiffness.progress.toFloat(), 50f)

    private var springDamping = 0.3f
        get() = Math.max(damping.progress / 100f, 0.05f)

    private var iconCornerRadius
        get() = adapter?.iconCornerRadius ?: 0f
        set(value) {
            applyGridProperty(
                { ad -> ad.iconCornerRadius = value },
                { iv -> iv.cornerRadius = value })
        }

    private var velocityX = 0f
        set(value) {
            applyGridProperty(
                { ad -> ad.velocityX = value },
                { iv -> iv.velocityX = value })
        }

    private var velocityY = 0f
        set(value) {
            applyGridProperty(
                { ad -> ad.velocityY = value },
                { iv -> iv.velocityY = value })
        }

    private var foregroundTranslateFactor
        get() = adapter?.foregroundTranslateFactor ?: DEF_FOREGROUND_TRANSLATE_FACTOR
        set(value) {
            applyGridProperty(
                { ad -> ad.foregroundTranslateFactor = value },
                { iv -> iv.foregroundTranslateFactor = value })
        }

    private var backgroundTranslateFactor
        get() = adapter?.backgroundTranslateFactor ?: DEF_BACKGROUND_TRANSLATE_FACTOR
        set(value) {
            applyGridProperty(
                { ad -> ad.backgroundTranslateFactor = value },
                { iv -> iv.backgroundTranslateFactor = value })
        }

    private var foregroundScaleFactor
        get() = adapter?.foregroundScaleFactor ?: DEF_FOREGROUND_SCALE_FACTOR
        set(value) {
            applyGridProperty(
                { ad -> ad.foregroundScaleFactor = value },
                { iv -> iv.foregroundScaleFactor = value })
        }

    private var backgroundScaleFactor
        get() = adapter?.backgroundScaleFactor ?: DEF_BACKGROUND_SCALE_FACTOR
        set(value) {
            applyGridProperty(
                { ad -> ad.backgroundScaleFactor = value },
                { iv -> iv.backgroundScaleFactor = value })
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adaptive_icon)
        val res = resources

        findViewById<View>(android.R.id.content).systemUiVisibility =
            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR)
        grid.setHasFixedSize(true)
        grid.addItemDecoration(CenteringDecoration(res.getInteger(R.integer.spans),
            res.getDimensionPixelSize(R.dimen.icon_size)))

        val fastOutSlowIn = AnimationUtils.loadInterpolator(
            this, android.R.interpolator.fast_out_slow_in)
        findViewById<View>(R.id.mask).setOnClickListener {
            corner = ++corner % corners.size
            with(ObjectAnimator.ofFloat(
                this@MainActivity,
                ICON_CORNER_RADIUS,
                corners[corner])) {
                duration = 200L
                interpolator = fastOutSlowIn
                start()
            }
        }

        val reorient = TransitionSet().apply {
            ordering = TransitionSet.ORDERING_TOGETHER
            addTransition(Fade(Fade.OUT))
            addTransition(ChangeBounds())
            addTransition(Fade(Fade.IN))
            duration = 200L
            interpolator = fastOutSlowIn
        }
        findViewById<View>(R.id.orientation).setOnClickListener { view ->
            orientation = orientation xor 1
            view.animate()
                .rotation(if (orientation == VERTICAL) 90f else 0f)
                .setDuration(160L)
                .setInterpolator(fastOutSlowIn)
                .start()
            TransitionManager.beginDelayedTransition(grid, reorient)
            (grid.layoutManager as GridLayoutManager).orientation = orientation
        }

        with(findViewById<ImageView>(R.id.background)) {
            setOnClickListener {
                decor = decor.next()
                grid.setBackgroundResource(decor.background)
                window.statusBarColor = decor.status
                setImageResource(decor.icon)

                if (decor.darkStatusIcons) {
                    systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                } else {
                    systemUiVisibility =
                        systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
                }
            }
        }

        findViewById<SeekBar>(R.id.foreground_parallax)
            .onSeek { progress -> foregroundTranslateFactor = progress / 100f }
        findViewById<SeekBar>(R.id.background_parallax)
            .onSeek { progress -> backgroundTranslateFactor = progress / 100f }
        findViewById<SeekBar>(R.id.foreground_scale)
            .onSeek { progress -> foregroundScaleFactor = progress / 100f }
        findViewById<SeekBar>(R.id.background_scale)
            .onSeek { progress -> backgroundScaleFactor = progress / 100f }

        BottomSheetBehavior.from(findViewById<View>(R.id.settings_sheet)).setBottomSheetCallback(
            object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {}

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    // make the sheet more opaque [80%–95%] as it slides up
                    val alpha = 204 + (38 * slideOffset).toInt()
                    val color = 0xccffffff.toInt() and 0x00ffffff or (alpha shl 24)
                    bottomSheet.setBackgroundColor(color)
                }
            })

        supportLoaderManager.initLoader(0, Bundle.EMPTY,
            object : LoaderManager.LoaderCallbacks<List<AdaptiveIconDrawable>> {
                override fun onCreateLoader(id: Int, args: Bundle) =
                    AdaptiveIconLoader(applicationContext)

                override fun onLoadFinished(loader: Loader<List<AdaptiveIconDrawable>>,
                                            data: List<AdaptiveIconDrawable>) {
                    findViewById<View>(R.id.loading).visibility = GONE
                    adapter = IconAdapter(data, corners[0])
                    grid.adapter = adapter
                    grid.setOnTouchListener(gridTouch)
                }

                override fun onLoaderReset(loader: Loader<List<AdaptiveIconDrawable>>) {}
            })
    }
}
