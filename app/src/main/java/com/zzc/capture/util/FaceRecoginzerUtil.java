package com.zzc.capture.util;

import android.content.Context;
import android.util.Log;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static android.content.ContentValues.TAG;

/**
 * 作者: Zzc on 2018-03-01.
 * 版本: v1.0
 */

public class FaceRecoginzerUtil {
    private static volatile  FaceRecoginzerUtil instance;
    private Context mContext;
    private String mPath;
    private String mAtPath;
    private String mXmlPath;
    private String mPrePath;
    private FaceRecoginzerUtil() {

    }

    //识别
    public static native int recoginzer(String xmlpath,String path);

    //训练
    public static native void train(String path,String xmlpath);

    private FaceRecoginzerUtil(Context context) {
        this.mContext = context;
        mPath=context.getExternalFilesDir("facedata").getPath();
        mAtPath=mPath+"/"+"at.txt";
        mXmlPath=mPath+"/"+"MyFacePCAModel.xml";
        mPrePath=mPath+"/"+"pre.jpg";
        Log.i(TAG, "FaceRecoginzerUtil: "+mPath);
    }

    public static FaceRecoginzerUtil getInstance(Context context) {
        if (instance == null) {
            synchronized (FaceRecoginzerUtil.class) {
                if (instance == null)          //2
                    instance = new FaceRecoginzerUtil(context);  //3
            }
        }
        return instance;
    }


    public boolean save(Mat[] mats,Rect rect,String dirName) {
        for (int i = 0; i < mats.length; i++) {
            // 原图置灰
            Mat grayMat = new Mat();
            Imgproc.cvtColor(mats[i], grayMat, Imgproc.COLOR_BGR2GRAY);
            // 把检测到的人脸重新定义大小后保存成文件
            Mat sub = grayMat.submat(rect);
            Mat mat = new Mat();
            Size size = new Size(100, 100);
            Imgproc.resize(sub, mat, size);
            File file=new File(mPath+"/"+dirName);
            if(!file.exists()){
                file.mkdirs();
            }
            boolean issuccess= Highgui.imwrite(mPath + "/"+dirName+"/"+i+".jpg", mat);
            if(issuccess==false)
                return false;
        }
        return true;
    }

    public int getFaceCount(){
        int i=0;
        File file=new File(mPath);
        File[] files = file.listFiles();
        for (File f:
                files) {
            if(f.isDirectory())
                i++;
        }
        return i;
    }


    /**
     * 生成at.txt,用来训练人脸
     */
    public void createAtTxt() {
        File file=new File(mAtPath);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream os=null;
        OutputStreamWriter osw=null;
        BufferedWriter bw = null;
        try {
            os=new FileOutputStream(file);
            osw=new OutputStreamWriter(os);
            bw=new BufferedWriter(osw);

            File[] files = new File(mPath).listFiles();

            for (int i = 0; i < files.length; i++) {
                File directory=files[i];
                if(directory.isDirectory()){
                    File[] faces = directory.listFiles();
                    for (int i1 = 0; i1 < faces.length; i1++) {
                        Log.i(TAG, "createAtTxt: "+faces[i1].getAbsolutePath()+";"+directory.getName().substring(1));
                        bw.write(faces[i1].getAbsolutePath()+";"+directory.getName().substring(1)+"\r\n");
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                bw.close();
                osw.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getAtPath(){
        return mAtPath;
    }

    public String getXmlPath(){
        return mXmlPath;
    }

    public String getPrePath(){
        return mPrePath;
    }

    public boolean savePreImg(Context context, Mat image, Rect rect) {
        // 原图置灰
        Mat grayMat = new Mat();
        Imgproc.cvtColor(image, grayMat, Imgproc.COLOR_BGR2GRAY);
        // 把检测到的人脸重新定义大小后保存成文件
        Mat sub = grayMat.submat(rect);
        Mat mat = new Mat();
        Size size = new Size(100, 100);
        Imgproc.resize(sub, mat, size);
        return Highgui.imwrite(mPrePath, mat);
    }
}
