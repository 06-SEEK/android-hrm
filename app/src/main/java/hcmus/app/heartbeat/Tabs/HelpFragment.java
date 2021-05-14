package hcmus.app.heartbeat.Tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import hcmus.app.heartbeat.R;

public class HelpFragment extends Fragment {

    private View rootView;
    WebView webview_player_view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView =  inflater.inflate(R.layout.fragment_help,container,false);
        webview_player_view = rootView.findViewById(R.id.webview_player_view);
        webview_player_view.getSettings().setJavaScriptEnabled(true);
        webview_player_view.loadUrl("https://www.youtube.com/watch?v=Pl7XZzzUHfY&autoplay=0");
        webview_player_view.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.getSettings().setMediaPlaybackRequiresUserGesture(false);
                return false;
            }
        });
        return rootView;
    }


}
