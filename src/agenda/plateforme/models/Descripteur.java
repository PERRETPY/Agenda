package agenda.plateforme.models;

import java.util.List;

public class Descripteur {
    /** The name. */
    protected String name;

    /** The class name. */
    protected String className;

    /** The auto run. */
    protected boolean autoRun;

    /** The args. */
    protected Class[] args;

    /** The requirements. */
    protected List<String> requirements;

    /** The dependency. */
    protected String dependency;

    /** The loaded. */
    protected boolean loaded;

    /** The interface. */
    protected String interfaceImpl;

    protected boolean defaultPlugin;
    
    protected String position;
    
    protected List<String> pluginIntegrable;
    
    protected String message ="";
    
    protected boolean error;
    
    protected boolean unique;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
    
    public boolean isDefaultPlugin() {
        return defaultPlugin;
    }

    public void setDefaultPlugin(boolean defaultPlugin) {
        this.defaultPlugin = defaultPlugin;
    }
    public boolean isUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public boolean isAutoRun() {
        return autoRun;
    }

    public void setAutoRun(boolean autoRun) {
        this.autoRun = autoRun;
    }

    public Class[] getArgs() {
        return args;
    }

    public void setArgs(Class[] args) {
        this.args = args;
    }

    /**
     * Adds the args.
     *
     * @param argClasses the arg classes
     */
    public void addArgs(List<String> argClasses) {
        args=new Class[argClasses.size()];
        for (int i=0; i<argClasses.size(); i++) {
            try {
                args[i]=Class.forName(argClasses.get(i));
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public List<String> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<String> requirements) {
        this.requirements = requirements;
    }
    
    public List<String> getPluginIntegrable() {
        return pluginIntegrable;
    }

    public void setPluginIntegrable(List<String> pluginIntegrable) {
        this.pluginIntegrable = pluginIntegrable;
    }
    public String getDependency() {
        return dependency;
    }

    public void setDependency(String dependency) {
        this.dependency = dependency;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public String getInterfaceImpl() {
        return interfaceImpl;
    }

    public void setInterfaceImpl(String interfaceImpl) {
        this.interfaceImpl = interfaceImpl;
    }

    public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}
    
}
