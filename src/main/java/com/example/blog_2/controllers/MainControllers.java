package com.example.blog_2.controllers;

import com.example.blog_2.models.Messages;
import com.example.blog_2.models.User;
import com.example.blog_2.repos.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.Binding;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
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
            saveFile(file, message,model);
        }

        Iterable<Messages> messages = messageRepository.findAll();
        model.addAttribute("messages",messages);
        return "main_page";
    }

    private void saveFile(@RequestParam("file") MultipartFile file, @Valid Messages message,Model model) throws IOException {

        if (file != null && !file.getOriginalFilename().isEmpty()) {

            File file1 = new File(uploadPath);

            if (!file1.exists()) {
                file1.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resulFileName = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(file1.getCanonicalPath() + "/" + resulFileName));

            message.setFilename(resulFileName);
        }
        model.addAttribute("message", null);

        messageRepository.save(message);
    }


    @GetMapping("/user-messages/{user}")
    public String userMessages(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            @RequestParam(required = false) Messages message,
            Model model
    ){
        Set<Messages> messages = user.getMessages();

        model.addAttribute("profileName",user.getUsername());
        model.addAttribute("userChannel",user);
        model.addAttribute("messages",messages);
        model.addAttribute("isCurrentUser",currentUser.equals(user));
        model.addAttribute("message",message);
        model.addAttribute("subscriptionsCount",user.getSubscriptions().size());
        model.addAttribute("subscribersCount",user.getSubscribers().size());
        model.addAttribute("isSubscriber",user.getSubscribers().contains(currentUser));

        return "user_messages";
    }

    @PostMapping("/user-messages/{user}")
    public String editMessage(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long user,
            @RequestParam("id") Messages message,
            @RequestParam("text") String text,
            @RequestParam("tag") String tag,
            @RequestParam("file") MultipartFile file,
            Model model
    ) throws IOException {
        if (message.getAuthor().equals(currentUser)){
            if(!StringUtils.isEmpty(text)){
                message.setText(text);
            }

            if(!StringUtils.isEmpty(tag)){
                message.setTag(tag);
            }

            saveFile(file, message,model);

            messageRepository.save(message);
        }


        return "redirect:/user-messages/" + user;
    }



}
