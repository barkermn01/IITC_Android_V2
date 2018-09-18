package uk.co.barkersmedia.iitcv2;

import android.content.SharedPreferences;
import android.webkit.JavascriptInterface;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JsObject {
    private SharedPreferences sharedPref;

    private boolean  IITCEnabled = false;
    private HashMap<String, String> plugins;

    public JsObject(SharedPreferences sp){
        plugins = new HashMap<>();
        sharedPref = sp;

        plugins = new HashMap<>();

        plugins.put("iitc_plugin_linked_portals", "https://static.iitc.me/build/release/plugins/show-linked-portals.user.js");
        plugins.put("iitc_plugin_reso_energy", "https://static.iitc.me/build/release/plugins/reso-energy-pct-in-portal-detail.user.js");
        plugins.put("iitc_plugin_portal_distance", "https://static.iitc.me/build/release/plugins/distance-to-portal.user.js");
        plugins.put("iitc_plugin_layer_count", "https://static.iitc.me/build/release/plugins/layer-count.user.js");
        plugins.put("iitc_plugin_guess_player_level", "https://static.iitc.me/build/release/plugins/guess-player-levels.user.js");
        plugins.put("iitc_plugin_zoom_slider", "https://static.iitc.me/build/release/plugins/zoom-slider.user.js");
        plugins.put("iitc_plugin_portal_level_number", "https://static.iitc.me/build/release/plugins/portal-level-numbers.user.js");
        plugins.put("iitc_plugin_draw_tool", "https://static.iitc.me/build/release/plugins/draw-tools.user.js");
        plugins.put("iitc_plugin_player_tracker", "https://static.iitc.me/build/release/plugins/player-tracker.user.js");
        plugins.put("iitc_plugin_max_links", "https://static.iitc.me/build/release/plugins/max-links.user.js");
        plugins.put("iitc_plugin_show_less_portals", "https://static.iitc.me/build/release/plugins/show-less-portals-zoomed-out.user.js");
        plugins.put("iitc_plugin_cache_viewed_portals", "https://static.iitc.me/build/release/plugins/cache-details-on-map.user.js");

        IITCEnabled = sp.getBoolean("enable_iitc", true);
    }

    private void updateEnabled(String name, Boolean value){
        SharedPreferences.Editor edit = sharedPref.edit();
        edit.putBoolean(name, value);
        edit.commit();
    }

    public boolean getIITCEnabled(){
        return IITCEnabled;
    }

    @JavascriptInterface
    public String getLinksToLoad(){
        ArrayList<String> urls = new ArrayList<>();
        if(IITCEnabled) {
            urls.add("https://static.iitc.me/build/release/total-conversion-build.user.js");
            for(Map.Entry<String, String> entry : plugins.entrySet()) {
                if(sharedPref.getBoolean(entry.getKey(), false)){
                    urls.add(entry.getValue());
                }
            }
        }

        return new JSONArray(urls).toString();
    }
}
