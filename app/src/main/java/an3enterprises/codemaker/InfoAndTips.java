package an3enterprises.codemaker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class InfoAndTips extends AppCompatActivity {
    
    int slideNum;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_info_and_tips);
        
        slideNum =  1;

    }
    
    public void goToNext(View view) {
        slideNum += 1;
        
        switch (slideNum) {
            case 1:
                
        }
    }
    
    public void goBack(View view) {
        slideNum -= 1;
    }
}
