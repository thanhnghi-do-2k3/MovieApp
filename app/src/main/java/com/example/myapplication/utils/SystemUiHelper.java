package com.example.myapplication.utils;

import android.app.Activity;
import android.os.Build;
import android.view.View;

public class SystemUiHelper {
    public static void hideStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            activity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            // Lưu ý: Bạn cũng có thể cần thêm View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            // và View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN để ngăn nội dung bị cắt bởi thanh trạng thái
        }
    }

    public static void hideNavigationBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            activity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
            // Lưu ý: Bạn cũng có thể cần thêm View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            // và View.SYSTEM_UI_FLAG_LAYOUT_STABLE để ngăn nội dung bị cắt bởi thanh điều hướng
        }
    }

    public static void enableImmersiveMode(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
