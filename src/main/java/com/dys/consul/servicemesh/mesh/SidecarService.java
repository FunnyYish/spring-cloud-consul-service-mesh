package com.dys.consul.servicemesh.mesh;

import com.google.gson.annotations.SerializedName;

public class SidecarService {
    @SerializedName("Proxy")
    private Proxy proxy;

    /**
     * @return the proxy
     */
    public Proxy getProxy() {
        return proxy;
    }

    /**
     * @param proxy the proxy to set
     */
    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */

    @Override
    public String toString() {
        return "SidecarService [proxy=" + proxy + "]";
    }

}
