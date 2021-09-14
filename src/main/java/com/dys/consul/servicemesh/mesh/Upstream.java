package com.dys.consul.servicemesh.mesh;

import com.google.gson.annotations.SerializedName;

public class Upstream {
    @SerializedName("DestinationName")
    private String destinationName;
    @SerializedName("LocalBindPort")
    private int localBindPort;

    /**
     * @return the destinationName
     */
    public String getDestinationName() {
        return destinationName;
    }

    /**
     * @param destinationName the destinationName to set
     */
    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    /**
     * @return the localBindPort
     */
    public int getLocalBindPort() {
        return localBindPort;
    }

    /**
     * @param localBindPort the localBindPort to set
     */
    public void setLocalBindPort(int localBindPort) {
        this.localBindPort = localBindPort;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */

    @Override
    public String toString() {
        return "Upstream [destinationName=" + destinationName + ", localBindPort=" + localBindPort + "]";
    }

}
