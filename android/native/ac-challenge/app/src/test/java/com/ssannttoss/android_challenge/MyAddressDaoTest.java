package com.ssannttoss.android_challenge;

import com.ssannttoss.android_challenge.mvc.model.entity.MyAddress;
import com.ssannttoss.android_challenge.mvc.model.repository.MyAddressDao;
import com.ssannttoss.android_challenge.mvc.model.repository.mock.MyAddressDaoMock;
import com.ssannttoss.android_challenge.mvc.model.repository.sqlite.MyAddressSqliteDao;
import com.ssannttoss.framework.ioc.DependencyManager;
import com.ssannttoss.framework.ioc.DependencyType;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class MyAddressDaoTest {
    @Test
    public void check_dependecy_manager() throws Exception {
        DependencyManager.getInstance().unregister(MyAddressDao.class);
        DependencyManager.getInstance().register(MyAddressDao.class, MyAddressSqliteDao.class, DependencyType.SINGLETON);
        MyAddressDao myAddressDao = DependencyManager.getInstance().get(MyAddressDao.class);
        assertNotNull("Invalid MyAdressDao implementation", myAddressDao);
        assertTrue("Invalid MyAdressDao type implementation", myAddressDao.getClass().isAssignableFrom(MyAddressSqliteDao.class));
    }

    @Test
    public void check_select_all() throws Exception {
        DependencyManager.getInstance().unregister(MyAddressDao.class);
        DependencyManager.getInstance().register(MyAddressDao.class, MyAddressDaoMock.class, DependencyType.SINGLETON);
        MyAddressDao myAddressDao = DependencyManager.getInstance().get(MyAddressDao.class);
        List<MyAddress> myAddressList = myAddressDao.selectAll();
        assertNotNull("The selectedAll returned a null list", myAddressList);
    }

    @Test
    public void insert_test() throws Exception {
        DependencyManager.getInstance().unregister(MyAddressDao.class);
        DependencyManager.getInstance().register(MyAddressDao.class, MyAddressDaoMock.class, DependencyType.SINGLETON);
        MyAddressDao myAddressDao = DependencyManager.getInstance().get(MyAddressDao.class);
        List<MyAddress> myAddressList = myAddressDao.selectAll();

        int sizeBeforeInsert = myAddressList.size();
        MyAddress myAddress = new MyAddress(-19.938614, -43.9358117, "R. Antônio de Albuquerque, 330 - Funcionários, Belo Horizonte - MG, 30130-151");
        long result = myAddressDao.insert(myAddress);
        myAddressList = myAddressDao.selectAll();
        assertTrue("The insert result is invalid", result > 0);
        assertEquals("The number of selected elements is not the expected after a insert",sizeBeforeInsert + 1, myAddressList.size());
        boolean foundInList = false;
        for(MyAddress myAddressInList : myAddressList) {
            if (myAddressInList.getId() == myAddress.getId()) {
                foundInList = true;
                break;
            }
        }
        assertTrue("MyAddress was not found in the select result", foundInList);
    }
}