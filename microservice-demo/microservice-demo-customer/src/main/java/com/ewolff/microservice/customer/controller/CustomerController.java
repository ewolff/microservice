package com.ewolff.microservice.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ewolff.microservice.customer.domain.Customer;
import com.ewolff.microservice.customer.repository.CustomerRepository;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	private CustomerRepository customerRepository;

	@Autowired
	public CustomerController(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@RequestMapping(value = "/{id}.html", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	public ModelAndView customer(@PathVariable("id") long id) {
		return new ModelAndView("customer", "customer",
				customerRepository.findOne(id));
	}

	@RequestMapping("/list.html")
	public ModelAndView customerList() {
		return new ModelAndView("customerlist", "customers",
				customerRepository.findAll());
	}

	@RequestMapping(value = "/add.html", method = RequestMethod.GET)
	public ModelAndView add() {
		return new ModelAndView("customer", "customer", new Customer());
	}

	@RequestMapping(value = "/add.html", method = RequestMethod.POST)
	public ModelAndView post(Customer customer) {
		customer = customerRepository.save(customer);
		return new ModelAndView("redirect:/customer/" + customer.getId()
				+ ".html");
	}

	@RequestMapping(value = "/{id}.html", method = RequestMethod.PUT)
	public ModelAndView put(@PathVariable("id") long id, Customer customer) {
		customer.setId(id);
		customerRepository.save(customer);
		return new ModelAndView("redirect:/customer/" + customer.getId()
				+ ".html");
	}

}
