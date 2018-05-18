package nu.rolandsson.jakob.noterav5.component;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;


public class PageComponent extends ViewPager {
    public interface PageFlipper {
        void onNextPage();
        void onPreviousPage();
    }

    private float initialXPos;
    private static final String TAG = ViewPager.class.getName();

    public PageComponent(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if(isLeftSwipe(event)) {
            return super.onInterceptTouchEvent(event);
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(isLeftSwipe(event)) {
            return super.onTouchEvent(event);
        }
        return false;
    }

    public void showNext() {
        setCurrentItem(getCurrentItem() + 1);
    }

    public void showPrevious() {
        setCurrentItem(getCurrentItem() - 1);
    }

    public boolean isLeftSwipe(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            initialXPos = event.getX();
            return false;
        }

        if(event.getAction() == MotionEvent.ACTION_MOVE) {
            if(initialXPos > event.getX()) {
                return true;
            }
        }
        return false;
    }
}
