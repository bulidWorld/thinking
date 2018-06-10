package org.zwx.thinking.cluster.multiple_operate;

public class RunAction implements Runnable {

    private Action action;

    private String body;

    public RunAction(Action action, String body) {
        this.action = action;
        this.body = body;
    }

    @Override
    public void run() {
        action.actBody(body);

    }
}
