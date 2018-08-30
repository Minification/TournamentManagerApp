package com.github.thorbenkuck.tm.tournamentmanagement.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.thorbenkuck.tm.tournamentmanagement.R;
import com.github.thorbenkuck.tm.tournamentmanagement.databinding.FragmentLoginBinding;
import com.github.thorbenkuck.tm.tournamentmanagement.mainmenu.MainMenuActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LoginFragment extends Fragment implements LoginContract.View {

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @BindView(R.id.username)
    TextInputLayout usernameWrapper;

    @BindView(R.id.password)
    TextInputLayout passwordWrapper;

    @BindView(R.id.server_name)
    TextInputLayout serverWrapper;

    private LoginContract.Presenter presenter;

    private Unbinder unbinder;

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        unbinder = ButterKnife.bind(this, root);

        FragmentLoginBinding fragmentLoginBinding = DataBindingUtil.bind(root);
        fragmentLoginBinding.setFragment(this);

        return root;
    }

    @Override
    public void showError(String error) {
        //Snackbar.make(usernameWrapper, "", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showUsernamePasswordEmptyError() {
        showErrorMessage(R.string.username_password_empty);
    }

    @Override
    public void showServerUnreachableError() {
        showErrorMessage(R.string.server_unreachable);
    }

    @Override
    public void showMainMenu() {
        Intent intent = new Intent(getContext(), MainMenuActivity.class);
        startActivity(intent);
    }

    @Override
    public void showLoginError() {
        showErrorMessage(R.string.login_failure);
    }

    @Override
    public void showLoginAndRegistrationError() {
        showErrorMessage(R.string.login_and_register_failure);
    }

    private void showErrorMessage(int resource) {
        Snackbar.make(usernameWrapper, getString(resource), Snackbar.LENGTH_LONG).show();
    }

    @OnClick(R.id.login_button)
    public void onLogin(View view) {
        String username = usernameWrapper.getEditText().getText().toString();
        String password = passwordWrapper.getEditText().getText().toString();
        String server = serverWrapper.getEditText().getText().toString();
        presenter.login(username, password, server);
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
