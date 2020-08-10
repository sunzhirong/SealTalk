package cn.rongcloud.im.niko.net.token;

import android.content.Context;
import android.content.SharedPreferences;

import cn.rongcloud.im.niko.common.NetConstant;
import cn.rongcloud.im.niko.net.ScUrl;

import static android.content.Context.MODE_PRIVATE;


public class TokenHttpClientManager {
    private static final String TAG = "HttpClientManager";
    private static TokenHttpClientManager instance;
    private Context context;
    private TokenRetrofitClient client;

    private TokenHttpClientManager(Context context) {
        this.context = context;
        client = new TokenRetrofitClient(context, ScUrl.TOKEN_BASE_URL);
    }

    public static TokenHttpClientManager getInstance(Context context) {
        if (instance == null) {
            synchronized (TokenHttpClientManager.class) {
                if (instance == null) {
                    instance = new TokenHttpClientManager(context);
                }
            }
        }

        return instance;
    }

    public TokenRetrofitClient getClient() {
        return client;
    }

}
