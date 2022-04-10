package com.example.speechtotext;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Locale;

public class MainActivity extends Activity {
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private ImageButton btnSpeak;
    private TextView txtSpeechInput;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);
        this.btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);
        this.btnSpeak.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainActivity.this.promptSpeechInput();
            }
        });
        Intent intent = getIntent();
        ((Button) findViewById(R.id.exit)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.finish();
            }
        });
    }

    /* access modifiers changed from: private */
    public void promptSpeechInput() {
        Intent intent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
        intent.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form");
        intent.putExtra("android.speech.extra.LANGUAGE", Locale.getDefault());
        intent.putExtra("android.speech.extra.PROMPT", getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, 100);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), getString(R.string.speech_not_supported), 0).show();
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == -1 && data != null) {
            this.txtSpeechInput.setText(data.getStringArrayListExtra("android.speech.extra.RESULTS").get(0));
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
