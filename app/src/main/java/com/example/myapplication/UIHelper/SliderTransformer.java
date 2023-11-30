package com.example.myapplication.UIHelper;

import android.view.View;

import androidx.core.view.ViewCompat;
import androidx.viewpager2.widget.ViewPager2;

import static java.lang.Math.abs;

public class SliderTransformer implements ViewPager2.PageTransformer {

    private final int offscreenPageLimit;
    private static final float DEFAULT_TRANSLATION_X = 0.0f;
    private static final float DEFAULT_TRANSLATION_FACTOR = 1.2f;
    private static final float SCALE_FACTOR = 0.14f;
    private static final float DEFAULT_SCALE = 1f;
    private static final float ALPHA_FACTOR = 0.3f;
    private static final float DEFAULT_ALPHA = 1f;

    public SliderTransformer(int offscreenPageLimit) {
        this.offscreenPageLimit = offscreenPageLimit;
    }

    @Override
    public void transformPage(View page, float position) {
        ViewCompat.setElevation(page, -abs(position));

        float scaleFactor = -SCALE_FACTOR * position + DEFAULT_SCALE;
        float alphaFactor = -ALPHA_FACTOR * position + DEFAULT_ALPHA;

        if (position <= 0f) {
            page.setTranslationX(DEFAULT_TRANSLATION_X);
            page.setScaleX(DEFAULT_SCALE);
            page.setScaleY(DEFAULT_SCALE);
            page.setAlpha(DEFAULT_ALPHA + position);
        } else if (position <= offscreenPageLimit - 1) {
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
            page.setTranslationX(-(page.getWidth() / DEFAULT_TRANSLATION_FACTOR) * position);
            page.setAlpha(alphaFactor);
        } else {
            page.setTranslationX(DEFAULT_TRANSLATION_X);
            page.setScaleX(DEFAULT_SCALE);
            page.setScaleY(DEFAULT_SCALE);
            page.setAlpha(DEFAULT_ALPHA);
        }
    }
}

