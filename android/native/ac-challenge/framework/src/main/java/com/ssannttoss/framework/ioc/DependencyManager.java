// Copyright © 2018 by Mário Heleno Nazareth Santos - ssannttoss@gmail.com
// All rights reserved. No part of this publication may be reproduced, distributed, or transmitted
// in any form or by any means, including photocopying, recording, or other electronic or mechanical
// methods, without the prior written permission of the publisher
// For permission requests, send an e-mail write to the publisher address ssannttoss@gmail.com

package com.ssannttoss.framework.ioc;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a Manager that provide a Dependency Injection. In this class, you can register
 * the app object dependency by using {@link DependencyManager#register(Class, Class, DependencyType, Object)}
 * method passing as parameter an Interface Class object and an Implementation Class object. The dependency is
 * registered as an instance of {@code DependencyItem} and it is used to instantiate the object when it is called by
 * {@link DependencyManager#get(Class)} method.
 * <p>
 * Created by ssannttoss on 1/21/2018.
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
     * Register a Transient Dependency of an object by it Interface and Implementation.
     *
     * @param interfaceClass The class object of the Interfaces Dependency.
     * @param instanceClass  The class object of the Implementation Dependency
     * @param <TInterface>   The Interface Type
     * @param <TInstance>    The Implementation Type that need to implements {@code TInterface}
     */
    public synchronized <TInterface, TInstance extends TInterface> void register(Class<TInterface> interfaceClass,
                                                                                 Class<TInstance> instanceClass) {
        register(interfaceClass, instanceClass, DependencyType.TRANSIENT, null);
    }

    /**
     * Register a Dependency of an object by it Interface and Implementation.
     *
     * @param interfaceClass The class object of the Interfaces Dependency.
     * @param instanceClass  The class object of the Implementation Dependency
     * @param type           Which kind of Dependency Type is this. {@code DependencyType.TRANSIENT} or
     *                       {@code DependencyType.SINGLETON}
     * @param <TInterface>   The Interface Type
     * @param <TInstance>    The Implementation Type that need to implements {@code TInterface}
     */
    public synchronized <TInterface, TInstance extends TInterface> void register(Class<TInterface> interfaceClass,
                                                                                 Class<TInstance> instanceClass,
                                                                                 DependencyType type) {
        register(interfaceClass, instanceClass, type, null);
    }

    /**
     * Register an existent object as Dependency of an object by it Interface and Implementation.
     *
     * @param interfaceClass The class object of the Interfaces Dependency.
     * @param instanceClass  The class object of the Implementation Dependency
     * @param type           Which kind of Dependency Type is this. {@code DependencyType.TRANSIENT} or
     *                       {@code DependencyType.SINGLETON}
     * @param instance       The existent object instance
     * @param <TInterface>   The Interface Type
     * @param <TInstance>    The Implementation Type that need to implements {@code TInterface}
     */
    public synchronized <TInterface, TInstance extends TInterface> void register(Class<TInterface> interfaceClass,
                                                                                 Class<TInstance> instanceClass,
                                                                                 DependencyType type, TInstance instance) {
        final DependencyItem item = new DependencyItem(interfaceClass, instanceClass, type, instance);
        dependencyItems.add(item);
    }

    /**
     * Unregister an existent object as Dependency of an object by it Interface class
     *
     * @param interfaceClass The class object of the Interfaces Dependency.
     * @param <TInterface>   The Interface Type
     */
    public synchronized <TInterface> void unregister(final Class<TInterface> interfaceClass) {
        int indexToRemove = -1;
        for (int i = 0; i < dependencyItems.size(); i++) {
            if (dependencyItems.get(i).getInterfaceClass() == interfaceClass) {
                indexToRemove = i;
            }
        }

        if (indexToRemove > -1) {
            dependencyItems.remove(indexToRemove);
        }
    }

    /**
     * Gets the instance of an object that is registered as Dependency by it Interface.
     *
     * @param interfaceClass The class object of the Interfaces Dependency.
     * @param <TInterface>   The Interface Type
     * @param <TInstance>    The Implementation Type that need to implements {@code TInterface}
     * @return The object instance.
     */
    @SuppressWarnings({"unchecked", "TryWithIdenticalCatches"})
    public <TInterface, TInstance extends TInterface> TInstance get(final Class<TInterface> interfaceClass) {
        TInstance instance = null;

        DependencyItem item = null;

        for (DependencyItem dependencyItem : dependencyItems) {
            if (dependencyItem.getInterfaceClass() == interfaceClass) {
                item = dependencyItem;
                break;
            }
        }

        if (item != null) {
            switch (item.getType()) {
                case TRANSIENT:
                    try {
                        instance = (TInstance) item.getInstanceClass().newInstance();
                    } catch (InstantiationException e) {
                        Log.e(TAG, e.getMessage(), e);
                    } catch (IllegalAccessException e) {
                        Log.e(TAG, e.getMessage(), e);
                    }
                    break;
                case SINGLETON:
                    if (item.getInstance() == null) {
                        try {
                            instance = (TInstance) item.getInstanceClass().newInstance();
                            item.setInstance(instance);
                        } catch (InstantiationException e) {
                            Log.e(TAG, e.getMessage(), e);
                        } catch (IllegalAccessException e) {
                            Log.e(TAG, e.getMessage(), e);
                        }
                    } else {
                        instance = (TInstance) item.getInstance();
                    }
                    break;
            }
        } else {
            //LogManager.w(this, "There is no item from " + interfaceClass);
        }

        return instance;
    }

    /**
     * Checks if the {@link DependencyManager} has any instance of the desired interface
     *
     * @param interfaceClass The desired interface
     * @return {@code true} if exists.
     */
    public boolean contains(final Class<?> interfaceClass) {
        for (DependencyItem dependencyItem : dependencyItems) {
            if (dependencyItem.getInterfaceClass() == interfaceClass) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() +
                " - DependencyItems: " + (dependencyItems != null ? dependencyItems.size() : "null");
    }
}
