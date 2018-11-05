// Copyright © 2018 by Mário Heleno Nazareth Santos - ssannttoss@gmail.com
// All rights reserved. No part of this publication may be reproduced, distributed, or transmitted
// in any form or by any means, including photocopying, recording, or other electronic or mechanical
// methods, without the prior written permission of the publisher
// For permission requests, send an e-mail write to the publisher address ssannttoss@gmail.com

package com.ssannttoss.framework.ioc;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Stores information about interfaces to be managed by dependency injection.
 * <p>
 * Created by ssannttoss on Nov/03/2018.
 */
final class DependencyItem {
    private final Class keyClass;
    private final Class valueClass;
    private final DependencyType dependencyType;
    private Object instance;

    DependencyItem(Class keyClass, Class valueClass, DependencyType dependencyType, Object instance) {
        super();
        this.keyClass = keyClass;
        this.valueClass = valueClass;
        this.dependencyType = dependencyType;
        this.instance = instance;
    }

    Class getValueClass() {
        return valueClass;
    }

    Class getKeyClass() {
        return keyClass;
    }

    DependencyType getType() {
        return dependencyType;
    }

    Object getInstance() {
        return instance;
    }

    void setInstance(Object instance) {
        this.instance = instance;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(keyClass).append(valueClass).append(dependencyType).append(instance).toString();
    }
}
