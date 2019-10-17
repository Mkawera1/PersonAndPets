package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class HomeController {
    @Autowired
    PersonRepository personRepository;
    @Autowired
    PetRepository petRepository;

    @RequestMapping("/")
    public String Main(Model model){
        model.addAttribute("persons", personRepository.findAll());
        model.addAttribute("pets", petRepository.findAll());
        return "home";
    }
    @RequestMapping("/petlist")
    public String petList(Model model){
        model.addAttribute("pets", petRepository.findAll());
        return "petlist";
    }
    @GetMapping("/addpet")
    public String petForm(Model model){
        model.addAttribute("pet", new Pet());
        model.addAttribute("persons", personRepository.findAll());
        return "petform";
    }
    @PostMapping("/processpet")
    public String processForm(@Valid Pet pet,
                              BindingResult result) {
        if (result.hasErrors()) {
            return "petform";
        }
        petRepository.save(pet);
        return "redirect:/";
    }
    @RequestMapping("personlist")
    public String personList(Model model){
        model.addAttribute("persons", personRepository.findAll());
        return "personlist";
    }
    @GetMapping("addperson")
    public String personForm(Model model){
        model.addAttribute("person", new Person());
        return "personform";
    }
    @PostMapping("/processperson")
    public String processForm1(@Valid Person person,
                              BindingResult result) {
        if (result.hasErrors()){
            return "personform";
        }
        personRepository.save(person);
        return "redirect:/";
    }
    @RequestMapping("/detailpet/{id}")
    public String showPet(@PathVariable("id") long id, Model model)
    {model.addAttribute("pet", petRepository.findById(id).get());
        return "showpet";
    }
    @RequestMapping("/updatepet/{id}")
    public String updatePet(@PathVariable("id") long id,Model model){
        model.addAttribute("pet", petRepository.findById(id).get());
        model.addAttribute("persons", personRepository.findAll());
        return "petform";
    }
    @RequestMapping("/deletepet/{id}")
    public String delPet(@PathVariable("id") long id){
        petRepository.deleteById(id);
        return "redirect:/";
    }
    @RequestMapping("/detailperson/{id}")
    public String showPerson(@PathVariable("id") long id, Model model){
        model.addAttribute("person", personRepository.findById(id).get());
        return "showperson";
    }
    @RequestMapping("/updateperson/{id}")
    public String updatePerson(@PathVariable("id") long id,Model model){
        model.addAttribute("person", personRepository.findById(id).get());
        return "personform";
    }
    @RequestMapping("/deleteperson/{id}")
    public String delPerson(@PathVariable("id") long id){
        personRepository.deleteById(id);
        return "redirect:/";
    }
    @PostMapping("/processsearch")
    public String searchResult(Model model, @RequestParam(name="search") String search){
        model.addAttribute("pets", petRepository.findBynameContainingIgnoreCase(search));
        return "searchlist";
    }


    }
