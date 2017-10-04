package an3enterprises.codemaker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class InfoAndTips extends AppCompatActivity {
    
    int slideNum;
    TextView titleInfoTips, subtitleInfoTips;
    Button previousBtn;
    ImageView infoImage;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_info_and_tips);

        titleInfoTips = (TextView) findViewById(R.id.title_info_tips);
        subtitleInfoTips = (TextView) findViewById(R.id.subtitle_info_tips);
        previousBtn = (Button) findViewById(R.id.previous_btn);
        infoImage = (ImageView) findViewById(R.id.info_image);
        
        slideNum = 1;

        changeContent();

    }
    
    public void goToNext(View view) {
        slideNum += 1;
        changeContent();
    }
    
    public void goBack(View view) {
        slideNum -= 1;
        changeContent();
    }

    public void changeContent() {

        switch (slideNum) {
            case 1:
                infoImage.setImageResource(R.mipmap.ic_launcher_round);
                titleInfoTips.setText(getResources().getString(R.string.welcome_message));
                subtitleInfoTips.setText(getResources().getString(R.string.subtitle_welcome_message));
                previousBtn.setVisibility(View.GONE);
                break;

            case 2:
                infoImage.setImageResource(R.drawable.ending_suffix_example);
                titleInfoTips.setText(getResources().getString(R.string.suffix_info_title));
                subtitleInfoTips.setText(getResources().getString(R.string.suffix_info_subtitle));
                previousBtn.setVisibility(View.VISIBLE);
                break;

        }
    }
}
