package com.shashi.urlopener;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText urlTextView;
    private Button openUrlButton;
    private String urlText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        urlTextView = findViewById(R.id.url_text);
        openUrlButton = findViewById(R.id.open_url_button);
        openUrlButton.setEnabled(false);

        urlTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                urlText = charSequence.toString().trim();
                openUrlButton.setEnabled(urlText.length() != 0);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        openUrlButton.setOnClickListener(view -> {
            if (!urlText.startsWith("http://") && !urlText.startsWith("https://")) {
                urlText = "http://" + urlText;
            }
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlText));

            // Assuming multiple browsers are installed in the system
            try {
                // If JioPages is installed in the system, the link will open there
                intent.setPackage("com.jio.web");
                startActivity(intent);
            } catch (ActivityNotFoundException exception) {
                // If JioPages is not installed, it will open in default browser
                // If no browser is selected as default browser, it will ask to select the browser to open the link
                intent.setPackage(null);
                startActivity(intent);
            }

            urlTextView.setText("");
        });
    }
}