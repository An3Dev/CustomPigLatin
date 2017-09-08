package an3enterprises.codemaker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class EditingActivity extends AppCompatActivity {

    // Where user enters ending letters
    EditText endPhraseEditText;
    // Alerts user he/she is editing
    TextView editingModeTextView;
    // Shows textView preview
    TextView previewTextView;
    // SeekBar where user controls
    // how many letters are cut
    SeekBar lettersCutSeekBar;

    TextView seekBarValue0;
    TextView seekBarValue1;
    TextView seekBarValue2;
    TextView seekBarValue3;
    TextView seekBarValue4;
    TextView seekBarValue5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editing);

        endPhraseEditText = (EditText) findViewById(R.id.end_phrase_edit_text);
        editingModeTextView = (TextView) findViewById(R.id.editingModeTextView);
        previewTextView = (TextView) findViewById(R.id.preview_text_view);
        lettersCutSeekBar = (SeekBar) findViewById(R.id.letters_cut_seek_bar);
        seekBarValue0 = (TextView) findViewById(R.id.seek_bar_value_0);
        seekBarValue1 = (TextView) findViewById(R.id.seek_bar_value_1);
        seekBarValue2 = (TextView) findViewById(R.id.seek_bar_value_2);
        seekBarValue3 = (TextView) findViewById(R.id.seek_bar_value_3);
        seekBarValue4 = (TextView) findViewById(R.id.seek_bar_value_4);
        seekBarValue5 = (TextView) findViewById(R.id.seek_bar_value_5);

        // First, set seek bar value to 1
        lettersCutSeekBar.setProgress(1);
        // Then, set the onSeekBarChangeListener
        lettersCutSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                // Show SeekBar value

                if (i == 0) {
                    seekBarValue0.setVisibility(View.VISIBLE);
                    seekBarValue1.setVisibility(View.INVISIBLE);
                    seekBarValue2.setVisibility(View.INVISIBLE);
                    seekBarValue3.setVisibility(View.INVISIBLE);
                    seekBarValue4.setVisibility(View.INVISIBLE);
                    seekBarValue5.setVisibility(View.INVISIBLE);
                }

                if (i == 1) {
                    seekBarValue0.setVisibility(View.INVISIBLE);
                    seekBarValue1.setVisibility(View.VISIBLE);
                    seekBarValue2.setVisibility(View.INVISIBLE);
                    seekBarValue3.setVisibility(View.INVISIBLE);
                    seekBarValue4.setVisibility(View.INVISIBLE);
                    seekBarValue5.setVisibility(View.INVISIBLE);
                }

                if (i == 2) {
                    seekBarValue0.setVisibility(View.INVISIBLE);
                    seekBarValue1.setVisibility(View.INVISIBLE);
                    seekBarValue2.setVisibility(View.VISIBLE);
                    seekBarValue3.setVisibility(View.INVISIBLE);
                    seekBarValue4.setVisibility(View.INVISIBLE);
                    seekBarValue5.setVisibility(View.INVISIBLE);
                }

                if (i == 3) {
                    seekBarValue0.setVisibility(View.INVISIBLE);
                    seekBarValue1.setVisibility(View.INVISIBLE);
                    seekBarValue2.setVisibility(View.INVISIBLE);
                    seekBarValue3.setVisibility(View.VISIBLE);
                    seekBarValue4.setVisibility(View.INVISIBLE);
                    seekBarValue5.setVisibility(View.INVISIBLE);
                }

                if (i == 4) {
                    seekBarValue0.setVisibility(View.INVISIBLE);
                    seekBarValue1.setVisibility(View.INVISIBLE);
                    seekBarValue2.setVisibility(View.INVISIBLE);
                    seekBarValue3.setVisibility(View.INVISIBLE);
                    seekBarValue4.setVisibility(View.VISIBLE);
                    seekBarValue5.setVisibility(View.INVISIBLE);
                }

                if (i == 5) {
                    seekBarValue0.setVisibility(View.INVISIBLE);
                    seekBarValue1.setVisibility(View.INVISIBLE);
                    seekBarValue2.setVisibility(View.INVISIBLE);
                    seekBarValue3.setVisibility(View.INVISIBLE);
                    seekBarValue4.setVisibility(View.INVISIBLE);
                    seekBarValue5.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        endPhraseEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                return false;
            }
        });

        endPhraseEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    public void goToCodeMakingActivity(View view) {
        Intent intent = new Intent(EditingActivity.this, CodeMakingActivity.class);
        startActivity(intent);
    }

    public void translate() {
        
    }

}
