package com.dys.consul.servicemesh;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryProperties;

@ConfigurationProperties(ConsulDiscoveryProperties.PREFIX)
public class ConsulMeshDiscoveryProperties extends ConsulDiscoveryProperties {

    public ConsulMeshDiscoveryProperties(InetUtils inetUtils) {
        super(inetUtils);
        // TODO Auto-generated constructor stub
    }

    /** 是否启用sidecar. */
    private boolean sidecar = false;

    /** 所依赖的上游微服务标识及端口 */
    private Map<String, Integer> upstream;

    /**
     * @return the sidecar
     */
    public boolean isSidecar() {
        return sidecar;
    }

    /**
     * @param sidecar the sidecar to set
     */
    public void setSidecar(boolean sidecar) {
        this.sidecar = sidecar;
    }

    /**
     * @return the upstream
     */
    public Map<String, Integer> getUpstream() {
        return upstream;
    }

    /**
     * @param upstream the upstream to set
     */
    public void setUpstream(Map<String, Integer> upstream) {
        this.upstream = upstream;
    }

}
