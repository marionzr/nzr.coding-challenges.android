package com.ssannttoss.framework.event;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Base event class used with the EventBus
 * @param <T> Type of the data
 * <p>
 * Created by ssannttoss on Nov/03/2018.
 */
public class ChangeEvent<T> {
    private final String action;
    private final T data;
    private final Map<String, Object> extras;

    public ChangeEvent(String action, T data, Map<String, Object> viewBag) {
        this.action = action;
        this.data = data;
        this.extras = new HashMap<>(Optional.ofNullable(viewBag).orElse(Collections.emptyMap()));
    }

    public String getAction() {
        return action;
    }

    public T getData() {
        return data;
    }

    public Map<String, Object> getExtras() {
        return Collections.unmodifiableMap(extras);
    }
}
