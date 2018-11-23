package com.sn.railway.custom;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.widget.Button;


public class SnButton extends AppCompatButton {
    public SnButton(Context context) {
        super(context);
        init();
    }

    public SnButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SnButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init() {
     /*   Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "Montserrat-Regular.otf");
        setTypeface(tf);*/
    }

}
