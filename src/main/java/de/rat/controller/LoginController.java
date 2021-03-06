package de.rat.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/** Controller for all pages they are handle with the Login
 * sets parameter and generate the data for the views
 *
 * @author Marco Petzold, Christian König, Danny Steinbrecher
 */
@Controller
public class LoginController {

    /**
     * @return  login
     * redirect to loginForm.html
     */
    @GetMapping("/login")
    public String welcome() {
        return "loginForm";
    }

    /**
     * @return  /
     * @param authentication Authentication
     * redirect to index.html
     */
    @GetMapping(value = "/loginSuccessfull")
    public String currentUserName(Authentication authentication) {
        return "redirect:/";
    }
    }
