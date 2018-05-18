package nu.rolandsson.jakob.noterav5.interaction;

import android.support.v4.view.ViewPager;

public interface PageChangeListener extends ViewPager.OnPageChangeListener {
    @Override
    default void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

    @Override
    void onPageSelected(int position);

    @Override
    default void onPageScrollStateChanged(int state) { }
}
