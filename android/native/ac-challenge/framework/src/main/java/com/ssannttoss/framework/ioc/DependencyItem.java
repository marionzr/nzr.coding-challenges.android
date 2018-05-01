// Copyright © 2018 by Mário Heleno Nazareth Santos - ssannttoss@gmail.com
// All rights reserved. No part of this publication may be reproduced, distributed, or transmitted
// in any form or by any means, including photocopying, recording, or other electronic or mechanical
// methods, without the prior written permission of the publisher
// For permission requests, send an e-mail write to the publisher address ssannttoss@gmail.com

package com.ssannttoss.framework.ioc;

/**
 * Stores information about interfaces to be managed by dependency injection.
 * <p>
 * Created by ssannttoss on 1/21/2018.
 */
final class DependencyItem {
    private final Class interfaceClass;
    private final Class instanceClass;
    private final DependencyType dependencyType;
    private Object instance;

    DependencyItem(Class interfaceClass, Class instanceClass, DependencyType dependencyType, Object instance) {
        super();
        this.interfaceClass = interfaceClass;
        this.instanceClass = instanceClass;
        this.dependencyType = dependencyType;
        this.instance = instance;
    }

    public Class getInstanceClass() {
        return instanceClass;
    }

    public Class getInterfaceClass() {
        return interfaceClass;
    }

    public DependencyType getType() {
        return dependencyType;
    }

    public Object getInstance() {
        return instance;
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }

    @Override
    public String toString() {
        return String.format("{ interfaceClass: %s, instanceClass: %s, type: %s, instance: %s }", interfaceClass, instanceClass,
                dependencyType, instance);
    }
}
