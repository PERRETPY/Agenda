package agenda.tiers;

import agenda.application.interfaces.UpdateInterface;

public class TestPlugin2 implements UpdateInterface, Runnable {
    
    public String test() {
        return "Hello from TestPlugin2";
    }

    @Override
    public void run() {

    }
}
