package com.dj.web;

import com.dj.dao.UserRepository;
import com.dj.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AboutShowController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/about/{id}")
    public String about(Model model,@PathVariable Long id){
        model.addAttribute("user",userRepository.getOne(id));
        return "about";
    }

    @GetMapping("/about")
    public String indexToAbout(Model model){
        model.addAttribute("user",userRepository.getOne(1L));
        return "about";
    }
}
