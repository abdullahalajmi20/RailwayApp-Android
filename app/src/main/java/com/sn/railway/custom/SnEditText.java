package com.sn.railway.custom;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.widget.EditText;


public class SnEditText extends AppCompatEditText {
    public SnEditText(Context context) {
        super(context);
        init();
    }

    public SnEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SnEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init() {
       /* Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "Montserrat-Regular.otf");
        setTypeface(tf);*/
    }

}
