package com.taleb.singlechoice

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.taleb.widget.SingleChoiceView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SingleChoiceView.ISingleChoiceView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        singleChoiceView.listener = this
    }


    override fun onItemSelected(position: Int) {
        Log.e("singleChoice","onItemSelected at : $position")
    }

}
