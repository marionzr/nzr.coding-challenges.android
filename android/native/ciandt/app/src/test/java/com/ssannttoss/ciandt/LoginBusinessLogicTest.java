package com.ssannttoss.ciandt;

import com.ssannttoss.ciandt.mvc.model.business.LoginBusinessLogic;
import com.ssannttoss.ciandt.mvc.model.entity.Login;
import com.ssannttoss.framework.ioc.DependencyManager;

import org.junit.Test;

import java.security.AccessControlException;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class LoginBusinessLogicTest {
    private LoginBusinessLogic logic = new LoginBusinessLogic();

    @Test(expected = AccessControlException.class)
    public void login_withNullableLogin_shouldThrowException() {
        logic.login(null);
    }

    @Test(expected = AccessControlException.class)
    public void login_withEmptyUserNameOrPassword_shouldThrowException() {
        logic.login(new Login());
    }

    @Test
    public void login_withFacebookAccount_shouldReturnFalse() {
        boolean isAsync = logic.login(new Login().withUsername("a").withPassword("b").withAuthority("FACEBOOK"));

        assertFalse(isAsync);
    }

    @Test
    public void processaLogin_withLogin_shouldSetUserNameInDepencyManager() {
        logic.processLogin(new Login().withUsername("a"));

        assertSame(DependencyManager.getInstance().get(Login.class), "a");
    }
}