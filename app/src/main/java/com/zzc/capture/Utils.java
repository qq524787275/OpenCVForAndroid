package com.zzc.capture;

import android.graphics.PointF;

/**
 * 作者: Zzc on 2018-01-17.
 * 版本: v1.0
 */

public class Utils {
    private static PointF calculatPoint(PointF startPoint, float length, float angle) {
        float deltaX = (float) Math.cos(Math.toRadians(angle)) * length;
        //符合Android坐标的y轴朝下的标准
        float deltaY = (float) Math.sin(Math.toRadians(angle-180)) * length;
        return new PointF(startPoint.x + deltaX, startPoint.y + deltaY);
    }
}
