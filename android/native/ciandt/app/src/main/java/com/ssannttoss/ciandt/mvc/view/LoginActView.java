package com.ssannttoss.ciandt.mvc.view;

import com.ssannttoss.ciandt.R;
import com.ssannttoss.ciandt.mvc.model.entity.Login;
import com.ssannttoss.framework.mvc.view.AbstractActViewExt;

public class LoginActView extends AbstractActViewExt<Login> {
    @Override
    public int getCustomContainerId() {
        return R.id.frm_content;
    }

    @Override
    public int getLayoutContentId() {
        return R.layout.login_view;
    }

    @Override
    protected void onAddFragments() {
        addFragment(LoginFragView.newInstance());
    }
}
