package agenda.tiers;

import agenda.application.interfaces.DisplayInterface;

public class TestPlugin implements DisplayInterface, Runnable {
    @Override
    public String test() {
        return "Hello from TestPlugin";
    }

    @Override
    public void run() {

    }
}
