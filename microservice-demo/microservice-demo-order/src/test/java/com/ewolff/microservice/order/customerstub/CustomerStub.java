package com.ewolff.microservice.order.customerstub;

import java.util.Arrays;

import org.springframework.context.annotation.Profile;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.PagedModel.PageMetadata;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ewolff.microservice.order.clients.Customer;

@RestController
@RequestMapping("/customer")
@Profile("test")
public class CustomerStub {

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Customer> getById(@PathVariable("id") long id) {

		if (id != 42) {
			return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Customer>(new Customer(42, "Eberhard",
				"Wolff", "eberhard.wolff@gmail.com", "Unter den Linden",
				"Berlin"), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET)
	public PagedModel<Customer> getAll() {
		return new PagedModel<Customer>(Arrays.asList(new Customer(42,
				"Eberhard", "Wolff", "eberhard.wolff@gmail.com",
				"Unter den Linden", "Berlin")), new PageMetadata(1, 0, 1));
	}

}
