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
	
	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("pizza", new Pizza());
		model.addAttribute("list", ingredienteService.findAllSortByIngrediente());
		return "/pizza/edit";
		
		
	}
	
	@PostMapping("/create")
	public String doCreate(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model) {
		model.addAttribute("list", ingredienteService.findAllSortByIngrediente());
		
		if(bindingResult.hasErrors()) {
			return "/pizza/edit";
		}
		
		service.save(formPizza);
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
