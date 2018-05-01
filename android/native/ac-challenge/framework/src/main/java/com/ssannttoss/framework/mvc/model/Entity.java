// Copyright © 2018 by Mário Heleno Nazareth Santos - ssannttoss@gmail.com
// All rights reserved. No part of this publication may be reproduced, distributed, or transmitted
// in any form or by any means, including photocopying, recording, or other electronic or mechanical
// methods, without the prior written permission of the publisher
// For permission requests, send an e-mail write to the publisher address ssannttoss@gmail.com

package com.ssannttoss.framework.mvc.model;

import android.os.Parcel;

/**
 * Base class for non UI entities.
 * <p>
 * Created by ssannttoss on 1/21/2018.
 */

public abstract class Entity<T> implements Cloneable {
    private long id;

    public Entity() {

    }

    public Entity(long id) {
        this();
        this.id = id;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
    }

    public void readFromParcel(Parcel in) {
        id = in.readLong();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public abstract T clone();
}
