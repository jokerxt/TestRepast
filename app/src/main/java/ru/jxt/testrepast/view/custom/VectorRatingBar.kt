package ru.jxt.testrepast.view.custom

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Shader
import android.graphics.drawable.*
import android.graphics.drawable.shapes.RoundRectShape
import android.graphics.drawable.shapes.Shape
import android.os.Build
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.RatingBar
import androidx.appcompat.app.AppCompatDelegate
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import ru.jxt.testrepast.R

class VectorRatingBar @JvmOverloads constructor(context: Context,
                                                attrs: AttributeSet? = null,
                                                defStyleAttr: Int = R.attr.ratingBarStyle)
    : RatingBar(context, attrs, defStyleAttr) {

    private var mSampleTile: Bitmap? = null

    private var offset: Int = 0

    private val drawableShape: Shape
        get() {
            val roundedCorners = floatArrayOf(5f, 5f, 5f, 5f, 5f, 5f, 5f, 5f)
            return RoundRectShape(roundedCorners, null, null)
        }

    init {
        //fix android 7.0 bug
        if(Build.VERSION.SDK_INT == Build.VERSION_CODES.N) {
            if(!isIndicator) {
                val seekBar = this::class.java.superclass.superclass
                seekBar.getDeclaredField("mTouchProgressOffset").apply {
                    isAccessible = true
                    setFloat(this@VectorRatingBar, 0.6f)
                }
            }
        }

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.VectorRatingBar, 0, 0)

        try {
            offset = typedArray.getDimensionPixelSize(R.styleable.VectorRatingBar_offsetBetweenStars, 0)
        } finally {
            typedArray.recycle()
        }

        progressDrawable = tileify(progressDrawable, false) as LayerDrawable
    }

    private fun tileify(drawable: Drawable, clip: Boolean): Drawable {

        if (drawable is LayerDrawable) {
            val numberOfLayers = drawable.numberOfLayers
            val outDrawables = arrayOfNulls<Drawable>(numberOfLayers)

            for (i in 0 until numberOfLayers) {
                val id = drawable.getId(i)
                outDrawables[i] = tileify(drawable.getDrawable(i),
                    id == android.R.id.progress || id == android.R.id.secondaryProgress)
            }

            return LayerDrawable(outDrawables).apply {
                outDrawables.forEachIndexed {
                        index, _ -> setId(index, drawable.getId(index))
                }
            }
        }
        else if (drawable is BitmapDrawable) {
            val tileBitmap = drawable.bitmap

            if (mSampleTile == null)
                mSampleTile = tileBitmap

            val shapeDrawable = ShapeDrawable(drawableShape)
            val bitmapShader = BitmapShader(tileBitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP)

            shapeDrawable.paint.shader = bitmapShader
            shapeDrawable.paint.colorFilter = drawable.paint.colorFilter
            return if (clip)
                ClipDrawable(shapeDrawable, Gravity.START, ClipDrawable.HORIZONTAL)
            else
                shapeDrawable
        }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && drawable is VectorDrawable ||
            Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP && drawable is VectorDrawableCompat
        ) {
            return tileify(getBitmapDrawableFromVectorDrawable(drawable), clip)
        }

        return drawable
    }

    private fun getBitmapDrawableFromVectorDrawable(drawable: Drawable): BitmapDrawable {
        return drawable.run {
            val newHeight = suggestedMinimumHeight.toFloat()
            val newWidth = (intrinsicWidth * ((newHeight*100.0)/intrinsicHeight))/100.0
            val bitmap = Bitmap.createBitmap(newWidth.toInt(), newHeight.toInt(), Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            setBounds(0, 0, canvas.width-offset, canvas.height-offset)
            draw(canvas)
            BitmapDrawable(resources, bitmap)
        }
    }

    @Synchronized
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var tmpWidthMeasureSpec = widthMeasureSpec
        var tmpHeightMeasureSpec = heightMeasureSpec

        mSampleTile?.apply {
            tmpWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width * numStars - offset, MeasureSpec.EXACTLY)
            tmpHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height - offset, MeasureSpec.EXACTLY)
        }

        super.onMeasure(tmpWidthMeasureSpec, tmpHeightMeasureSpec)
    }
}
