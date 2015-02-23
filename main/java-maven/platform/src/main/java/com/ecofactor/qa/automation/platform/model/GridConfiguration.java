package com.ecofactor.qa.automation.platform.model;

/**
 * The Class GridConfiguration.
 * This model describes the configuration of the node. This configuration will be used
 * by the hub to use the node appropriately.
 * 
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class GridConfiguration {

    private int cleanUpCycle;
    private int timeout;
    private String proxy;
    private String url;
    private int maxSession;
    private int port;
    private String host;
    private boolean register;
    private int registerCycle;
    private int hubPort;
    private String hubHost;

    /**
     * Gets the clean up cycle.
     * @return the clean up cycle
     */
    public int getCleanUpCycle() {

        return cleanUpCycle;
    }

    /**
     * Sets the clean up cycle.
     * @param cleanUpCycle the new clean up cycle
     */
    public void setCleanUpCycle(final int cleanUpCycle) {

        this.cleanUpCycle = cleanUpCycle;
    }

    /**
     * Gets the timeout.
     * @return the timeout
     */
    public int getTimeout() {

        return timeout;
    }

    /**
     * Sets the timeout.
     * @param timeout the new timeout
     */
    public void setTimeout(final int timeout) {

        this.timeout = timeout;
    }

    /**
     * Gets the proxy.
     * @return the proxy
     */
    public String getProxy() {

        return proxy;
    }

    /**
     * Sets the proxy.
     * @param proxy the new proxy
     */
    public void setProxy(final String proxy) {

        this.proxy = proxy;
    }

    /**
     * Gets the url.
     * @return the url
     */
    public String getUrl() {

        return url;
    }

    /**
     * Sets the url.
     * @param url the new url
     */
    public void setUrl(final String url) {

        this.url = url;
    }

    /**
     * Gets the max session.
     * @return the max session
     */
    public int getMaxSession() {

        return maxSession;
    }

    /**
     * Sets the max session.
     * @param maxSession the new max session
     */
    public void setMaxSession(final int maxSession) {

        this.maxSession = maxSession;
    }

    /**
     * Gets the port.
     * @return the port
     */
    public int getPort() {

        return port;
    }

    /**
     * Sets the port.
     * @param port the new port
     */
    public void setPort(final int port) {

        this.port = port;
    }

    /**
     * Gets the host.
     * @return the host
     */
    public String getHost() {

        return host;
    }

    /**
     * Sets the host.
     * @param host the new host
     */
    public void setHost(final String host) {

        this.host = host;
    }

    /**
     * Checks if is register.
     * @return true, if is register
     */
    public boolean isRegister() {

        return register;
    }

    /**
     * Sets the register.
     * @param register the new register
     */
    public void setRegister(final boolean register) {

        this.register = register;
    }

    /**
     * Gets the register cycle.
     * @return the register cycle
     */
    public int getRegisterCycle() {

        return registerCycle;
    }

    /**
     * Sets the register cycle.
     * @param registerCycle the new register cycle
     */
    public void setRegisterCycle(final int registerCycle) {

        this.registerCycle = registerCycle;
    }

    /**
     * Gets the hub port.
     * @return the hub port
     */
    public int getHubPort() {

        return hubPort;
    }

    /**
     * Sets the hub port.
     * @param hubPort the new hub port
     */
    public void setHubPort(final int hubPort) {

        this.hubPort = hubPort;
    }

    /**
     * Gets the hub host.
     * @return the hub host
     */
    public String getHubHost() {

        return hubHost;
    }

    /**
     * Sets the hub host.
     * @param hubHost the new hub host
     */
    public void setHubHost(final String hubHost) {

        this.hubHost = hubHost;
    }
}
