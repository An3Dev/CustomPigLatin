package an3enterprises.codemaker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;

public class SeekBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_seek_bar);
    }

    public void goToNext(View view) {
        Intent intent = new Intent(SeekBarActivity.this, SuffixActivity.class);
        startActivity(intent);
    }

    public void goBack(View view) {
        Intent intent = new Intent();
        startActivity(intent);
    }
}
