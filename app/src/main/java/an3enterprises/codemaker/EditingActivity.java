package an3enterprises.codemaker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
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
    String completedSentence;
    String originalWord;

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

//    public void goToCodeMakingActivity(View view) {
//        Intent intent = new Intent(EditingActivity.this, CodeMakingActivity.class);
//        startActivity(intent);
//        overridePendingTransition(0, 0);
//    }
//
//    public void translatePreview(int seekBarValueInt) {
//        endPhrase = endPhraseEditText.getText().toString();
//        userInputPreview = getResources().getString(R.string.preview);
//
//
//
//
//        userInputPreviewLower = userInputPreview.toLowerCase();
//
//        String[] wordList = userInputPreview.split("[ ]");
//
//        ArrayList<String> translatedList = new ArrayList<>();
//        translatedList.clear();
////        Log.d("Andres", endPhrase);
////        Log.d("Andres", seekBarValueInt + "");
//
//        for (String word : wordList) {
//            originalWord = word;
//            completedSentence = "";
//            String[] num = identifyUppercase(originalWord).split("(?!^)");
////            String[] num = identifyUppercase(originalWord).toCharArray();
//
//
//            if (seekBarValueInt != 0) {
//                word = word.toLowerCase();
//                try {
//                    endPhraseBeginning = word.substring(0, seekBarValueInt);
//                } catch (StringIndexOutOfBoundsException s) {
//                    endPhraseBeginning = word.substring(0, word.length() - 1);
//                }
//                try {
//                    startingPhrase = word.substring(seekBarValueInt, word.length());
//                } catch (StringIndexOutOfBoundsException s2) {
//                    endPhraseBeginning = word.substring(word.length() - 1, word.length());
//                }
//
//                cutWord = startingPhrase + endPhraseBeginning;
//
//                cutWordSub = cutWord;
//                for (int i = 0; i < cutWord.length(); i++) {
//
//                    for (String n : num) {
//
//                        try {
//
//                            if (i == Long.parseLong(n)) {
//
//                                try {
//                                    String character = cutWordSub.charAt((int) Long.parseLong(n)) + "";
//                                    cutWordSub = cutWordSub.substring(0, (int) Long.parseLong(n)) + character.toUpperCase() + cutWordSub.substring((int) Long.parseLong(n) + 1);
//                                } catch (StringIndexOutOfBoundsException s) {
//                                        lettersCutSeekBar.setProgress(lettersCutSeekBar.getProgress() - 1);
//                                }
//                            }
//                        } catch (NumberFormatException NFE) {
////
//                        }
//                    }
//                }
//
//
//
//                cutWord = cutWordSub;
////
//                int numOfCaps = 0;
//                for (int i = 0; i < cutWord.length(); i++) {
//                    if (isUpperCase(cutWord.charAt(i) + "")) {
//                        numOfCaps += 1;
//                    }
//                }
//                if (numOfCaps > 1) {
//                    translatedList.add(cutWord + endPhrase.toUpperCase());
//
//                } else {
//                    translatedList.add(cutWord + endPhrase);
//                }
//            } else {
//                int numOfCaps = 0;
//                for (int i = 0; i < word.length(); i++) {
//                    if (isUpperCase(word.charAt(i) + "")) {
//                        numOfCaps += 1;
//                    }
//                }
//                if (numOfCaps > 1) {
//                    translatedList.add(word + endPhrase.toUpperCase());
//                } else {
//                    translatedList.add(word + endPhrase);
//                }
//
//            }
//
//            for (String words : translatedList) {
//                completedSentence += words + " ";
//            }
////            completedSentence = addSpaceForEveryWord(completedSentence, translatedList);
//
//        }
//
//
//        previewTextView.setText(completedSentence);
//    }
//
//    public String identifyUppercase(String word) {
//        String capString = "";
//        for (int i = 0; i < word.length(); i++){
//            if (isUpperCase(word.charAt(i) + "")) {
//                capString = capString + i;
//            }
//            //Process char
//        }
//        return capString;
//    }
//
//    public boolean isUpperCase(String string) {
//        boolean hasUppercase = !string.equals(string.toLowerCase());
//        if (!hasUppercase) {
//            return false;
//        }
//        if (hasUppercase) {
//            return true;
//        }else{
//            return false;
//        }
//    }
//
//
//    public void showToast(Context context, String message, int duration) {
//        if (toast != null) {
//            toast.cancel();
//        }
//        toast = Toast.makeText(context, message, duration);
//        toast.show();
//    }
//
//    public String addSpaceForEveryWord(String sentence, ArrayList<String> transList) {
//        for (String words : transList) {
//            sentence = sentence + words + " ";
//        }
//        return sentence;
//    }
//
//    public static boolean identifyPunctuationOrNum(String inputString) {
//
//        String specialCharacters = " !#$%&'()*+,-./:;<=>?@[]^_`{|}~0123456789";
//        String[] strlCharactersArray = new String[inputString.length()];
//        for (int i = 0; i < inputString.length(); i++) {
//            strlCharactersArray[i] = Character
//                    .toString(inputString.charAt(i));
//        }
//        //now  strlCharactersArray[i]=[A, d, i, t, y, a]
//        int count = 0;
//        for (int i = 0; i <  strlCharactersArray.length; i++) {
//            if (specialCharacters.contains( strlCharactersArray[i])) {
//                count++;
//            }
//
//        }
//
//        if (inputString != null && count == 0) {
//            return true;
//        } else {
//            return false;
//        }
//    }

    public void translatePreview(int seekBarValue) {
        String latin = "";
        endPhrase = endPhraseEditText.getText().toString();
        userInputPreview = getResources().getString(R.string.preview);
        int i = 0;
        while (i < userInputPreview.length()) {
            while (i < userInputPreview.length() && !isLetter(userInputPreview.charAt(i))) {
                latin = latin + userInputPreview.charAt(i);
                i++;
            }
            if (i >= userInputPreview.length())
                break;
            int begin = i;
            while (i < userInputPreview.length() && isLetter(userInputPreview.charAt(i))) {
                i++;
            }
            int end = i;
            latin = latin + pigWord(userInputPreview.substring(begin, end));
        }
        previewTextView.setText(latin);
    }

    public boolean isLetter(char c) {
        return ((c >= 'A' && c <= 'z') || (c >= 'a' && c <= 'z'));
    }

    public String pigWord(String word) {
//        int split = firstVowel(word);
//        if (split == 0) {
//            return word.substring(split) + word.substring(0, split) + "way";
//        } else {


            return cutWord + endPhrase;
//        }
    }


}
