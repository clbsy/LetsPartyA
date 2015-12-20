package parteam.letspartya;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by xulin on 2015/12/18.
 */
public class FixedSizeViewPager extends ViewPager {

    public FixedSizeViewPager(Context context) {
        super(context);
    }

    public FixedSizeViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = getMeasuredWidth();
        setMeasuredDimension(measuredWidth, (int) (measuredWidth * 9f / 16));
    }
}
