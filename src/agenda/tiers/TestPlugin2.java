package agenda.tiers;

import agenda.application.interfaces.DisplayInterface;

public class TestPlugin2 implements DisplayInterface, Runnable {
    @Override
    public String test() {
        return "Hello from TestPlugin2";
    }

    @Override
    public void run() {

    }
}
