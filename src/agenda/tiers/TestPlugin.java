package agenda.tiers;

import agenda.application.interfaces.DisplayInterface;

public class TestPlugin implements DisplayInterface {

    public TestPlugin() {

    }

    @Override
    public String test() {
        return "Hello from TestPlugin";
    }
}
