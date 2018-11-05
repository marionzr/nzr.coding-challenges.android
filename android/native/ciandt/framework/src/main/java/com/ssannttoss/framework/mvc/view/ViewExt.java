// Copyright © 2018 by Mário Heleno Nazareth Santos - ssannttoss@gmail.com
// All rights reserved. No part of this publication may be reproduced, distributed, or transmitted
// in any form or by any means, including photocopying, recording, or other electronic or mechanical
// methods, without the prior written permission of the publisher
// For permission requests, send an e-mail write to the publisher address ssannttoss@gmail.com

package com.ssannttoss.framework.mvc.view;

import com.ssannttoss.framework.mvc.controller.ControllerExt;

import java.util.Map;

public interface ViewExt<T> {
    /**
     * Notify the view about an update that needs to be performed after an asynchronous
     * request has finished.
     * @param action The action to be performed
     * @param model The object used as a model for this request
     * @param extras Extra information not present in the Model attributes
     */
    void updateView(String action, T model, Map<String, Object> extras);

    /**
     * Starts a navigation (transition) from one view to another.
     * It is started by the Controller in order to activate another controller.
     * @param controllerClass The next controller to be activated
     * @param extras Extra information to be passed in the navigation process.
     */
    void navigate(Class<? extends ControllerExt> controllerClass, Map<String, Object> extras);

    /**
     * A short-cut to display exceptions messages in the view
     * @param message A message
     * @param e The exception
     */
    void showException(String message, Exception e);
}
