package com.ssannttoss.android_challenge.mvc.model.repository;


import com.ssannttoss.android_challenge.mvc.model.entity.MyAddress;
import com.ssannttoss.framework.persistence.Dao;
import com.ssannttoss.framework.persistence.sqlite.ConstraintException;

import java.util.List;

/**
 * Created by ssannttoss on 1/21/2018.
 */

public interface MyAddressDao extends Dao {
    /**
     * Selects all saved MyAddress objects.
     * @return
     */
    List<MyAddress> selectAll();

    /**
     * Selects all saved MyAddress objects based on a template object.
     * The query must be based on the Id
     * @param template
     * @return
     */
    List<MyAddress> select(MyAddress template);

    /**
     * Inserts a new MyAddress object.
     * @param entity
     * @return
     * @throws ConstraintException
     */
    long insert(MyAddress entity) throws ConstraintException;

    /**
     * Updates a saved MyAddress object.
     * @param entity
     * @return
     * @throws ConstraintException
     */
    int update(MyAddress entity) throws ConstraintException ;

    /**
     * Deletes a saved MyAddress object.
     * @param entity
     * @return
     */
    int delete(MyAddress entity);
}
