#include <jni.h>
#include <string>
#include <sstream>
#include <fstream>
#include <opencv2/opencv.hpp>
#include <android/log.h>
#define  LOG_TAG    "native-dev"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGE(...)  __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)
#define LOGI(...)  __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

using namespace std;
using namespace cv;

static void read_csv(const string& filename, vector<Mat>& imges, vector<int>& labels, char = ';')
{
    ifstream file(filename.c_str(), ifstream::in);
    if(!file)
    {
        LOGI("文件读取失败");
    }
    string line, path, lable;
    while (getline(file,line))
    {

        stringstream liness(line);
        getline(liness, path, ';');
        getline(liness, lable);

        if(!path.empty() && !lable.empty())
        {
            imges.push_back(imread(path, 0));
            labels.push_back(atoi(lable.c_str()));
        }
    }
}



extern "C"
JNIEXPORT jstring JNICALL
Java_com_zzc_capture_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    string hello = "Hello from C++";

    return env->NewStringUTF(hello.c_str());
}


extern "C"
JNIEXPORT jint JNICALL
Java_com_zzc_capture_util_FaceRecoginzerUtil_recoginzer(JNIEnv *env, jobject instance,
                                                        jstring xmlpath_, jstring path_) {
    const char *xmlpath = env->GetStringUTFChars(xmlpath_, 0);
    const char *path = env->GetStringUTFChars(path_, 0);
    Ptr<FaceRecognizer> model = createLBPHFaceRecognizer(1,8,8,8,123.0);
    LOGI("------------------1:%s",xmlpath);
    model->load(xmlpath);
    Mat mat=imread(path,CV_LOAD_IMAGE_GRAYSCALE);
    LOGI("------------------2:%s",path);
    // TODO
    int i = model->predict(mat);
    //打印参数
    string model_info = format("tLBPH(radius=%i, neighbors=%i, grid_x=%i, grid_y=%i, threshold=%.2f)",
                               model->getInt("radius"),
                               model->getInt("neighbors"),
                               model->getInt("grid_x"),
                               model->getInt("grid_y"),
                               model->getDouble("threshold"));

    LOGI("------哈哈:%s",model_info.c_str());
    env->ReleaseStringUTFChars(xmlpath_, xmlpath);
    env->ReleaseStringUTFChars(path_, path);
    return  i;
}


extern "C"
JNIEXPORT void JNICALL
Java_com_zzc_capture_util_FaceRecoginzerUtil_train(JNIEnv *env, jclass type, jstring path_,
                                                   jstring xmlpath_) {
    const char *path = env->GetStringUTFChars(path_, 0);
    const char *xmlpath = env->GetStringUTFChars(xmlpath_, 0);
    LOGI("-----------------------------------------");
    vector<Mat> imges;
    vector<int> labels;
    // TODO
    read_csv(path,imges,labels);
    /*
     * int radius=1, int neighbors=8,int grid_x=8, int grid_y=8, double threshold = DBL_MAX
     * */
    Ptr<FaceRecognizer> model = createLBPHFaceRecognizer(1,8,8,8,123.0);
    model->train(imges, labels);
    model->save(xmlpath);
    // TODO
    env->ReleaseStringUTFChars(path_, path);
    env->ReleaseStringUTFChars(xmlpath_, xmlpath);
}
