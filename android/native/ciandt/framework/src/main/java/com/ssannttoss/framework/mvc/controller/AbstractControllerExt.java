// Copyright © 2018 by Mário Heleno Nazareth Santos - ssannttoss@gmail.com
// All rights reserved. No part of this publication may be reproduced, distributed, or transmitted
// in any form or by any means, including photocopying, recording, or other electronic or mechanical
// methods, without the prior written permission of the publisher
// For permission requests, send an e-mail write to the publisher address ssannttoss@gmail.com

package com.ssannttoss.framework.mvc.controller;

import com.ssannttoss.framework.mvc.view.ViewExt;

import java.util.Map;

/**
 * A {@link ControllerExt} abstract class with basic helper methods.
 * <p>
 * Created by ssannttoss on Nov/03/2018.
 */
public abstract class AbstractControllerExt<T> implements ControllerExt<T> {
    protected ViewExt viewExt;

    public AbstractControllerExt() {
    }

    @Override
    public void attach(ViewExt viewExt) {
        this.viewExt = viewExt;
    }

    @Override
    public void detach(ViewExt viewExt) {
        if (this.viewExt == viewExt) {
            this.viewExt = null;
        }
    }

    @Override
    public void viewStateChanged(String action, T model, Map<String, Object> extras, ResponseListener<T> responseListener) {
        onViewStateChanged(action, model, extras, responseListener);
    }

    protected abstract void onViewStateChanged(String action, T model, Map<String, Object> extras, ResponseListener<T> responseListener);
}
