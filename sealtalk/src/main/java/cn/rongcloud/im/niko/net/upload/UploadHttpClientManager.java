package cn.rongcloud.im.niko.net.upload;

import android.content.Context;
import android.content.SharedPreferences;

import cn.rongcloud.im.niko.common.NetConstant;
import cn.rongcloud.im.niko.net.ScUrl;

import static android.content.Context.MODE_PRIVATE;


public class UploadHttpClientManager {
    private static final String TAG = "HttpClientManager";
    private static UploadHttpClientManager instance;
    private Context context;
    private UploadRetrofitClient client;

    private UploadHttpClientManager(Context context) {
        this.context = context;
        client = new UploadRetrofitClient(context, ScUrl.UPLOAD_BASE_URL);
    }

    public static UploadHttpClientManager getInstance(Context context) {
        if (instance == null) {
            synchronized (UploadHttpClientManager.class) {
                if (instance == null) {
                    instance = new UploadHttpClientManager(context);
                }
            }
        }

        return instance;
    }

    public UploadRetrofitClient getClient() {
        return client;
    }

}
