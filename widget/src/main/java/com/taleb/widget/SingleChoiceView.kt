package com.taleb.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import top.defaults.drawabletoolbox.DrawableBuilder



class SingleChoiceView :LinearLayout, View.OnClickListener {

    private var defaultTextColor:Int = Color.BLACK
    private var selectedTextColor:Int = Color.WHITE
    private var choices:Array<String> = arrayOf()
    private var choiceBackgroundColor:Int = Color.parseColor("#00a98f")
    private var strokeWidth = 2
    private var cornerRadius = 4
    private var selectedPosition = 0
    private var textSize:Int = 14
    private var textFont:String? = null
    private var backColor:Int = Color.TRANSPARENT
    var listener: ISingleChoiceView? = null
    // need text size property,text font, set attrs for stroke width,selected position,background color



    constructor(context: Context):super(context){init(context,null)}

    constructor(context: Context,attributeSet: AttributeSet?):super(context,attributeSet){
        init(context,attributeSet)
    }

    constructor(context: Context,attributeSet: AttributeSet?,  defStyleAttr: Int):super(context,attributeSet,defStyleAttr){
        init(context,attributeSet)
    }

    private fun init(context: Context, attributeSet: AttributeSet?) {
        this.minimumHeight = context.resources.getDimensionPixelSize(R.dimen.minimum_choice_height)
        this.clipChildren = true
        this.setPadding(strokeWidth,strokeWidth,strokeWidth,strokeWidth)
        setBackgroundDrawable()
        if (attributeSet == null) {return}
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.SingleChoiceView)
        try {
            this.selectedPosition = typedArray.getInt(R.styleable.SingleChoiceView_sc_select_position,0)
            this.textSize = typedArray.getDimensionPixelSize(R.styleable.SingleChoiceView_sc_choice_text_size,14)
            this.textFont = typedArray.getString(R.styleable.SingleChoiceView_sc_choice_text_font)
            this.backColor = typedArray.getColor(R.styleable.SingleChoiceView_sc_background_color,Color.TRANSPARENT)
            this.defaultTextColor = typedArray.getColor(R.styleable.SingleChoiceView_sc_choice_default_text_color, Color.BLACK)
            this.selectedTextColor = typedArray.getColor(R.styleable.SingleChoiceView_sc_choice_selected_text_color, Color.WHITE)
            this.choiceBackgroundColor = typedArray.getColor(R.styleable.SingleChoiceView_sc_choice_background, Color.parseColor("#00a98f"))
            setChoiceBackgroundColor(this.choiceBackgroundColor)
            val choicesRes = typedArray.getResourceId(R.styleable.SingleChoiceView_sc_choices, 0)
            setChoices(context.resources.getStringArray(choicesRes))
        } catch (ex: Exception) {
            ex.printStackTrace()
        } finally {
            typedArray.recycle()
        }

    }

    fun setChoiceBackgroundColor(choiceBackgroundColor:Int){
        this.choiceBackgroundColor = choiceBackgroundColor
        setBackgroundDrawable()
    }

    private fun setBackgroundDrawable(){
        val backDrwble = DrawableBuilder()
                .rectangle()
                .strokeWidth(strokeWidth)
                .strokeColor(choiceBackgroundColor)
                .solidColor(backColor)
                .cornerRadii(cornerRadius,cornerRadius,cornerRadius,cornerRadius)
                .build()
        this.background = backDrwble
    }

    fun setChoices(choices: Array<String>){
        this.choices = choices
        this.removeAllViews()
        //to do create choice item

        for(i in 0..(choices.size-1)) {
            val textView = TextView(this.context)
            textView.text = choices[i]
            this.addView(textView)
            var itemWidth:Int
            var itemHeight:Int
            val padding = 6
            if (this.orientation == VERTICAL){
                itemWidth = ViewGroup.LayoutParams.MATCH_PARENT
                itemHeight = 0
            }else {
                itemWidth = 0
                itemHeight = ViewGroup.LayoutParams.MATCH_PARENT
            }
            val lp = LayoutParams(itemWidth,itemHeight,1.0f)
            textView.layoutParams = lp
            textView.gravity = Gravity.CENTER
            textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
            textView.setTextColor(this.defaultTextColor)
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,this.textSize.toFloat())
            textView.setPadding(padding,padding,padding,padding)
            textView.setOnClickListener(this)
            if (textFont !== null) {
                try {textView.typeface = Typeface.createFromAsset(this.context.assets,textFont)}
                catch (e: java.lang.Exception){e.printStackTrace()}
            }
        }
        selectPosition(selectedPosition)
    }

    override fun onClick(p0: View?) {
        if (p0 == null) {return}
        val pos = this.indexOfChild(p0)
        if (this.selectedPosition == pos){return}
        selectPosition(pos)
        listener?.onItemSelected(pos)
    }


    fun selectPosition(selectedPosition: Int) {
        this.selectedPosition = selectedPosition
        for(i in 0..(choices.size-1)) {
            val txtView = this.getChildAt(i) as TextView
            val backDrwble = DrawableBuilder()
                    .rectangle()
                    .cornerRadii(cornerRadius,cornerRadius,cornerRadius,cornerRadius)

            val alphaAnimator = ObjectAnimator.ofFloat(txtView, View.ALPHA, 0.0f, 1.0f)
            alphaAnimator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator) {
                    txtView.background = backDrwble.build()
                }
            })

            if (i == this.selectedPosition) {
                backDrwble
                        .solidColor(choiceBackgroundColor)
                txtView.setTextColor(selectedTextColor)
                alphaAnimator.duration = 200
            }else {
                backDrwble
                        .solidColor(Color.TRANSPARENT)
                txtView.setTextColor(defaultTextColor)
                alphaAnimator.duration = 0
            }
            alphaAnimator.start()
        }
    }

    fun getSelectedPosition() :Int {
        return this.selectedPosition
    }

    interface ISingleChoiceView{
        fun onItemSelected(position: Int)
    }
}