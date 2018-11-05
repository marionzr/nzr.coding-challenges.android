package com.ssannttoss.ciandt.mvc.view;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ssannttoss.ciandt.BuildConfig;
import com.ssannttoss.ciandt.R;
import com.ssannttoss.ciandt.mvc.model.entity.Login;
import com.ssannttoss.framework.mvc.CommonActions;
import com.ssannttoss.framework.mvc.view.AbstractFragmentViewExt;

public class LoginFragView extends AbstractFragmentViewExt<Login> {
    private static final String FACEBOOK = "FACEBOOK";
    private EditText edtEmail;
    private EditText edtPassword;
    public LoginFragView() {
        super(BuildConfig.APPLICATION_ID);
    }

    public static LoginFragView newInstance() {
        LoginFragView fragment = new LoginFragView();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.login_frag_view;
    }

    @Override
    protected void onFinishCreateView(View view) {
        super.onFinishCreateView(view);

        edtEmail = view.findViewById(R.id.edtEmail);
        edtPassword = view.findViewById(R.id.edtPassword);
        view.findViewById(R.id.btnLogin).setOnClickListener(this::onBntLoginClick);
        view.findViewById(R.id.btnGuest).setOnClickListener(this::onBntGuestClick);
    }

    private void onBntGuestClick(View view) {
        hideKeyboard();

        if(checkNetwork(view, this::onBntGuestClick)) {
            onViewStateChanged(CommonActions.LOGIN, null, extras, (error, model1, extras) -> {
                if (error != null) {
                    showException(R.string.unable_to_authenticate, error);
                }
            });
        }
    }

    private void onBntLoginClick(View view) {
        hideKeyboard();

        if (checkNetwork(view, this::onBntLoginClick)) {
            Login model = new Login().withUsername(edtEmail.getText().toString()).withPassword(edtPassword.getText().toString()).withAuthority(FACEBOOK);
            onViewStateChanged(CommonActions.LOGIN, model, extras, (error, model1, extras) -> {
                if (error != null) {
                    showException(R.string.unable_to_authenticate, error);
                }
            });
        }
    }
}
