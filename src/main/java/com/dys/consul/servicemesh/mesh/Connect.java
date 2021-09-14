package com.dys.consul.servicemesh.mesh;

import com.google.gson.annotations.SerializedName;

public class Connect {
    @SerializedName("SidecarService")
    private SidecarService sidecarService;

    /**
     * @return the sidecarService
     */
    public SidecarService getSidecarService() {
        return sidecarService;
    }

    /**
     * @param sidecarService the sidecarService to set
     */
    public void setSidecarService(SidecarService sidecarService) {
        this.sidecarService = sidecarService;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */

    @Override
    public String toString() {
        return "Connect [sidecarService=" + sidecarService + "]";
    }

}
