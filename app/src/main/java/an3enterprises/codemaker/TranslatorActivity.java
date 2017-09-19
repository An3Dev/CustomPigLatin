package an3enterprises.codemaker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class TranslatorActivity extends AppCompatActivity {

    EditText input;
    TextView output;

    static String endingSuffixSharedPreferenceName = "endingSuffix";

    static String lettersTransposedSharedPreferenceName = "lettersTransposed";

    static String suffixToNumPreferenceName = "suffixToNum";

    static String transposeNumPreferenceName = "transposeNum";



    String endPhrase;
    String endPhraseBeginning;
    String inputString;
    String startingPhrase;
    String cutWord;
    String inputStringLower;
    String cutWordSub;
    String completedSentence;
    String originalWord;
    String endPunctuation;

    String endPhraseSaved;
    int transposedValueSaved;

    int transposedValue;

    boolean noSuffixToNum, noTransposeNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translator);

        input = (EditText) findViewById(R.id.input);

        output = (TextView) findViewById(R.id.output);

        SharedPreferences endingSuffix = getSharedPreferences(endingSuffixSharedPreferenceName, Context.MODE_PRIVATE);
        endPhrase = endingSuffix.getString(endingSuffixSharedPreferenceName, "");

        SharedPreferences lettersTransposedPreference = getSharedPreferences(lettersTransposedSharedPreferenceName, Context.MODE_PRIVATE);
        transposedValue = lettersTransposedPreference.getInt(lettersTransposedSharedPreferenceName, 0);

        SharedPreferences suffixToNum = getSharedPreferences(suffixToNumPreferenceName, Context.MODE_PRIVATE);
        noSuffixToNum = Boolean.valueOf(suffixToNum.getString(suffixToNumPreferenceName, "true"));

        SharedPreferences transposeNum = getSharedPreferences(transposeNumPreferenceName, Context.MODE_PRIVATE);
        noTransposeNum = Boolean.valueOf(transposeNum.getString(transposeNumPreferenceName, "true"));

        endPhraseSaved = endPhrase;
        transposedValueSaved = transposedValue;

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    translatePreview();
                }
                else {
                    output.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

    public void goToDetranslator(View view) {
        Intent intent = new Intent(TranslatorActivity.this, CodeDetranslator.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void translatePreview() {
        try {
            inputString = input.getText().toString();

            endPunctuation = "";


            inputStringLower = inputString.toLowerCase();

            String[] wordList = inputStringLower.split("[ ]");

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

                if (noSuffixToNum) {
                    if (word.matches("[0-9]+")) {
                        endPhrase = "";
//                        Toast.makeText(this, "yes", Toast.LENGTH_SHORT).show();
                    }
                }

                if (noTransposeNum) {
                    if (word.matches("[0-9]+")) {
                        transposedValue = 0;
//                        Toast.makeText(this, "yes", Toast.LENGTH_SHORT).show();
                    }

                }


                //split by letter
                String[] num = identifyUppercase(originalWord).split("(?!^)");


                if (transposedValue != 0) {
                    word = word.toLowerCase();
                    try {
                        if (transposedValue == word.length()) {
                            //this line causes stringIndexOutOfBoundsException
                            String exceptionString = "6chars";
                            exceptionString.substring(0, exceptionString.length() + 25);
                        } else {
                            endPhraseBeginning = word.substring(0, transposedValue);
                        }

                    } catch (StringIndexOutOfBoundsException s) {

                        endPhraseBeginning = word.substring(0, word.length() - 1);
                    }
                    try {
                        if (transposedValue == word.length()) {
                            //this line causes stringIndexOutOfBoundsException
                            String exceptionString = "6chars";
                            exceptionString.substring(0, exceptionString.length() + 25);
                        } else {
                            startingPhrase = word.substring(transposedValue, word.length());
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

                endPhrase = endPhraseSaved;

                transposedValue = transposedValueSaved;

            }


            output.setText(completedSentence);
        } catch (final Exception e) {
            //This won't allow app to crash
            if (this.getCurrentFocus() != null) {
                InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
            Snackbar.make(findViewById(R.id.input), "Error: " + e, Snackbar.LENGTH_INDEFINITE).setAction("Report", new View.OnClickListener() {
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

    public void copyTranslation(View view) {

    }

    public void onShareClicked(View view) {

    }
}
