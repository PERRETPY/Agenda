package agenda.tiers;


import agenda.application.interfaces.UpdateInterface;

public class TestPlugin implements UpdateInterface {

    public TestPlugin() {

    }

    
    public String test() {
        return "Hello from TestPlugin";
    }
}
