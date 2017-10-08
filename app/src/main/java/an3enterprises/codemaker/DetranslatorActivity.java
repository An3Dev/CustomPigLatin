package an3enterprises.codemaker;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static an3enterprises.codemaker.EditingActivity.endingSuffixSharedPreferenceName;
import static an3enterprises.codemaker.EditingActivity.lettersTransposedSharedPreferenceName;
import static an3enterprises.codemaker.EditingActivity.suffixToNumPreferenceName;
import static an3enterprises.codemaker.EditingActivity.transposeNumPreferenceName;
import static an3enterprises.codemaker.TranslatorActivity.numOfErrors;

public class DetranslatorActivity extends AppCompatActivity {

    EditText input;
    TextView output;



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

    String word;

    static String inputSPName = "input";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_detranslator);

        input = (EditText) findViewById(R.id.inputDetranslator);

        output = (TextView) findViewById(R.id.outputDetranslator);

        SharedPreferences endingSuffix = getSharedPreferences(endingSuffixSharedPreferenceName, Context.MODE_PRIVATE);
        endPhrase = endingSuffix.getString(endingSuffixSharedPreferenceName, "");

        completedSentence = "";

        SharedPreferences lettersTransposedPreference = getSharedPreferences(lettersTransposedSharedPreferenceName, Context.MODE_PRIVATE);
        int num = lettersTransposedPreference.getInt(lettersTransposedSharedPreferenceName, 0);
        transposedValue = num;

        SharedPreferences suffixToNum = getSharedPreferences(suffixToNumPreferenceName, Context.MODE_PRIVATE);
        noSuffixToNum = Boolean.valueOf(suffixToNum.getString(suffixToNumPreferenceName, "true"));

        SharedPreferences transposeNum = getSharedPreferences(transposeNumPreferenceName, Context.MODE_PRIVATE);
        noTransposeNum = Boolean.valueOf(transposeNum.getString(transposeNumPreferenceName, "true"));

        endPhraseSaved = endPhrase;
        transposedValueSaved = transposedValue;

        try {

            SharedPreferences inputSP = getSharedPreferences(inputSPName, Context.MODE_PRIVATE);
            String savedInput = inputSP.getString(inputSPName, "");
            input.setText(savedInput);
            translatePreview();
        }catch (Exception e) {
            Snackbar.make(findViewById(R.id.inputDetranslator), getResources().getString(R.string.cant_load_string), Snackbar.LENGTH_SHORT).show();
        }





        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    if(charSequence.toString().contains("['\"@#%$&*()_+=~`]")) {
                        Toast.makeText(DetranslatorActivity.this, "" + getResources().getString(R.string.please_no_special_symbols), Toast.LENGTH_LONG).show();
                    }
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

    }

    public void goToEditMode(View view) {
        Intent intent = new Intent(DetranslatorActivity.this, EditingActivity.class);
        startActivity(intent);
        saveInput();
    }

    public void goToTranslator(View view) {
        Intent intent = new Intent(DetranslatorActivity.this, TranslatorActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        saveInput();
    }

    public void pasteTranslation(View view) {
        CharSequence textToPaste = null;

// get the text from the clipboard manager
        int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            try {
                textToPaste = clipboard.getText();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            if (clipboard.getPrimaryClip() != null) {
                android.content.ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
                textToPaste = item.getText();
            }
        }

// insert the text at the cursor position, or if there is a selection it
// replaces the selection with the text to paste
        if (textToPaste != null) {
            int start = Math.max(input.getSelectionStart(), 0);
            int end = Math.max(input.getSelectionEnd(), 0);
            input.getText().replace(Math.min(start, end), Math.max(start, end),
                    textToPaste, 0, textToPaste.length());
        }
    }

    public void translatePreview() {

        try {
            inputString = input.getText().toString();

            endPunctuation = "";

            inputStringLower = inputString.toLowerCase();

            String[] wordList = inputString.split("[ ]");

            ArrayList<String> translatedList = new ArrayList<>();
            translatedList.clear();

            for (String word : wordList) {

                if (containsIllegalCharacters(word)) {
                    endPhrase = "";
                    transposedValue = 0;
                }

                originalWord = word;
                completedSentence = "";

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
                        // Don't remove last suffix
//                        Toast.makeText(this, "yes", Toast.LENGTH_SHORT).show();
                    }
                }

                if (noTransposeNum) {
                    if (word.matches("[0-9]+")) {
                        // Don't change position of num.
//                        Toast.makeText(this, "yes", Toast.LENGTH_SHORT).show();
                    }

                }

                // if last letters match end phrase
                if (word.toLowerCase().substring(word.length() - endPhrase.length(), word.length()).matches(endPhrase)) {
                    // take end phrase off
                    word = word.substring(0, word.length() - endPhrase.length());
                }



                // if transposed value is not 0
                if (transposedValue != 0) {
                    // move word back together

                    String lastLetters;
                    while(word.length() <= transposedValue) {
                        transposedValue -= 1;
                    }
                    lastLetters = word.substring(word.length() - transposedValue, word.length());
                    word = lastLetters + word.substring(0, word.length() - transposedValue);

                    //split by letter
                    String[] num = identifyUppercase(originalWord.substring(0, originalWord.length() - endPhrase.length())).split("(?!^)");
                    cutWord = word.toLowerCase();
                    cutWordSub = cutWord;
                    for (int i = 0; i < word.length(); i++) {

                        for (String n : num) {
//                            Log.d("Andres", n);
                            try {

                                if (i == Long.parseLong(n)) {

                                    try {
                                        String character = cutWordSub.charAt((int) Long.parseLong(n)) + "";
                                        cutWordSub = cutWordSub.substring(0, (int) Long.parseLong(n)) + character.toUpperCase() + cutWordSub.substring((int) Long.parseLong(n) + 1);
                                    } catch (StringIndexOutOfBoundsException s) {

                                    }
                                }
                            } catch (NumberFormatException NFE) {

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
//                        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
                        translatedList.add(cutWord+ endPunctuation);


                    } else {
                        translatedList.add(cutWord + endPunctuation);
                    }
                } else {
                    int numOfCaps = 0;
                    for (int i = 0; i < word.length(); i++) {
                        if (isUpperCase(word.charAt(i) + "")) {
                            numOfCaps += 1;
                        }
                    }
                    if (numOfCaps > 1) {
                        translatedList.add(word+ endPunctuation);
                    } else {
                        translatedList.add(word + endPunctuation);
                    }

                }

                endPhrase = endPhraseSaved;
                transposedValue = transposedValueSaved;
            }



            for (String words : translatedList) {
                completedSentence += words + " ";
            }

            output.setText(completedSentence);

        } catch (final Exception e) {
            //This won't allow app to crash

            numOfErrors += 1;

            if (numOfErrors > 100) {
                numOfErrors = 0;

                if (this.getCurrentFocus() != null) {
                    InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }

                Snackbar.make(findViewById(R.id.inputDetranslator), "Oops! There's an error. Please report to the dev.", Snackbar.LENGTH_LONG).setAction("Report", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

                        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"an3developer@gmail.com"});

                        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "CodeMaker Error");

                        emailIntent.setType("plain/text");
                        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Error: " + e);

                        startActivity(emailIntent);
                    }
                }).show();
            }
        }


    }

    private boolean containsIllegalCharacters(String displayName)
    {
        final int nameLength = displayName.length();

        for (int i = 0; i < nameLength; i++)
        {
            final char hs = displayName.charAt(i);

            if (0xd800 <= hs && hs <= 0xdbff)
            {
                final char ls = displayName.charAt(i + 1);
                final int uc = ((hs - 0xd800) * 0x400) + (ls - 0xdc00) + 0x10000;

                if (0x1d000 <= uc && uc <= 0x1f77f)
                {
                    return true;
                }
            }
            else if (Character.isHighSurrogate(hs))
            {
                final char ls = displayName.charAt(i + 1);

                if (ls == 0x20e3)
                {
                    return true;
                }
            }
            else
            {
                // non surrogate
                if (0x2100 <= hs && hs <= 0x27ff)
                {
                    return true;
                }
                else if (0x2B05 <= hs && hs <= 0x2b07)
                {
                    return true;
                }
                else if (0x2934 <= hs && hs <= 0x2935)
                {
                    return true;
                }
                else if (0x3297 <= hs && hs <= 0x3299)
                {
                    return true;
                }
                else if (hs == 0xa9 || hs == 0xae || hs == 0x303d || hs == 0x3030 || hs == 0x2b55 || hs == 0x2b1c || hs == 0x2b1b || hs == 0x2b50)
                {
                    return true;
                }
            }
        }

        return false;
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

    public void onShareClicked(View view) {
        //String url = "https://play.google.com/store/apps/details?id=an3enterprises.guessthenumber";
        String shareBody = completedSentence;
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        //sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, R.string.app_name);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_using)));
        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
//        Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
//                .setMessage(getString(R.string.invitation_message))
////                .setDeepLink(Uri.parse(getString(R.string.invitation_deep_link)))
//                .setCallToActionText(getString(R.string.invitation_cta))
//                .build();
//        startActivityForResult(intent, REQUEST_INVITE);
    }

    public void copyTranslation(View view) {
        if (!completedSentence.isEmpty() || !completedSentence.trim().isEmpty()) {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("PygLatin sentence", completedSentence);
            clipboard.setPrimaryClip(clip);

            if(input.isFocused()) {
                Toast.makeText(this, getResources().getString(R.string.translation_copied), Toast.LENGTH_SHORT).show();
            }
            else {
                Snackbar.make(findViewById(R.id.linearLayout), getResources().getString(R.string.translation_copied), Snackbar.LENGTH_LONG).show();
            }
        }else {
            if(input.isFocused()) {
                Toast.makeText(this, getResources().getString(R.string.you_cant_copy), Toast.LENGTH_SHORT).show();
            }
            else {
                Snackbar.make(findViewById(R.id.linearLayout), getResources().getString(R.string.you_cant_copy), Snackbar.LENGTH_SHORT).show();            }

        }
    }

    public void saveInput() {
        SharedPreferences inputSP = getSharedPreferences(inputSPName, Context.MODE_PRIVATE);
        SharedPreferences.Editor inputEditor = inputSP.edit();
        inputEditor.putString(inputSPName, input.getText().toString());
        inputEditor.commit();
    }

    @Override
    protected void onDestroy() {
        saveInput();
        super.onDestroy();

    }

    @Override
    protected void onRestart() {
        saveInput();
        super.onRestart();
    }

    @Override
    protected void onStop() {
        saveInput();
        super.onStop();
    }


    @Override
    protected void onPause() {
        saveInput();
        super.onPause();
    }
}
