package nduati.soc.googlecertification

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View

/**
 * TODO: document your custom view class.
 */
class TurningKnobView : View {

    private var _exampleString: String? = null // TODO: use a default from R.string...
    private var _exampleColor: Int = Color.RED // TODO: use a default from R.color...
    private var _exampleDimension: Float = 0f // TODO: use a default from R.dimen...

    private var textPaint: Paint = Paint()
    private var wellPaint: Paint = Paint()
    private var textWidth: Float = 0f
    private var textHeight: Float = 0f
    private var knobRadius: Float = 0f
    private var exampleString : String = "Manana"
    private var exampleColor : Int = Color.RED
    private var exampleDimension : Float = 0f




    /**
     * In the example view, this drawable is drawn above the text.
     */
    var exampleDrawable: Drawable? = null

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.TurningKnobView, defStyle, 0
        )

        _exampleString = a.getString(
            R.styleable.TurningKnobView_exampleString
        )
        _exampleColor = a.getColor(
            R.styleable.TurningKnobView_exampleColor,
            exampleColor
        )
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        _exampleDimension = a.getDimension(
            R.styleable.TurningKnobView_exampleDimension,
            exampleDimension
        )

        if (a.hasValue(R.styleable.TurningKnobView_exampleDrawable)) {
            exampleDrawable = a.getDrawable(
                R.styleable.TurningKnobView_exampleDrawable
            )
            exampleDrawable?.callback = this
        }

        a.recycle()

        // Set up a default TextPaint object
        textPaint = TextPaint().apply {
            flags = Paint.ANTI_ALIAS_FLAG
            textAlign = Paint.Align.LEFT
            textSize = 50f
            color = Color.RED
        }
        knobRadius = 100f
        wellPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = resources.getColor(R.color.colorPrimary)
        }


    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(80, 100)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        val paddingLeft = paddingLeft
        val paddingTop = paddingTop
        val paddingRight = paddingRight
        val paddingBottom = paddingBottom

        val contentWidth = width - paddingLeft - paddingRight
        val contentHeight = height - paddingTop - paddingBottom
        val rect = Rect((paddingLeft + (contentWidth - textWidth) / 2).toInt()-20,
            (paddingTop + (contentHeight + textHeight) / 2).toInt()-50,
            (paddingLeft + (contentWidth - textWidth) / 2).toInt(),
            (paddingTop + (contentHeight + textHeight) / 2).toInt()+20)


            // Draw the text.
            canvas.drawRect(rect, wellPaint)
            canvas.drawText("Nduati",paddingLeft + (contentWidth - textWidth) / 2,
                paddingTop + (contentHeight + textHeight) / 2,textPaint
            )


        // Draw the example drawable on top of the text.
        exampleDrawable?.let {
            it.setBounds(
                paddingLeft, paddingTop,
                paddingLeft + contentWidth, paddingTop + contentHeight
            )
            it.draw(canvas)
        }
    }
}
