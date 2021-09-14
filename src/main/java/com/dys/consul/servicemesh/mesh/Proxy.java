package com.dys.consul.servicemesh.mesh;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Proxy {
    @SerializedName("Upstreams")
    private List<Upstream> upstreams;

    /**
     * @return the upstreams
     */
    public List<Upstream> getUpstreams() {
        return upstreams;
    }

    /**
     * @param upstreams the upstreams to set
     */
    public void setUpstreams(List<Upstream> upstreams) {
        this.upstreams = upstreams;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */

    @Override
    public String toString() {
        return "Proxy [upstreams=" + upstreams + "]";
    }

}
