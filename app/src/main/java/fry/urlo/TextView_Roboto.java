package fry.urlo;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class TextView_Roboto extends android.support.v7.widget.AppCompatTextView {

    public TextView_Roboto(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            createFont();
    }

    public TextView_Roboto(Context context, AttributeSet attrs) {
            super(context, attrs);
            createFont();
    }

    public TextView_Roboto(Context context) {
            super(context);
            createFont();
    }

    public void createFont() {
            Typeface font = Typeface.createFromAsset(getContext().getAssets(), "robo_font.ttf");
            setTypeface(font);
    }
}