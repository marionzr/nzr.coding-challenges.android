// Copyright © 2018 by Mário Heleno Nazareth Santos - ssannttoss@gmail.com
// All rights reserved. No part of this publication may be reproduced, distributed, or transmitted
// in any form or by any means, including photocopying, recording, or other electronic or mechanical
// methods, without the prior written permission of the publisher
// For permission requests, send an e-mail write to the publisher address ssannttoss@gmail.com

package com.ssannttoss.framework.mvc.controller;

/**
 * A {@link ControllerExt} abstract class with basic helper methods.
 * <p>
 * Created by ssannttoss on 1/21/2018.
 */

public abstract class AbstractControllerExt<T> implements ControllerExt<T> {
    private T model;

    public AbstractControllerExt() {

    }

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }
}
