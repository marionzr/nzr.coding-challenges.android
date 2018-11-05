// Copyright © 2018 by Mário Heleno Nazareth Santos - ssannttoss@gmail.com
// All rights reserved. No part of this publication may be reproduced, distributed, or transmitted
// in any form or by any means, including photocopying, recording, or other electronic or mechanical
// methods, without the prior written permission of the publisher
// For permission requests, send an e-mail write to the publisher address ssannttoss@gmail.com

package com.ssannttoss.framework.mvc.controller;

import com.ssannttoss.framework.mvc.view.ViewExt;

import java.util.Map;

/**
 * A MVC like Controller class to limit the View access to Model Logic and persistence classes.
 * Handlers the view state changes that needs retrieve or save data, perform business requirements validations
 * and controls the application flow.
 * <p>
 * Created by ssannttoss on Nov/03/2018.
 */

public interface ControllerExt<T> {
    /**
     * Listener used send to the view synchronous requests
     * @param <T> Type of model
     */
    interface ResponseListener<T> {
        void onResponse(Exception error, T model, Map<String, Object> extras);
    }

    /**
     * Sets a viewExt instance in the controller to used to trigger navigation (transitions)
     * or notify about asynchronous requests
     * @param viewExt the current viewExt
     */
    void attach(ViewExt viewExt);

    /**
     * Detach a view from the controller. Can be used to clean-up operations
     * @param viewExt
     */
    void detach(ViewExt viewExt);

    /**
     * Notifies the controller that the view state was changed or a request has been made by the view
     * @param action The action to be performed
     * @param model The object used as a model for this request
     * @param extras Extra information not present in the Model attributes
     * @param responseListener A response listener for synchronous operations
     */
    void viewStateChanged(String action, T model, Map<String, Object> extras, ResponseListener<T> responseListener);
}
