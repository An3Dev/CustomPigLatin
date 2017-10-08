package an3enterprises.codemaker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;

public class InfoAndTips extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_info_and_tips);

        VideoView vidView = (VideoView)findViewById(R.id.myVideo);

//        Uri vidUri = Uri.parse("android.resource://" + getPackageName()+"/"+R.raw);

//        vidView.setVideoURI(vidUri);

//        vidView.start();
    }
}
