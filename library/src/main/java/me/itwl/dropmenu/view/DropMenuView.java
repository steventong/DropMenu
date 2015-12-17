package me.itwl.dropmenu.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import me.itwl.dropmenu.R;

/**
 * Created by Steven on 2015/12/15 0015.
 */
public class DropMenuView extends RelativeLayout {

    private FrameLayout menuContainer;
    private View popupWindowFrame;

    public DropMenuView(Context context) {
        super(context);
        init(context);
    }

    public DropMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        setAttr(context, attrs);
    }

    public DropMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        setAttr(context, attrs);
    }

    private void init(Context context) {
        inflate(context, R.layout.menu_container, this);
        this.menuContainer = (FrameLayout) findViewById(R.id.menuContent);
        this.popupWindowFrame = findViewById(R.id.menuBg);
    }

    private void setAttr(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DropMenuView);
        int frameWindowBackgroundColor = a.getColor(R.styleable.DropMenuView_frameWindowBackground, 0X00000000);
        popupWindowFrame.setBackgroundColor(frameWindowBackgroundColor);
        a.recycle();
    }

    public FrameLayout getMenuContainer() {
        return menuContainer;
    }

    public View getPopupWindowFrame() {
        return popupWindowFrame;
    }
}
