package com.example.blog_2.controllers;

import com.example.blog_2.models.Messages;
import com.example.blog_2.models.User;
import com.example.blog_2.repos.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.Binding;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Controller
public class MainControllers {

    @Autowired
    private MessageRepository messageRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String home(Model model){
        return "home";
    }

    @GetMapping("/main")
    public String mainPage(@RequestParam(required = false,defaultValue = "") String text,Model model){
        model.addAttribute("tittle", "Main Page");
        Iterable<Messages> messages = messageRepository.findAll();
        if(text!=null&&!text.isEmpty()){
            messages = messageRepository.findByTag(text);
        }else {
            messages = messageRepository.findAll();
        }
        model.addAttribute("messages",messages);
        model.addAttribute("filter",text);
        return "main_page";
    }

    @PostMapping("/main")
    public String add(
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal User user,
            @Valid Messages message,
            BindingResult bindingResult,
            Model model) throws IOException {

        message.setAuthor(user);

        if(bindingResult.hasErrors()){
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errorsMap);
            model.addAttribute("message",message);
        }else {
            if(file!=null) {
                File file1 = new File(uploadPath);

                if (!file1.exists() && !file.getOriginalFilename().isEmpty()) {
                    file1.mkdir();
                }

                String uuidFile = UUID.randomUUID().toString();
                String resulFileName = uuidFile + "." + file.getOriginalFilename();

                file.transferTo(new File(uploadPath + "/" + resulFileName));

                model.addAttribute("message",null);

                message.setFilename(resulFileName);
                messageRepository.save(message);
            }
        }

        Iterable<Messages> messages = messageRepository.findAll();
        model.addAttribute("messages",messages);
        return "main_page";
    }


}
