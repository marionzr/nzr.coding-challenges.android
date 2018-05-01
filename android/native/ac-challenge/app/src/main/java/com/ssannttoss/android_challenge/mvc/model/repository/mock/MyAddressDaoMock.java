package com.ssannttoss.android_challenge.mvc.model.repository.mock;

import com.ssannttoss.android_challenge.mvc.model.entity.MyAddress;
import com.ssannttoss.android_challenge.mvc.model.repository.MyAddressDao;
import com.ssannttoss.framework.persistence.sqlite.ConstraintException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ssannttoss on 1/21/2018.
 */

public class MyAddressDaoMock implements MyAddressDao {
    private static List<MyAddress> memoryTable;
    private final String name = this.getClass().getSimpleName();

    public MyAddressDaoMock() {
        createTable();
    }

    @Override
    public String getTableName() {
        return name;
    }

    private void createTable() {
        if (memoryTable == null) {
            memoryTable = new ArrayList<>();
        }
    }


    @Override
    public List<MyAddress> selectAll() {
        List<MyAddress> result = new ArrayList<>(memoryTable.size());

        for (MyAddress myAddress : memoryTable) {
            result.add(myAddress.clone());
        }

        return result;
    }

    @Override
    public List<MyAddress> select(MyAddress template) {
        List<MyAddress> result = new ArrayList<>();

        for (MyAddress myAddress : memoryTable) {
            if (template.getId() != 0 && myAddress.getId() == template.getId() ||
                    template.getId() == 0 && myAddress.getFeatureName().equals(template.getFeatureName())) {
                result.add(myAddress.clone());
            }
        }

        return result;
    }

    @Override
    public long insert(MyAddress entity) throws ConstraintException {
        checkConstraints(entity);

        if (entity.getId() != 0) {
            for (MyAddress myAddress : memoryTable) {
                if (myAddress.getId() == entity.getId()) {
                    throw new ConstraintException(getTableName(), "feature_name", entity.getId(), "(1555) Constraint failed: id must be unique");
                }
            }
        }

        long nextId = selectNextId();
        entity.setId(nextId);
        memoryTable.add(entity.clone());
        return nextId;
    }

    @Override
    public int update(MyAddress entity) throws ConstraintException {
        checkConstraints(entity);
        int rowsAffected = 0;

        for (int i = 0; i < memoryTable.size(); i++) {
            MyAddress myAddress = memoryTable.get(i);
            if (myAddress.getId() == entity.getId()) {
                memoryTable.set(i, entity.clone());
                rowsAffected = 1;
                break;
            }
        }

        return rowsAffected;
    }

    @Override
    public int delete(MyAddress entity) {
        MyAddress myAddressToDelete = null;

        for (MyAddress myAddress : memoryTable) {
            if (myAddress.getId() == entity.getId()) {
                myAddressToDelete = myAddress;
                break;
            }
        }

        memoryTable.remove(myAddressToDelete);
        return myAddressToDelete != null ? 1 : 0;
    }

    private void checkConstraints(MyAddress myAddress) {
        if (myAddress.getFeatureName() == null) {
            throw new ConstraintException(getTableName(), "feature_name", null, "(1299) Constraint failed: feature_name cannot be null");
        }
    }

    private long selectNextId() {
        if (memoryTable.size() == 0) {
            return 1;
        } else {
            return memoryTable.get(memoryTable.size() - 1).getId() + 1;
        }
    }
}
