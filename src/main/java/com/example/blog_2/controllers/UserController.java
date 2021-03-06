package com.example.blog_2.controllers;

import com.example.blog_2.models.Role;
import com.example.blog_2.models.User;
import com.example.blog_2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String userList(Model model){
        model.addAttribute("users",userService.findAll());

        return "user_list";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user,Model model){
        model.addAttribute("user",user);
        model.addAttribute("roles", Role.values());

        return "user_edit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String userSave(
            @RequestParam String userName,
            @RequestParam Map<String,String> form,
            @RequestParam("userId") User user
    ){
        userService.saveUser(user,userName,form);

        return "redirect:/user";
    }

    @GetMapping("profile")
    public String getProfile(
            Model model,
            @AuthenticationPrincipal User user
    ){

        model.addAttribute("username",user.getUsername());
        model.addAttribute("email",user.getEmail());

        return "profile";
    }


    @PostMapping("profile")
    public String updateProfile(
            @AuthenticationPrincipal User user,
            @RequestParam String password,
            @RequestParam String email
    ){
        userService.updateProfile(user,password,email);

        return "redirect:user/profile";
    }

    @GetMapping("subscribe/{user}")
    public String subscribe(
            @PathVariable User user,
            @AuthenticationPrincipal User currentUser){

        userService.subscribe(user,currentUser);

        return "redirect:/user-messages/" + user.getId();
    }

    @GetMapping("unsubscribe/{user}")
    public String unsubscribe(
            @PathVariable User user,
            @AuthenticationPrincipal User currentUser){

        userService.unsubscribe(user,currentUser);

        return "redirect:/user-messages/" + user.getId();
    }

    @GetMapping("{type}/{user}/list")
    public String userList(
            @PathVariable User user,
            @PathVariable String type,
            Model model){


        model.addAttribute("type",type);
        model.addAttribute("userChannel",user);

        if("subscriptions".equals(type)){
            model.addAttribute("users",user.getSubscriptions());
        }else {
            model.addAttribute("users",user.getSubscribers());
        }

        return "subscriptions";
    }


}
