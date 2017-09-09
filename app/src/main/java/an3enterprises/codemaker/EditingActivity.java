package an3enterprises.codemaker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

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

    TextView seekBarValueTextView;


    String endPhrase;
    String endPhraseBeginning;
    String userInputPreview;
    String startingPhrase;
    String cutWord;
    String userInputPreviewLower;
    String cutWordSub;

    Toast toast;

    long indexOfUpper;
    int seekBarValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editing);

        endPhraseEditText = (EditText) findViewById(R.id.end_phrase_edit_text);
        editingModeTextView = (TextView) findViewById(R.id.editingModeTextView);
        previewTextView = (TextView) findViewById(R.id.preview_text_view);
        lettersCutSeekBar = (SeekBar) findViewById(R.id.letters_cut_seek_bar);
        seekBarValueTextView = (TextView) findViewById(R.id.seek_bar_value);

        // First, set seek bar value to 1
        lettersCutSeekBar.setProgress(0);
        // Then, set the onSeekBarChangeListener
        lettersCutSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                // Show SeekBar value

                seekBarValueTextView.setText(i + "");
                seekBarValue = i;
                translatePreview(seekBarValue);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                seekBarValue = seekBar.getProgress();
                translatePreview(seekBarValue);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBarValue = seekBar.getProgress();
                translatePreview(seekBarValue);
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
                translatePreview(seekBarValue);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    public void goToCodeMakingActivity(View view) {
        Intent intent = new Intent(EditingActivity.this, CodeMakingActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void translatePreview(int seekBarValueInt) {
        endPhrase = endPhraseEditText.getText().toString();
        userInputPreview = getResources().getString(R.string.preview);
//        userInputPreview = "HeLlO";

        String[] num = identifyUppercase(userInputPreview).split("(?!^)");
        Log.d("Andres", num + "");



        userInputPreviewLower = userInputPreview.toLowerCase();
//        Log.d("Andres", endPhrase);
//        Log.d("Andres", seekBarValueInt + "");
        if (seekBarValueInt != 0) {
            try {
                endPhraseBeginning = userInputPreviewLower.substring(0, seekBarValueInt);
            } catch (StringIndexOutOfBoundsException s) {
                showToast(EditingActivity.this, "Nope", 0);
            }
            try {
                startingPhrase = userInputPreviewLower.substring(seekBarValueInt, userInputPreviewLower.length());
            } catch (StringIndexOutOfBoundsException s2) {
                showToast(EditingActivity.this, "Nope2", 0);
            }

            cutWord = startingPhrase + endPhraseBeginning;

            cutWordSub = cutWord;
            for (int i = 0; i < cutWord.length(); i++){
                
                for (String n : num) {

                    if (i == Long.parseLong(n)) {

                        try {
                            String character = cutWordSub.charAt((int)Long.parseLong(n)) + "";
                            cutWordSub = cutWordSub.substring(0, (int)Long.parseLong(n)) + character.toUpperCase() + cutWordSub.substring((int)Long.parseLong(n) + 1);
//                            showToast(EditingActivity.this, cutWordSub, 0);
                        }
                        catch (StringIndexOutOfBoundsException s) {
                            showToast(EditingActivity.this, "Nope3", 1);
                        }
                    }
                }
            }

            cutWord = cutWordSub;

            previewTextView.setText(cutWord);
        }
        else {
            previewTextView.setText(userInputPreview);
        }
    }

    public String identifyUppercase(String word) {
        String capString = "";
        for (int i = 0; i < word.length(); i++){
            if (isUpperCase(word.charAt(i) + "")) {
                capString = capString + i;
            }
            //Process char
        }
        return capString;
    }

    public boolean isUpperCase(String string) {
        boolean hasUppercase = !string.equals(string.toLowerCase());
        if (!hasUppercase) {
            return false;
        }
        if (hasUppercase) {
            return true;
        }else{
            return false;
        }
    }


    public void showToast(Context context, String message, int duration) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, message, duration);
        toast.show();
    }

}
