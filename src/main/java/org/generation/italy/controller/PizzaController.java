 package org.generation.italy.controller;




import javax.validation.Valid;

import org.generation.italy.model.Ingrediente;
import org.generation.italy.model.Pizza;
import org.generation.italy.service.IngredienteService;
import org.generation.italy.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pizza")
public class PizzaController {

	@Autowired
	private PizzaService service;

	@Autowired
	private IngredienteService ingredienteService;

	@GetMapping
	public String list(Model model) {
		model.addAttribute("list", service.findAllSortedByRecent());
		model.addAttribute("ingredienteList", ingredienteService.findAllSortByIngrediente());
		return "/pizza/list";
	}
	
	@GetMapping("/detail/{id}")
	public String detail(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("pizza", service.getById(id));
		return "/pizza/detail";
		
	}
	
	
	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("edit", false);
		model.addAttribute("pizza", new Pizza());
		model.addAttribute("list", ingredienteService.findAllSortByIngrediente());
		return "/pizza/create";
		
		
	}
	
	@PostMapping("/create")
	public String doCreate(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("list", ingredienteService.findAllSortByIngrediente());
			model.addAttribute("edit", false);
			return "/pizza/create";
		}
		
		service.create(formPizza);
		return "redirect:/pizza";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("edit", true);
		model.addAttribute("pizza", service.getById(id));
		model.addAttribute("list", ingredienteService.findAllSortByIngrediente());
		return "/pizza/create";
	}
	
	@PostMapping("/edit/{id}")
	public String doUpdate(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("edit", true);
			model.addAttribute("list", ingredienteService.findAllSortByIngrediente()); 
			return "/pizza/create";
		}
		service.update(formPizza);
		return "redirect:/pizza";
	}
	
	@GetMapping("/delete/{id}")
	public String doDelete(Model model, @PathVariable("id") Integer id) {
		service.deleteById(id);
		return "redirect:/pizza";
	}
	
	
	@GetMapping("/ingredienti")
	public String ingredienti(Model model) {
		model.addAttribute("ingrediente", new Ingrediente()); 
		model.addAttribute("list", ingredienteService.findAllSortByIngrediente());
		return "/pizza/ingredienti";
		
	}
	
	
	@PostMapping("/ingredienti")
	public String doCreate(@Valid @ModelAttribute("ingrediente") Ingrediente formIngrediente, BindingResult bindingResult, Model model) {

		model.addAttribute("list", ingredienteService.findAllSortByIngrediente());
		
		if(bindingResult.hasErrors()) {
			return "/pizza/ingredienti";
		}
		
		ingredienteService.save(formIngrediente);
		return "redirect:/pizza";
		}
	
	
	
}
