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
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Component
public class CatalogClient {

	public static class ItemPagedResources extends PagedResources<Item> {

	}

	private RestTemplate restTemplate;
	private String catalogServiceHost;
	private long catalogServicePort;
	private boolean useRibbon;
	private LoadBalancerClient loadBalancer;
	private Collection<Item> itemsCache = null;

	public CatalogClient() {
		this.restTemplate = getRestTemplate();
	}

	@Value("${catalog.service.host:catalog}")
	public void setCatalogServiceHost(String itemServiceHost) {
		this.catalogServiceHost = itemServiceHost;
	}

	@Value("${catalog.service.port:8080}")
	public void setCatalogServicePort(long itemServicePort) {
		this.catalogServicePort = itemServicePort;
	}

	@Value("${ribbon.eureka.enabled:false}")
	public void setUseRibbon(boolean userRibbon) {
		this.useRibbon = userRibbon;
	}

	@Autowired(required = false)
	public void setLoadBalancer(LoadBalancerClient loadBalancer) {
		this.loadBalancer = loadBalancer;
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

	@HystrixCommand(fallbackMethod = "priceCache", commandProperties = { @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2") })
	public double price(long itemId) {
		return getOne(itemId).getPrice();
	}

	public double priceCache(long itemId) {
		return getOneCache(itemId).getPrice();
	}

	@HystrixCommand(fallbackMethod = "getItemsCache", commandProperties = { @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2") })
	public Collection<Item> findAll() {
		PagedResources<Item> pagedResources = restTemplate.getForObject(
				catalogURL(), ItemPagedResources.class);
		this.itemsCache = pagedResources.getContent();
		return pagedResources.getContent();
	}

	public Collection<Item> getItemsCache() {
		return itemsCache;
	}

	private String catalogURL() {
		if (useRibbon) {
			ServiceInstance instance = loadBalancer.choose("CATALOG");
			return "http://" + instance.getHost() + ":" + instance.getPort()
					+ "/catalog/";
		} else {
			return "http://" + catalogServiceHost + ":" + catalogServicePort
					+ "/catalog/";
		}
	}

	@HystrixCommand(fallbackMethod = "getOneCache", commandProperties = { @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2") })
	public Item getOne(long itemId) {
		return restTemplate.getForObject(catalogURL() + itemId, Item.class);
	}

	public Item getOneCache(long itemId) {
		return itemsCache.stream().filter(i -> (i.getItemId() == itemId))
				.findFirst().get();
	}
}
