package com.ewolff.microservice.catalog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ewolff.microservice.catalog.domain.Item;
import com.ewolff.microservice.catalog.repository.ItemRepository;

@Controller
public class CatalogController {

	private final ItemRepository itemRepository;

	@Autowired
	public CatalogController(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}

	@RequestMapping(value = "/{id}.html", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	public ModelAndView Item(@PathVariable("id") long id) {
		return new ModelAndView("item", "item", itemRepository.findOne(id));
	}

	@RequestMapping("/list.html")
	public ModelAndView ItemList() {
		return new ModelAndView("itemlist", "items", itemRepository.findAll());
	}

	@RequestMapping(value = "/add.html", method = RequestMethod.GET)
	public ModelAndView add() {
		return new ModelAndView("item", "item", new Item());
	}

	@RequestMapping(value = "/add.html", method = RequestMethod.POST)
	public ModelAndView post(Item Item) {
		Item = itemRepository.save(Item);
		return new ModelAndView("success");
	}

	@RequestMapping(value = "/{id}.html", method = RequestMethod.PUT)
	public ModelAndView put(@PathVariable("id") long id, Item item) {
		item.setId(id);
		itemRepository.save(item);
		return new ModelAndView("success");
	}

	@RequestMapping(value = "/searchForm.html", produces = MediaType.TEXT_HTML_VALUE)
	public ModelAndView searchForm() {
		return new ModelAndView("search");
	}

	@RequestMapping(value = "/search.html", produces = MediaType.TEXT_HTML_VALUE)
	public ModelAndView search(@RequestParam("query") String query) {
		return new ModelAndView("itemlist", "items",
				itemRepository.findByNameContaining(query));
	}

	@RequestMapping(value = "/{id}.html", method = RequestMethod.DELETE)
	public ModelAndView delete(@PathVariable("id") long id) {
		itemRepository.delete(id);
		return new ModelAndView("success");
	}

}
