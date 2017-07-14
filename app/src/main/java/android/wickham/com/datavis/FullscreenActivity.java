package android.wickham.com.datavis;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import org.apache.http.util.EncodingUtils;

public class FullscreenActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        webView = (WebView) findViewById(R.id.wb_webview);
        //Scroll bars should not be hidden
        webView.setScrollbarFadingEnabled(false);
        //Disable the horizontal scroll bar
        webView.setHorizontalScrollBarEnabled(true);
        webView.setVerticalScrollBarEnabled(true);
        //webView.setFitsSystemWindows(true);
        //Enable JavaScript
        webView.getSettings().setJavaScriptEnabled(true);
        //Set the user agent
        //webView.getSettings().setUserAgentString("AndroidWebView");
        //Clear the cache
        webView.clearCache(true);
        // webView.setBackgroundColor(Color.parseColor("#CCCCCC"));
        webView.setFadingEdgeLength(10);
        //webView.getSettings().setBuiltInZoomControls(true);
        //webView.getSettings().setDisplayZoomControls(false);

        final Activity activity = this;
        final ProgressDialog progressDialog = new ProgressDialog(activity);
        //progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgressStyle(ProgressDialog.THEME_HOLO_LIGHT);
        progressDialog.setCancelable(true);

        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                progressDialog.setCanceledOnTouchOutside(true);
                progressDialog.setTitle("Loading report ...");
                progressDialog.setButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        webView.destroy();
                        finish();
                    } });
                progressDialog.show();
                progressDialog.setProgress(0);
                activity.setProgress(progress * 1000);
                progressDialog.incrementProgressBy(progress);
                if(progress == 100 && progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });

        String postData = "";
        //webView.loadUrl("http://www.yahoo.com");
        webView.postUrl("http://www.majawi.com/test/cluster-dendo.html", EncodingUtils.getBytes(postData, "base64"));
    }
}