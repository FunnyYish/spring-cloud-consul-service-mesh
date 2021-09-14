package com.dys.consul.servicemesh;

import java.util.List;

import com.ecwid.consul.v1.ConsulClient;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.CommonsClientAutoConfiguration;
import org.springframework.cloud.client.ConditionalOnBlockingDiscoveryEnabled;
import org.springframework.cloud.client.ConditionalOnDiscoveryEnabled;
import org.springframework.cloud.client.discovery.simple.SimpleDiscoveryClientAutoConfiguration;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationProperties;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.commons.util.UtilAutoConfiguration;
import org.springframework.cloud.consul.ConditionalOnConsulEnabled;
import org.springframework.cloud.consul.ConsulAutoConfiguration;
import org.springframework.cloud.consul.discovery.ConditionalOnConsulDiscoveryEnabled;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryClient;
import org.springframework.cloud.consul.discovery.HeartbeatProperties;
import org.springframework.cloud.consul.serviceregistry.ConsulAutoRegistration;
import org.springframework.cloud.consul.serviceregistry.ConsulManagementRegistrationCustomizer;
import org.springframework.cloud.consul.serviceregistry.ConsulRegistrationCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@ConditionalOnDiscoveryEnabled
@ConditionalOnBlockingDiscoveryEnabled
@ConditionalOnConsulEnabled
@ConditionalOnConsulDiscoveryEnabled
@EnableConfigurationProperties
@AutoConfigureBefore({ SimpleDiscoveryClientAutoConfiguration.class, CommonsClientAutoConfiguration.class })
@AutoConfigureAfter({ UtilAutoConfiguration.class, ConsulAutoConfiguration.class })
public class ConsulMeshConfiguration {
    @Bean

    public ConsulMeshDiscoveryProperties consulDiscoveryProperties(InetUtils inetUtils) {
        return new ConsulMeshDiscoveryProperties(inetUtils);
    }

    @Bean
    public ConsulDiscoveryClient consulDiscoveryClient(ConsulClient consulClient,
            ConsulMeshDiscoveryProperties discoveryProperties) {
        return new ConsulMeshDiscoveryClient(consulClient, discoveryProperties);
    }

    @Bean
    public ConsulAutoRegistration consulRegistration(
            AutoServiceRegistrationProperties autoServiceRegistrationProperties,
            ConsulMeshDiscoveryProperties properties, ApplicationContext applicationContext,
            ObjectProvider<List<ConsulRegistrationCustomizer>> registrationCustomizers,
            ObjectProvider<List<ConsulManagementRegistrationCustomizer>> managementRegistrationCustomizers,
            HeartbeatProperties heartbeatProperties) {
        return ConsulMeshAutoRegistration.registration(autoServiceRegistrationProperties, properties,
                applicationContext, registrationCustomizers.getIfAvailable(),
                managementRegistrationCustomizers.getIfAvailable(), heartbeatProperties);
    }
}
