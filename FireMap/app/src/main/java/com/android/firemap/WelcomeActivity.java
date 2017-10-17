package com.android.firemap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WelcomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;
    private TextView username;
    private TextView useremail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        username = (TextView) findViewById(R.id.nameUserTV);
        useremail = (TextView) findViewById(R.id.emailUserTV);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (user == null){
            finish();
            startActivity(new Intent(getBaseContext(), LoginActivity.class));
        }
        username.setText(user.getDisplayName());
        useremail.setText(user.getEmail());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemClicked = item.getItemId();
        if (itemClicked == R.id.menuLogOut) {
            mAuth.signOut();
            startActivity(new Intent(getBaseContext(),
                    LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        } else if (itemClicked == R.id.menuConfiguracion) {
            startActivity(new Intent(getBaseContext(), SettingsActivity.class));
        } else if (itemClicked == R.id.menuNocturno) {
            Toast.makeText(getBaseContext(), "Modo Nocturno", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
