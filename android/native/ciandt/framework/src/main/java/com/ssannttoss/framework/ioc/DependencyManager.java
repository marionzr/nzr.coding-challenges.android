// Copyright © 2018 by Mário Heleno Nazareth Santos - ssannttoss@gmail.com
// All rights reserved. No part of this publication may be reproduced, distributed, or transmitted
// in any form or by any means, including photocopying, recording, or other electronic or mechanical
// methods, without the prior written permission of the publisher
// For permission requests, send an e-mail write to the publisher address ssannttoss@gmail.com

package com.ssannttoss.framework.ioc;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * This class is a Manager that provide a Dependency Injection. In this class, you can register
 * the app object dependency by using {@link DependencyManager#register(Class, Class, DependencyType, Object)}
 * method passing as parameter an Interface Class object and an Implementation Class object. The dependency is
 * registered as an instance of {@code DependencyItem} and it is used to instantiate the object when it is called by
 * {@link DependencyManager#get(Class)} method.
 * <p>
 * Created by ssannttoss on Nov/03/2018.
 *
 * @see DependencyItem
 * @see DependencyType
 */
public final class DependencyManager {
    /**
     * Logcat TAG
     */
    private static final String TAG = DependencyManager.class.getSimpleName();
    /**
     * Singleton instance of this class
     */
    private static DependencyManager instance;
    /**
     * List of Dependencies that was registered in this application
     */
    private final List<DependencyItem> dependencyItems;

    private DependencyManager() {
        super();
        dependencyItems = new ArrayList<>();
    }

    /**
     * @return Singleton Instance of this class.
     */
    public static DependencyManager getInstance() {
        synchronized (DependencyManager.class) {
            if (instance == null) {
                instance = new DependencyManager();
            }

            return instance;
        }
    }

    /**
     * Register an existent object as Dependency of an object by it Interface and Implementation.
     *
     * @param keyClass The class object of the Interfaces Dependency.
     * @param valueClass  The class object of the Implementation Dependency
     * @param type           Which kind of Dependency Type is this. {@code DependencyType.TRANSIENT} or
     *                       {@code DependencyType.SINGLETON}
     * @param instance       The existent object instance
     * @param <TInterface>   The Interface Type
     * @param <TInstance>    The Implementation Type that need to implements {@code KeyClass}
     */
    public synchronized <TInterface, TInstance> void register(Class<TInterface> keyClass,
                                                                                 Class<TInstance> valueClass,
                                                                                 DependencyType type, TInstance instance) {
        final DependencyItem item = new DependencyItem(keyClass, valueClass, type, instance);
        dependencyItems.add(item);
    }

    /**
     * Unregister an existent object as Dependency of an object by it Interface class
     *
     * @param keyClass The class object of the Interfaces Dependency.
     * @param <TInterface>   The Interface Type
     */
    public synchronized <TInterface> void unregister(final Class<TInterface> keyClass) {
        dependencyItems.removeIf(i -> i.getKeyClass().equals(keyClass));
    }

    /**
     * Gets the instance of an object that is registered as Dependency by it Interface.
     *
     * @param keyClass The key class type
     * @param <KeyClass>   The Interface Type
     * @param <InstanceValue>    The Implementation Type that need to implements {@code KeyClass}
     * @return The object instance.
     */
    @SuppressWarnings({"unchecked", "TryWithIdenticalCatches"})
    public <KeyClass, InstanceValue> InstanceValue get(Class<KeyClass> keyClass) {
        InstanceValue instance = null;

        Optional<DependencyItem> optItem = dependencyItems
                .stream()
                .filter(kv -> kv.getKeyClass().equals(keyClass))
                .findFirst();

        if (optItem.isPresent()) {
            DependencyItem item = optItem.get();

            switch (item.getType()) {
                case TRANSIENT:
                    try {
                        instance = (InstanceValue) item.getValueClass().newInstance();
                    } catch (InstantiationException | IllegalAccessException e) {
                        Log.e(TAG, e.getMessage(), e);
                    }
                    break;
                case SINGLETON:
                    if (item.getInstance() == null) {
                        try {
                            instance = (InstanceValue) item.getValueClass().newInstance();
                            item.setInstance(instance);
                        } catch (InstantiationException | IllegalAccessException e) {
                            Log.e(TAG, e.getMessage(), e);
                        }
                    } else {
                        instance = (InstanceValue) item.getInstance();
                    }
                    break;
            }
        }

        return instance;
    }

    /**
     * Gets the instance of an object that is registered as Dependency by it Interface.
     *
     * @param valueClass The value class type
     * @return The object instance.
     */
    @SuppressWarnings({"unchecked", "TryWithIdenticalCatches"})
    public Class<?> getReversed(final Class<?> valueClass) {

        return dependencyItems
                .stream()
                .filter(kv -> kv.getValueClass().equals(valueClass))
                .map(DependencyItem::getKeyClass)
                .findFirst()
                .orElse(null);
    }
}
