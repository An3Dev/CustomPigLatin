package an3enterprises.codemaker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

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

    CheckBox noSuffixToNumCheckBox, noTransposeNumCheckBox;

    static String endingSuffixSharedPreferenceName = "endingSuffix";

    static String lettersTransposedSharedPreferenceName = "lettersTransposed";

    static String suffixToNumPreferenceName = "suffixToNum";

    static String transposeNumPreferenceName = "transposeNum";



    String endPhrase;
    String endPhraseBeginning;
    String userInputPreview;
    String startingPhrase;
    String cutWord;
    String userInputPreviewLower;
    String cutWordSub;
    String completedSentence;
    String originalWord;
    String endPunctuation;
    static String endPhraseStatic;

    Toast toast;
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

        noSuffixToNumCheckBox = (CheckBox) findViewById(R.id.no_suffix_num_check_box);
        noTransposeNumCheckBox = (CheckBox) findViewById(R.id.no_transpose_num_check_box);



        // First, set seek bar value to 1
        lettersCutSeekBar.setProgress(0);
        // Then, set the onSeekBarChangeListener

        // Get the SharedPreference settings
//        try {
            SharedPreferences endingSuffix = getSharedPreferences(endingSuffixSharedPreferenceName, Context.MODE_PRIVATE);
            endPhraseEditText.setText(endingSuffix.getString(endingSuffixSharedPreferenceName, ""));

            SharedPreferences lettersTransposedPreference = getSharedPreferences(lettersTransposedSharedPreferenceName, Context.MODE_PRIVATE);
            int num = lettersTransposedPreference.getInt(lettersTransposedSharedPreferenceName, 0);
            lettersCutSeekBar.setProgress(num);

            SharedPreferences suffixToNum = getSharedPreferences(suffixToNumPreferenceName, Context.MODE_PRIVATE);
            noSuffixToNumCheckBox.setChecked(Boolean.valueOf(suffixToNum.getString(suffixToNumPreferenceName, "true")));

            SharedPreferences transposeNum = getSharedPreferences(transposeNumPreferenceName, Context.MODE_PRIVATE);
            noTransposeNumCheckBox.setChecked(Boolean.valueOf(transposeNum.getString(transposeNumPreferenceName, "true")));

            seekBarValueTextView.setText(lettersCutSeekBar.getProgress() + "");

