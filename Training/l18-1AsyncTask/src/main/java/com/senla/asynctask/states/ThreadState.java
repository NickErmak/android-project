package com.senla.asynctask.states;

import java.util.ArrayList;
import java.util.List;

public class ThreadState {
    private volatile boolean shouldExit;
    private final Object lock = new Object();
    private volatile List<String> messages;
    private volatile List<String> messagesBuffer;

    public ThreadState() {
        messages = new ArrayList<>();
        messagesBuffer = new ArrayList<>();
        shouldExit = true;
    }

    public void startNewSession() {
        shouldExit = false;
        messages.clear();
        messagesBuffer.clear();
    }

    public boolean isShouldExit() {
        return shouldExit;
    }

    public void setShouldExit(boolean shouldExit) {
        this.shouldExit = shouldExit;
    }

    public synchronized void appendMessage(String message) {
        messages.add(message);
        messagesBuffer.add(message);
    }

    public synchronized List<String> pullOutMessages() {
        List<String> pulledMessages = new ArrayList<>(messagesBuffer);
        messagesBuffer.clear();
        return pulledMessages;
    }

    public List<String> getMessages() {
        return messages;
    }

    public Object getLock() {
        return lock;
    }
}
