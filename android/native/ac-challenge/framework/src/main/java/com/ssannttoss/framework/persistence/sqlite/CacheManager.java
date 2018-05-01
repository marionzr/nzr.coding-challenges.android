// Copyright © 2018 by Mário Heleno Nazareth Santos - ssannttoss@gmail.com
// All rights reserved. No part of this publication may be reproduced, distributed, or transmitted
// in any form or by any means, including photocopying, recording, or other electronic or mechanical
// methods, without the prior written permission of the publisher
// For permission requests, send an e-mail write to the publisher address ssannttoss@gmail.com

package com.ssannttoss.framework.persistence.sqlite;

import com.ssannttoss.framework.logging.LogManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Helps to reduce IO keeping some objects in memory.
 * <p>
 * Created by ssannttoss on 1/21/2018.
 */

public class CacheManager<T> {
    private List<T> cache;
    private boolean enabled;

    public CacheManager() {
        cache = new ArrayList<>();
    }

    public void add(T item) {
        if (!isEnabled()) {
            return;
        }

        if (cache.contains(item)) {
            LogManager.w(this, "add. Item found: " + item.toString());
        }

        cache.add(item);
    }

    public void remove(T item) {
        if (!isEnabled()) {
            return;
        }

        if (!cache.contains(item)) {
            LogManager.w(this, "remove. Item not found: " + item.toString());
        }

        cache.remove(item);
    }

    public void clear() {
        cache.clear();
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
