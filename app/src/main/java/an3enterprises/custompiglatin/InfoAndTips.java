package an3enterprises.custompiglatin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import an3enterprises.codemaker.R;

public class InfoAndTips extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_info_and_tips);
    }

    public void goToEditMode(View view) {
        Intent intent = new Intent(InfoAndTips.this, EditingActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
    }
}
