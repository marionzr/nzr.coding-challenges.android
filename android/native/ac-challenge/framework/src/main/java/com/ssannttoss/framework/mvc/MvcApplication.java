// Copyright © 2018 by Mário Heleno Nazareth Santos - ssannttoss@gmail.com
// All rights reserved. No part of this publication may be reproduced, distributed, or transmitted
// in any form or by any means, including photocopying, recording, or other electronic or mechanical
// methods, without the prior written permission of the publisher
// For permission requests, send an e-mail write to the publisher address ssannttoss@gmail.com

package com.ssannttoss.framework.mvc;

import android.app.Application;
import android.content.Context;

import com.ssannttoss.framework.ioc.DependencyManager;
import com.ssannttoss.framework.ioc.DependencyType;

/**
 * Application entry point. Registry dependencies here.
 * Created by ssannttoss on 1/21/2018.
 */

public abstract class MvcApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        final DependencyManager manager = DependencyManager.getInstance();
        manager.register(Context.class, MvcApplication.class, DependencyType.SINGLETON, this);
    }
}
