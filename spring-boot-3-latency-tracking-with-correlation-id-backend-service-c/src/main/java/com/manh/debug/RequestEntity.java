package com.manh.debug;

import java.util.ArrayList;
import java.util.List;

public class RequestEntity {
    private boolean xverbose;
    private List<String> debugMessages = new ArrayList<>();

    public boolean isXverbose() {
        return xverbose;
    }

    public void setXverbose(boolean xverbose) {
        this.xverbose = xverbose;
    }

    public List<String> getDebugMessages() {
        return debugMessages;
    }

    public void addDebugMessage(String message) {
        this.debugMessages.add(message);
    }
}
