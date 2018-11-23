package com.sn.railway.custom;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;


public class SnTextView extends AppCompatTextView {
    public SnTextView(Context context) {
        super(context);
        init();
    }

    public SnTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SnTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
      /*  Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "Montserrat-Regular.otf");
        setTypeface(tf);*/
    }
}
