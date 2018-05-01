// Copyright © 2018 by Mário Heleno Nazareth Santos - ssannttoss@gmail.com
// All rights reserved. No part of this publication may be reproduced, distributed, or transmitted
// in any form or by any means, including photocopying, recording, or other electronic or mechanical
// methods, without the prior written permission of the publisher
// For permission requests, send an e-mail write to the publisher address ssannttoss@gmail.com

package com.ssannttoss.framework.mvc.controller;

/**
 * A MVC like Controller class to limit the View access to Model Logic and persistence classes.
 * Handlers the view state changes that needs retrieve or save data, perform business requirements validations
 * and controls the application flow.
 * <p>
 * Created by ssannttoss on 1/21/2018.
 */

public interface ControllerExt<T> {
    /**
     * Returns the model shared by controller and view.
     * @return
     */
    T getModel();

    /**
     * Initializes the controller to work with a view.
     */
    void initialize();
}
