package uk.co.barkersmedia.iitcv2;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.*;

public class MainActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivityForResult(new Intent(this, PrefferencesActivity.class), 0);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation ==
                Configuration.ORIENTATION_LANDSCAPE) {
            // Change things
        } else if (newConfig.orientation ==
                Configuration.ORIENTATION_PORTRAIT){
            // Change other things
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // remove navigation bar
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN);

        //Remove title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        final JsObject jsObj = new JsObject(PreferenceManager.getDefaultSharedPreferences(getBaseContext()));

        webView = findViewById(R.id.webView);
        webView.addJavascriptInterface(jsObj, "IITCPlugin");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (jsObj.getIITCEnabled()) {
                    if (webView.getUrl().contains("ingress.com/intel")) {
                        webView.loadUrl("javascript:(function(){\n" +
                                "\t\tvar links = JSON.parse(IITCPlugin.getLinksToLoad());\n" +
                                "\t\tvar h = document.getElementsByTagName(\"head\")[0];\n" +
                                "\t\tfor(var i = 0; i < links.length; i++){\n" +
                                "\t\t\tvar src = links[i];\n" +
                                "\t\t\tvar s = document.createElement(\"script\");\n" +
                                "\t\t\ts.setAttribute(\"src\", src);\n" +
                                "\t\t\ts.setAttribute(\"type\", \"text/javascript\");\n" +
                                "\t\t\ts.setAttribute(\"async\", true);\n" +
                                "\t\t\th.appendChild(s);\n" +
                                "\t\t}\n" +
                                "})();");
                        webView.loadUrl("javascript:(function(){" +
                                "console.log(\"test\");" +
                                "var interval = setInterval(function(){" +
                                "   if(typeof jQuery === \"undefined\"){" +
                                "       return true;" +
                                "   }else{" +
                                "       clearInterval(interval);" +
                                "       jQuery(\".leaflet-top.leaflet-right\").hide();" +
                                "       jQuery(function(){"+
                                "          if(jQuery(\"input.leaflet-control-layers-selector[name=leaflet-base-layers]\")[0] == jQuery(\"input.leaflet-control-layers-selector[name=leaflet-base-layers]:checked\")[0] || jQuery(\"input.leaflet-control-layers-selector[name=leaflet-base-layers]\")[1] == jQuery(\"input.leaflet-control-layers-selector[name=leaflet-base-layers]:checked\")[0]){" +
                                "              jQuery(\"input.leaflet-control-layers-selector[name=leaflet-base-layers]\")[7].click();" +
                                "          }" +
                                "       });" +
                                "   }" +
                                "}, 4000);" +
                                "})();");
                    }
                }else{
                    // handle IITC not enabled.
                }
            }
        });

        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);

        webView.loadUrl("https://ingress.com/intel");
    }
}
