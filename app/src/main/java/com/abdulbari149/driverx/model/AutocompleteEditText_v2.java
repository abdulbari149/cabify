package com.abdulbari149.driverx.model;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class AutocompleteEditText_v2 extends androidx.appcompat.widget.AppCompatEditText {
    public AutocompleteEditText_v2(Context context) {
        super(context);
    }

    public AutocompleteEditText_v2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;

            case MotionEvent.ACTION_UP:
                performClick();
                return true;
        }
        return false;
    }

    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }
}