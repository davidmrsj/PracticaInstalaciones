package com.example.practicainstalaciones;

import static com.example.practicainstalaciones.MainActivity.editor;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public abstract class ClaseMenu extends AppCompatActivity {

    TextView userName;
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_item:
//                    userName = findViewById(R.id.textView7);
//                    userName.setText("");
                    editor.clear();
                    editor.commit();

                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }
}

