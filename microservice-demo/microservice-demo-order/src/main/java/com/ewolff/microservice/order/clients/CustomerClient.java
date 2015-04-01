package com.ewolff.microservice.order.clients;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CustomerClient {

	private RestTemplate restTemplate;
	private String customerServiceHost;
	private long customerServicePort;
	private boolean useRibbon;
	private LoadBalancerClient loadBalancer;

	static class CustomerPagedResources extends PagedResources<Customer> {

	}

	public CustomerClient() {
		this.restTemplate = getRestTemplate();
	}

	@Value("${customer.service.host:customer}")
	public void setCustomerServiceHost(String customerServiceHost) {
		this.customerServiceHost = customerServiceHost;
	}

	@Value("${customer.service.port:8080}")
	public void setCustomerServicePort(long customerServicePort) {
		this.customerServicePort = customerServicePort;
	}

	@Value("${ribbon.eureka.enabled:false}")
	public void setUseRibbon(boolean userRibbon) {
		this.useRibbon = userRibbon;
	}

	@Autowired(required = false)
	public void setLoadBalancer(LoadBalancerClient loadBalancer) {
		this.loadBalancer = loadBalancer;
	}

	public boolean isValidCustomerId(long customerId) {
		RestTemplate restTemplate = new RestTemplate();
		try {
			ResponseEntity<String> entity = restTemplate.getForEntity(
					customerURL() + customerId, String.class);
			return entity.getStatusCode().is2xxSuccessful();
		} catch (final HttpClientErrorException e) {
			if (e.getStatusCode().value() == 404)
				return false;
			else
				throw e;
		}
	}

	protected RestTemplate getRestTemplate() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
				false);
		mapper.registerModule(new Jackson2HalModule());

		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(MediaType
				.parseMediaTypes("application/hal+json"));
		converter.setObjectMapper(mapper);

		return new RestTemplate(
				Collections.<HttpMessageConverter<?>> singletonList(converter));
	}

	public Collection<Customer> findAll() {
		PagedResources<Customer> pagedResources = getRestTemplate()
				.getForObject(customerURL(), CustomerPagedResources.class);
		return pagedResources.getContent();
	}

	private String customerURL() {
		if (useRibbon) {
			ServiceInstance instance = loadBalancer.choose("CUSTOMER");
			return "http://" + instance.getHost() + ":" + instance.getPort()
					+ "/customer/";
		} else {
			return "http://" + customerServiceHost + ":" + customerServicePort
					+ "/customer/";
		}

	}

	public Customer getOne(long customerId) {
		return restTemplate.getForObject(customerURL() + customerId,
				Customer.class);
	}
}
