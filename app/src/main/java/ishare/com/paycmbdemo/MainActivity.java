package ishare.com.paycmbdemo;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import cmb.pb.util.CMBKeyboardFunc;

public class MainActivity extends Activity {

    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webview = (WebView) findViewById(R.id.webview);
        // 对WebView进行设置
        WebSettings set = webview.getSettings();
        // 支持JS
        set.setJavaScriptEnabled(true);
        set.setSaveFormData(false);
        set.setSavePassword(false);
        set.setSupportZoom(false);

        pay();
    }

    private String url = "http://54.169.105.196/pay";
//    private String url = "http://123.207.11.104/pay";
//    private String url = "http://192.168.31.7:8080/pay";

    //调用一网通支付
    public void pay() {
        loadData();
        webview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 使用当前的WebView加载页面
                CMBKeyboardFunc kbFunc = new CMBKeyboardFunc(MainActivity.this);
                if (kbFunc.HandleUrlCall(webview, url) == false) {
                    return super.shouldOverrideUrlLoading(view, url);
                } else {
                    return true;
                }
            }
        });

    }

    private void loadData() {
        try {
            CookieSyncManager.createInstance(this.getApplicationContext());
            CookieManager.getInstance().removeAllCookie();
            CookieSyncManager.getInstance().sync();
        } catch (Exception e) {

        }
        webview.loadUrl(url);
    }
}
