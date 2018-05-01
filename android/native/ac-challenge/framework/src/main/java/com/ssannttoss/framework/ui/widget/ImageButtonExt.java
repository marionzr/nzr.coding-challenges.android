// Copyright © 2018 by Mário Heleno Nazareth Santos - ssannttoss@gmail.com
// All rights reserved. No part of this publication may be reproduced, distributed, or transmitted
// in any form or by any means, including photocopying, recording, or other electronic or mechanical
// methods, without the prior written permission of the publisher
// For permission requests, send an e-mail write to the publisher address ssannttoss@gmail.com

package com.ssannttoss.framework.ui.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatImageButton;
import android.util.AttributeSet;

/**
 * A {@link AppCompatImageButton} which supports compatible features on older versions of the platform
 * and also includes extended features.
 * <p>
 * Created by ssannttoss on 1/21/2018.
 */

public class ImageButtonExt extends AppCompatImageButton {
    public ImageButtonExt(Context context) {
        super(context);
    }

    public ImageButtonExt(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageButtonExt(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
