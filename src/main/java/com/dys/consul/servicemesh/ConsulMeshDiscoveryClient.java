package com.dys.consul.servicemesh;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.QueryParams;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryClient;
import org.springframework.cloud.consul.discovery.ConsulServiceInstance;

public class ConsulMeshDiscoveryClient extends ConsulDiscoveryClient {
    private final ConsulMeshDiscoveryProperties meshProperties;
    private Map<String, ServiceInstance> upstreamServices = new HashMap<>();

    public ConsulMeshDiscoveryClient(ConsulClient client, ConsulMeshDiscoveryProperties properties) {
        super(client, properties);
        meshProperties = properties;
        if (meshProperties.isSidecar()) {
            properties.getUpstream().forEach((k, v) -> {
                ServiceInstance serviceInstance = new ConsulServiceInstance(k, k, "127.0.0.1", v, false);
                upstreamServices.put(k, serviceInstance);
            });
        }
    }

    @Override
    public List<ServiceInstance> getAllInstances() {

        if (meshProperties.isSidecar()) {
            return upstreamServices.values().stream().collect(Collectors.toList());
        } else {
            return super.getAllInstances();
        }

    }

    @Override
    public List<ServiceInstance> getInstances(String serviceId) {
        if (meshProperties.isSidecar()) {
            return Arrays.asList(upstreamServices.get(serviceId));
        }
        return super.getInstances(serviceId);
    }

    @Override
    public List<ServiceInstance> getInstances(String serviceId, QueryParams queryParams) {
        if (meshProperties.isSidecar()) {
            return Arrays.asList(upstreamServices.get(serviceId));
        }
        return super.getInstances(serviceId, queryParams);
    }

}