//        }
//        catch(Exception e) {
//            Toast.makeText(this, "Could not load data: " + e, Toast.LENGTH_SHORT).show();
//            Log.e("EditingActivity", e + "");
//        }

        translatePreview(lettersCutSeekBar.getProgress());


        noTransposeNumCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                translatePreview(seekBarValue);
            }
        });

        noSuffixToNumCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                translatePreview(seekBarValue);
            }
        });
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
                endPhraseStatic = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.info_and_tips_menu) {
            Intent intent = new Intent(EditingActivity.this, InfoAndTips.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void goToCodeMakingActivity(View view) {
        saveValues();
        Intent intent = new Intent(EditingActivity.this, TranslatorActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void saveValues() {
        SharedPreferences endingSuffixPreference = getSharedPreferences(endingSuffixSharedPreferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor endingSuffixPreferenceEditor = endingSuffixPreference.edit();
        endingSuffixPreferenceEditor.putString(endingSuffixSharedPreferenceName, endPhraseEditText.getText().toString());
        endingSuffixPreferenceEditor.commit();

        SharedPreferences lettersTransposedPreference = getSharedPreferences(lettersTransposedSharedPreferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor lettersTransposedEditor = lettersTransposedPreference.edit();
        lettersTransposedEditor.putInt(lettersTransposedSharedPreferenceName, lettersCutSeekBar.getProgress());
        lettersTransposedEditor.commit();

        SharedPreferences suffixToNum = getSharedPreferences(suffixToNumPreferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor suffixToNumEditor = suffixToNum.edit();
        suffixToNumEditor.putString(suffixToNumPreferenceName, noSuffixToNumCheckBox.isChecked() + "");
        suffixToNumEditor.commit();

        SharedPreferences transposeNum = getSharedPreferences(transposeNumPreferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor transposeNumEditor = transposeNum.edit();
        transposeNumEditor.putString(transposeNumPreferenceName, noTransposeNumCheckBox.isChecked() + "");
        transposeNumEditor.commit();
    }

    public void translatePreview(int seekBarValueInt) {
        try {
            endPhrase = endPhraseEditText.getText().toString();
            userInputPreview = getResources().getString(R.string.preview);

            endPunctuation = "";


            userInputPreviewLower = userInputPreview.toLowerCase();

            String[] wordList = userInputPreview.split("[ ]");

            ArrayList<String> translatedList = new ArrayList<>();
            translatedList.clear();
//        Log.d("Andres", endPhrase);
//        Log.d("Andres", seekBarValueInt + "");

            for (String word : wordList) {
                originalWord = word;
                completedSentence = "";
//            String test = "done.";


                if (word.contains(".") || word.contains(",") || word.contains("?") || word.contains("!") || word.contains(":") || word.contains(";")) {
                    try {
                        endPunctuation = originalWord.substring(originalWord.indexOf("."));
                        word = word.replace(".", "");
                    } catch (Exception e) {
                        try {
                            endPunctuation = originalWord.substring(originalWord.indexOf(","));
                            word = word.replace(",", "");
                        } catch (Exception e2) {
                            try {
                                endPunctuation = originalWord.substring(originalWord.indexOf("?"));
                                word = word.replace("?", "");
                            } catch (Exception e3) {
                                try {
                                    endPunctuation = originalWord.substring(originalWord.indexOf("!"));
                                    word = word.replace("!", "");
                                } catch (Exception e4) {
                                    try {
                                        endPunctuation = originalWord.substring(originalWord.indexOf(":"));
                                        word = word.replace(":", "");
                                    } catch (Exception e5) {
                                        try {
                                            endPunctuation = originalWord.substring(originalWord.indexOf(";"));
                                            word = word.replace(";", "");
                                        } catch (Exception e6) {
                                            endPunctuation = "";
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    endPunctuation = "";
                }

                if (noSuffixToNumCheckBox.isChecked()) {
                    if (word.matches("[0-9]+")) {
                        endPhrase = "";
//                        Toast.makeText(this, "yes", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        endPhrase = endPhraseEditText.getText().toString();
                    }
                } else {
                    endPhrase = endPhraseEditText.getText().toString();
                }

                if (noTransposeNumCheckBox.isChecked()) {
                    if (word.matches("[0-9]+")) {
                        seekBarValueInt = 0;
//                        Toast.makeText(this, "yes", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        seekBarValueInt = lettersCutSeekBar.getProgress();
                    }
                } else {
                    seekBarValueInt = lettersCutSeekBar.getProgress();
                }


                //split by letter
                String[] num = identifyUppercase(originalWord).split("(?!^)");


                if (seekBarValueInt != 0) {
                    word = word.toLowerCase();
                    try {
                        if (seekBarValueInt == word.length()) {
                            //this line causes stringIndexOutOfBoundsException
                            String exceptionString = "6chars";
                            exceptionString.substring(0, exceptionString.length() + 25);
                        } else {
                            endPhraseBeginning = word.substring(0, seekBarValueInt);
                        }

                    } catch (StringIndexOutOfBoundsException s) {

                        endPhraseBeginning = word.substring(0, word.length() - 1);
                    }
                    try {
                        if (seekBarValueInt == word.length()) {
                            //this line causes stringIndexOutOfBoundsException
                            String exceptionString = "6chars";
                            exceptionString.substring(0, exceptionString.length() + 25);
                        } else {
                            startingPhrase = word.substring(seekBarValueInt, word.length());
                        }
                    } catch (StringIndexOutOfBoundsException s2) {
                        startingPhrase = word.substring(word.length() - 1, word.length());
                    }

                    cutWord = startingPhrase + endPhraseBeginning;

                    cutWordSub = cutWord;
                    for (int i = 0; i < cutWord.length(); i++) {

                        for (String n : num) {

                            try {

                                if (i == Long.parseLong(n)) {

                                    try {
                                        String character = cutWordSub.charAt((int) Long.parseLong(n)) + "";
                                        cutWordSub = cutWordSub.substring(0, (int) Long.parseLong(n)) + character.toUpperCase() + cutWordSub.substring((int) Long.parseLong(n) + 1);
                                        Log.d("Andres", cutWordSub);
                                    } catch (StringIndexOutOfBoundsException s) {

                                    }
                                }
                            } catch (NumberFormatException NFE) {
//
                            }
                        }
                    }




                    cutWord = cutWordSub;
//

                    int numOfCaps = 0;
                    for (int i = 0; i < cutWord.length(); i++) {
                        if (isUpperCase(cutWord.charAt(i) + "")) {
                            numOfCaps += 1;
                        }
                    }
                    if (numOfCaps > 1) {

                        translatedList.add(cutWord + endPhrase.toUpperCase() + endPunctuation);


                    } else {
                        translatedList.add(cutWord + endPhrase + endPunctuation);
                    }
                } else {
                    int numOfCaps = 0;
                    for (int i = 0; i < word.length(); i++) {
                        if (isUpperCase(word.charAt(i) + "")) {
                            numOfCaps += 1;
                        }
                    }
                    if (numOfCaps > 1) {
                        translatedList.add(word + endPhrase.toUpperCase() + endPunctuation);
                    } else {
                        translatedList.add(word + endPhrase + endPunctuation);
                    }

                }

                for (String words : translatedList) {
                    completedSentence += words + " ";
                }
//            completedSentence = addSpaceForEveryWord(completedSentence, translatedList);

            }


            previewTextView.setText(completedSentence);
        } catch (final Exception e) {
            //This won't allow app to crash
            Snackbar.make(findViewById(R.id.editingModeTextView), "Error: " + e, Snackbar.LENGTH_INDEFINITE).setAction("Report", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

                    emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, "an3developer@gmail.com");

                    emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "CodeMaker Error");

                    emailIntent.setType("plain/text");
                    emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Error: " + e);

                    startActivity(emailIntent);
                }
            });
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

    public String addSpaceForEveryWord(String sentence, ArrayList<String> transList) {
        for (String words : transList) {
            sentence = sentence + words + " ";
        }
        return sentence;
    }

    public static boolean identifyPunctuationOrNum(String inputString) {

        String specialCharacters = " !#$%&'()*+,-./:;<=>?@[]^_`{|}~0123456789";
        String[] strlCharactersArray = new String[inputString.length()];
        for (int i = 0; i < inputString.length(); i++) {
            strlCharactersArray[i] = Character
                    .toString(inputString.charAt(i));
        }
        //now  strlCharactersArray[i]=[A, d, i, t, y, a]
        int count = 0;
        for (int i = 0; i <  strlCharactersArray.length; i++) {
            if (specialCharacters.contains( strlCharactersArray[i])) {
                count++;
            }

        }

        if (inputString != null && count == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onPause() {
        saveValues();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        saveValues();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        saveValues();
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        saveValues();
        super.onBackPressed();
    }
}
