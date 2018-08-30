package com.github.thorbenkuck.tm.tournamentmanagement.views.activities;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.github.thorbenkuck.netcom2.network.shared.comm.model.RegisterResponse;
import com.github.thorbenkuck.tm.shared.user.LoginResponse;
import com.github.thorbenkuck.tm.tournamentmanagement.R;
import com.github.thorbenkuck.tm.tournamentmanagement.network.Network;
import com.github.thorbenkuck.tm.tournamentmanagement.views.LoginView;

import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends AppCompatActivity implements LoginView {

    private TextInputLayout usernameWrapper;
    private TextInputLayout passwordWrapper;
    private TextInputLayout serverWrapper;
    private Network network = Network.getInstance();
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameWrapper = findViewById(R.id.username);
        passwordWrapper = findViewById(R.id.password);
        serverWrapper = findViewById(R.id.server_name);
        network.register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        network.unregister(this);
    }

    public void login(View view) {

        username = usernameWrapper.getEditText().getText().toString();
        password = passwordWrapper.getEditText().getText().toString();
        String server = serverWrapper.getEditText().getText().toString();

        boolean success = network.prepare(server);
        if (!success) {
            Toast.makeText(this, "Server nicht erreichbar.", Toast.LENGTH_LONG).show();
            return;
        }
        network.login(username, password);
    }

    @Override
    public void changeView(Class<? extends com.github.thorbenkuck.tm.tournamentmanagement.views.View> clazz) {

    }

    @Subscribe
    public void loginResponse(LoginResponse loginResponse) {
        boolean meh = loginResponse.isLoginOkay();
        runOnUiThread(() -> {
            if (meh) {
                startActivity(new Intent(this, MainMenuActivity.class));
            } else {
                Toast.makeText(MainActivity.this, "Da ist wohl was schief gelaufen. Registrierungsversuch.", Toast.LENGTH_LONG).show();
                network.register(username, password);
            }
        });
    }

    @Subscribe
    public void registerResponse(RegisterResponse registerResponse) {
        boolean meh = registerResponse.isOkay();
        runOnUiThread(() -> {
            if (meh) {
                startActivity(new Intent(this, MainMenuActivity.class));
            } else {
                Toast.makeText(MainActivity.this, "Da ist wohl beim Registrieren was schief gelaufen.", Toast.LENGTH_LONG).show();
            }
        });
    }

}
