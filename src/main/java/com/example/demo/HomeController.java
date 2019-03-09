package com.example.demo;

import com.cloudinary.utils.ObjectUtils;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Id;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    CarRepository carRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    cloudinaryConfig cloudc;

    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("categories",categoryRepository.findAll());
        model.addAttribute("cars",carRepository.findAll());
        return "index";
    }
    @GetMapping("/addcar")
    public String addCar(Model model){
        model.addAttribute("cars",new Car());
        model.addAttribute("categories",categoryRepository.findAll());
        return "carForm";
    }
    @PostMapping("/processCar")
    public String processCar(@Valid Car cars, BindingResult result, Model model , @RequestParam("file") MultipartFile file) {

            if (file.isEmpty()) {
                return "redirect:/addcar";
            }
            try {
                Map uploadResult = cloudc.upload(file.getBytes(),
                        ObjectUtils.asMap("resourcetype", "auto"));
                cars.setHeadshot(uploadResult.get("url").toString());
                carRepository.save(cars);
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/addcar";
            }
            return "redirect:/";
        }
        @GetMapping("/addCategories")
         public String addCategories(Model model){
            model.addAttribute("categories",new Categories());
            return "categoryForm";
        }
        @PostMapping("processCategory")
    public String processCategory(@Valid Categories categories,BindingResult result,Model model){
         if(result.hasErrors()){
             return "categoryForm";
         }
         if(categoryRepository.findByName(categories.getName())!=null){
             model.addAttribute("Error","please enter d/t Category there is already "+categories.getName()+" Category");
         }
         categoryRepository.save(categories);
         return "redirect:/";
        }

     @RequestMapping("/detail_car/{id}")
     public String detail1(@PathVariable("id") long id, Model model){
        model.addAttribute("cars",carRepository.findById(id).get());
        return "show_car";
     }

    @RequestMapping("/detail_categories/{id}")
    public String detail2(@PathVariable("id") long id, Model model){
        model.addAttribute("categories",categoryRepository.findById(id).get());
        return "show_categories";
    }
    @RequestMapping("/update_car/{id}")
    public String update1(@PathVariable("id") long id, Model model){
        model.addAttribute("categories",categoryRepository.findAll());
        model.addAttribute("cars",carRepository.findById(id).get());
        return "carForm";
    }
    @RequestMapping("/update_categories/{id}")
     public String update2(@PathVariable("id") long id, Model model){
      model.addAttribute("categories",categoryRepository.findById(id).get());
      return "categoriesForm";
    }
    @RequestMapping("/delete_car/{id}")
    public String delete1(@PathVariable("id")long id,Model model){
        carRepository.deleteById(id);
        return "redirect:/";
    }
    @RequestMapping("/delete_categories/{id}")
    public String delete2(@PathVariable("id") long id ){
        categoryRepository.deleteById(id);
        return "redirect:/";
    }

}
