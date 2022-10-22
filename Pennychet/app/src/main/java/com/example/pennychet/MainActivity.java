package com.example.pennychet;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    // Floating Action Button (FAB)
    FloatingActionButton mAddFab, mAddAlarmFab, mAddPersonFab;
    TextView addAlarmActionText, addPersonActionText;
    Boolean isAllFabsVisible;

    // Drawer Layout
    private DrawerLayout drawerLayout;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: [STARTED]");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        // ActionBar actionbar = getSupportActionBar();
        // actionbar.setDisplayHomeAsUpEnabled(true);
        // actionbar.setDisplayShowHomeEnabled(true);
        // actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        drawerLayout = findViewById(R.id.drawer_layout);
        // NavigationView navigationView = findViewById(R.id.nav_view);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        // FAB
        mAddFab = findViewById(R.id.add_fab);
        mAddAlarmFab = findViewById(R.id.add_alarm_fab);
        mAddPersonFab = findViewById(R.id.add_person_fab);

        addAlarmActionText = findViewById(R.id.add_alarm_action_text);
        addPersonActionText = findViewById(R.id.add_person_action_text);

        mAddAlarmFab.setVisibility(View.GONE);
        mAddPersonFab.setVisibility(View.GONE);
        addAlarmActionText.setVisibility(View.GONE);
        addPersonActionText.setVisibility(View.GONE);
        isAllFabsVisible = false;

        mAddFab.setOnClickListener(view -> {
            if (!isAllFabsVisible) {
                mAddAlarmFab.show();
                mAddPersonFab.show();
                addAlarmActionText.setVisibility(View.VISIBLE);
                addPersonActionText.setVisibility(View.VISIBLE);
                isAllFabsVisible = true;
            } else {
                mAddAlarmFab.hide();
                mAddPersonFab.hide();
                addAlarmActionText.setVisibility(View.GONE);
                addPersonActionText.setVisibility(View.GONE);
                isAllFabsVisible = false;
            }
        });

        mAddPersonFab.setOnClickListener(
                view -> Toast.makeText(MainActivity.this, "Person Added", Toast.LENGTH_SHORT
                ).show());

        mAddAlarmFab.setOnClickListener(
                view -> Toast.makeText(MainActivity.this, "Alarm Added", Toast.LENGTH_SHORT
                ).show());
    }

    // Toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.search_toolbar_btn) {
            return true;
        }
        // TODO

        return super.onOptionsItemSelected(item);
    }

}
        /*
        TextView textView = (TextView) findViewById(R.id.text_view);
        String textFromTextView = textView.getText().toString();
        Log.d(TAG, "textFromTextView orig = [" + textFromTextView + "]");

        textView.setText("Set TextView");
        textFromTextView = textView.getText().toString();
        Log.d(TAG, "textFromTextView set = [" + textFromTextView + "]");

        Button btnAdd = (Button)findViewById(R.id.btnAdd);
        Button btnSub = (Button)findViewById(R.id.btnSubtract);
        Button btnMul = (Button)findViewById(R.id.btnMultiply);
        Button btnDiv = (Button)findViewById(R.id.btnDivide);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = btnAdd.getText().toString();
                Toast.makeText(MainActivity.this, "Clicked " + name, Toast.LENGTH_SHORT).show();
                textView.setText("Last clicked button: " + name);
                btnAdd.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);

                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = btnSub.getText().toString();
                Toast.makeText(MainActivity.this, "Clicked " + name, Toast.LENGTH_SHORT).show();
                textView.setText("Last clicked button: " + name);

                Intent intent = new Intent(MainActivity.this, SubActivity.class);
                startActivity(intent);
            }
        });

        btnMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = btnMul.getText().toString();
                Toast.makeText(MainActivity.this, "Clicked " + name, Toast.LENGTH_SHORT).show();
                textView.setText("Last clicked button: " + name);

                Intent intent = new Intent(MainActivity.this, MulActivity.class);
                startActivity(intent);
            }
        });

        btnDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = btnDiv.getText().toString();
                Toast.makeText(MainActivity.this, "Clicked " + name, Toast.LENGTH_SHORT).show();
                textView.setText("Last clicked button: " + name);

                Intent intent = new Intent(MainActivity.this, DivActivity.class);
                startActivity(intent);
            }
        });

        Log.d(TAG, "onCreate: [FINISHED]");

         */
