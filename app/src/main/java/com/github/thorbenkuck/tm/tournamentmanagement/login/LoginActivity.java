package com.github.thorbenkuck.tm.tournamentmanagement.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.thorbenkuck.tm.tournamentmanagement.R;
import com.github.thorbenkuck.tm.tournamentmanagement.util.ActivityUtils;

import org.greenrobot.eventbus.EventBus;

public class LoginActivity extends AppCompatActivity {

    private LoginContract.Presenter presenter;

    private EventBus eventBus = EventBus.getDefault();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if(loginFragment == null) {
            loginFragment = LoginFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), loginFragment, R.id.contentFrame);
        }

        presenter = new LoginPresenter(loginFragment, eventBus);
    }

}
