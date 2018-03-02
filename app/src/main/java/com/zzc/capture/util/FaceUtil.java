package com.zzc.capture.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: Zzc on 2018-02-28.
 * 版本: v1.0
 */

public class FaceUtil {
    private static final String TAG = "FaceUtil";
    /**
     * 特征保存
     *
     * @param context  Context
     * @param image    Mat
     * @param rect     人脸信息
     * @param fileName 文件名字
     * @return 保存是否成功
     */
    public static boolean saveImage(Context context, Mat image, Rect rect, String fileName) {
        // 原图置灰
        Mat grayMat = new Mat();
        Imgproc.cvtColor(image, grayMat, Imgproc.COLOR_BGR2GRAY);
        // 把检测到的人脸重新定义大小后保存成文件
        Mat sub = grayMat.submat(rect);
        Mat mat = new Mat();
        Size size = new Size(100, 100);
        Imgproc.resize(sub, mat, size);
        return Highgui.imwrite(getFilePath(context, fileName), mat);
    }

    /**
     * 获取人脸特征路径
     *
     * @param fileName 人脸特征的图片的名字
     * @return 路径
     */
    private static String getFilePath(Context context, String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return null;
        }
        // 内存路径
        return context.getApplicationContext().getFilesDir().getPath() + fileName + ".jpg";
        // 内存卡路径 需要SD卡读取权限
        // return Environment.getExternalStorageDirectory() + "/FaceDetect/" + fileName + ".jpg";
    }

    /**
     * 提取特征
     *
     * @param context  Context
     * @param fileName 文件名
     * @return 特征图片
     */
    public static Bitmap getImage(Context context, String fileName) {
        String filePath = getFilePath(context, fileName);
        if (TextUtils.isEmpty(filePath)) {
            return null;
        } else {
            return BitmapFactory.decodeFile(filePath);
        }
    }

    /**
     * 特征对比
     *
     * @param context   Context
     * @param fileName1 人脸特征
     * @param fileName2 人脸特征
     * @return 相似度
     */
    public static double compare(Context context, String fileName1, String fileName2) {
        String pathFile1 = getFilePath(context, fileName1);
        String pathFile2 = getFilePath(context, fileName2);
        Mat mat1 = Highgui.imread(pathFile1);
        Mat mat2 = Highgui.imread(pathFile2);

        MatOfInt histSize = new MatOfInt(256);
        MatOfFloat histRange = new MatOfFloat(0, 256);
        MatOfInt channels = new MatOfInt(0);

        List<Mat> mats1=new ArrayList<>();
        List<Mat> mats2=new ArrayList<>();

        mats1.add(mat1);
        mats2.add(mat2);

        Mat hist_a = new Mat();
        Mat hist_b = new Mat();

        Imgproc.calcHist(mats1,channels,new Mat(),hist_a,histSize,histRange);
        Imgproc.calcHist(mats2,channels,new Mat(),hist_b,histSize,histRange);

        double c1 = Imgproc.compareHist(hist_a, hist_b,Imgproc.CV_COMP_CORREL)*100;
        return c1;
//        FeatureDetector fd=FeatureDetector.create(FeatureDetector.AKAZE);
//        DescriptorExtractor de=DescriptorExtractor.create(DescriptorExtractor.AKAZE);
//        DescriptorMatcher matcher=DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_L1);
//
//        MatOfKeyPoint mkp = new MatOfKeyPoint();
//        fd.detect(mat1, mkp);
//        Mat desc = new Mat();
//        de.compute(mat1, mkp, desc);
//        Features2d.drawKeypoints(mat1, mkp, mat1);
//
//        MatOfKeyPoint mkp2 = new MatOfKeyPoint();
//        fd.detect(mat2, mkp2);
//        Mat desc2 = new Mat();
//        de.compute(mat2, mkp2, desc2);
//        Features2d.drawKeypoints(mat2, mkp2, mat2);
//
//        // Matching features
//        MatOfDMatch Matches = new MatOfDMatch();
//        matcher.match(desc, desc2, Matches);
//
//        double maxDist = Double.MIN_VALUE;
//        double minDist = Double.MAX_VALUE;
//
//        DMatch[] mats = Matches.toArray();
//
//        for (int i = 0; i < mats.length; i++) {
//            double dist = mats[i].distance;
//            if (dist < minDist) {
//                minDist = dist;
//            }
//            if (dist > maxDist) {
//                maxDist = dist;
//            }
//        }
//        Log.i(TAG, "compare: Min Distance:"+minDist+","+"Max Distance:"+maxDist);
//        List<DMatch> goodMatch = new LinkedList<>();
//
//        for (int i = 0; i < mats.length; i++) {
//            double dist = mats[i].distance;
//            if (dist < 3 * minDist && dist < 0.2f) {
//                goodMatch.add(mats[i]);
//            }
//        }
//        Log.i(TAG, "compare: 总点数:"+mats.length+"好点数:"+goodMatch.size());
    }
}
