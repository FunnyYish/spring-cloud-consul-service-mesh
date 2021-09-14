package com.dys.consul.servicemesh;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.dys.consul.servicemesh.mesh.Connect;
import com.dys.consul.servicemesh.mesh.NewMeshService;
import com.dys.consul.servicemesh.mesh.Proxy;
import com.dys.consul.servicemesh.mesh.SidecarService;
import com.dys.consul.servicemesh.mesh.Upstream;
import com.ecwid.consul.v1.agent.model.NewService;

import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationProperties;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryProperties;
import org.springframework.cloud.consul.discovery.HeartbeatProperties;
import org.springframework.cloud.consul.serviceregistry.ConsulAutoRegistration;
import org.springframework.cloud.consul.serviceregistry.ConsulManagementRegistrationCustomizer;
import org.springframework.cloud.consul.serviceregistry.ConsulRegistrationCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class ConsulMeshAutoRegistration extends ConsulAutoRegistration {

    /**
     * @param service
     * @param autoServiceRegistrationProperties
     * @param properties
     * @param context
     * @param heartbeatProperties
     * @param managementRegistrationCustomizers
     */
    public ConsulMeshAutoRegistration(NewService service,
            AutoServiceRegistrationProperties autoServiceRegistrationProperties, ConsulDiscoveryProperties properties,
            ApplicationContext context, HeartbeatProperties heartbeatProperties,
            List<ConsulManagementRegistrationCustomizer> managementRegistrationCustomizers) {
        super(service, autoServiceRegistrationProperties, properties, context, heartbeatProperties,
                managementRegistrationCustomizers);
    }

    /**
     * @param service
     * @param autoServiceRegistrationProperties
     * @param properties
     * @param context
     * @param heartbeatProperties
     * @deprecated
     */
    public ConsulMeshAutoRegistration(NewService service,
            AutoServiceRegistrationProperties autoServiceRegistrationProperties,
            ConsulMeshDiscoveryProperties properties, ApplicationContext context,
            HeartbeatProperties heartbeatProperties) {
        super(service, autoServiceRegistrationProperties, properties, context, heartbeatProperties);
    }

    public static ConsulAutoRegistration registration(
            AutoServiceRegistrationProperties autoServiceRegistrationProperties,
            ConsulMeshDiscoveryProperties properties, ApplicationContext context,
            List<ConsulRegistrationCustomizer> registrationCustomizers,
            List<ConsulManagementRegistrationCustomizer> managementRegistrationCustomizers,
            HeartbeatProperties heartbeatProperties) {

        NewMeshService service = new NewMeshService();
        if (properties.isSidecar()) {

            Connect connect = new Connect();
            service.setConnect(connect);
            SidecarService sidecarService = new SidecarService();
            Proxy proxy = new Proxy();
            List<Upstream> upstreams = new ArrayList<>();
            proxy.setUpstreams(upstreams);
            sidecarService.setProxy(proxy);
            connect.setSidecarService(sidecarService);
            properties.getUpstream().forEach((k, v) -> {
                Upstream upstream = new Upstream();
                upstream.setDestinationName(k);
                upstream.setLocalBindPort(v);
                upstreams.add(upstream);
            });
        }

        String appName = getAppName(properties, context.getEnvironment());
        service.setId(getInstanceId(properties, context));
        // 启用sidecar 必须不填服务Address，否则sidecar端口会绑定到hostname上但是sidecar health
        // check却默认是发往127.0.0.1导致失败
        if (!properties.isPreferAgentAddress() && !properties.isSidecar()) {
            service.setAddress(properties.getHostname());
        }
        service.setName(normalizeForDns(appName));
        service.setTags(new ArrayList<>(properties.getTags()));
        service.setEnableTagOverride(properties.getEnableTagOverride());
        service.setMeta(getMetadata(properties));

        if (properties.getPort() != null) {
            service.setPort(properties.getPort());
            // we know the port and can set the check
            setCheck(service, autoServiceRegistrationProperties, properties, context, heartbeatProperties);
        }

        ConsulAutoRegistration registration = new ConsulAutoRegistration(service, autoServiceRegistrationProperties,
                properties, context, heartbeatProperties, managementRegistrationCustomizers);
        customize(registrationCustomizers, registration);
        return registration;
    }

    private static Map<String, String> getMetadata(ConsulDiscoveryProperties properties) {
        LinkedHashMap<String, String> metadata = new LinkedHashMap<>();
        if (!CollectionUtils.isEmpty(properties.getMetadata())) {
            metadata.putAll(properties.getMetadata());
        }

        // add metadata from other properties. See createTags above.
        if (!StringUtils.isEmpty(properties.getInstanceZone())) {
            metadata.put(properties.getDefaultZoneMetadataName(), properties.getInstanceZone());
        }
        if (!StringUtils.isEmpty(properties.getInstanceGroup())) {
            metadata.put("group", properties.getInstanceGroup());
        }

        // store the secure flag in the tags so that clients will be able to figure
        // out whether to use http or https automatically
        metadata.put("secure", Boolean.toString(properties.getScheme().equalsIgnoreCase("https")));

        return metadata;
    }
}
