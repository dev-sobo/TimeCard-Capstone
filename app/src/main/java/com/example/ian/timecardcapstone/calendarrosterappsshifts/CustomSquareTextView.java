package com.example.ian.timecardcapstone.calendarrosterappsshifts;

import android.content.Context;
import android.util.AttributeSet;

import com.roomorama.caldroid.SquareTextView;

/**
 * A customization of the sqauretextview from caldroid in order to accomdate filling the whole
 * screen
 */
public class CustomSquareTextView extends SquareTextView {
    int myHeight = 0;

    public CustomSquareTextView(Context context) {
        super(context);
    }

    public CustomSquareTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CustomSquareTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (myHeight == 0) {
            myHeight = RosterappsFragment.getHeights();
        }
        if (myHeight == 0) {
            myHeight = 600;
        }
        this.setMeasuredDimension(widthMeasureSpec, myHeight / 5);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        myHeight = RosterappsFragment.getHeights();
        if (myHeight == 0) {
            myHeight = 600;
        }
        super.onSizeChanged(w, myHeight / 5, oldw, oldh);
    }


}
