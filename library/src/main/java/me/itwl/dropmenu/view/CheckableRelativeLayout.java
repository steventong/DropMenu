package me.itwl.dropmenu.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.RelativeLayout;

import me.itwl.dropmenu.R;

/**
 * Created by Steven on 2015/11/05 0005.
 */
public class CheckableRelativeLayout extends RelativeLayout implements Checkable {

    private boolean isChecked;

    public CheckableRelativeLayout(Context context) {
        super(context);
    }

    public CheckableRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CheckableRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setChecked(boolean checked) {
        isChecked = checked;
        if (checked)
            setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
        else
            setBackgroundColor(ContextCompat.getColor(getContext(), R.color.lineColor));
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void toggle() {

    }
}
