package de.rat.controller;

import de.rat.account.details.NameControllerAdvice;
import de.rat.model.common.Account;
import de.rat.model.customer.Customer;
import de.rat.model.logistics.Tool;
import de.rat.repositories.AccountRepository;
import de.rat.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;
import java.util.Optional;

/**
 * Controller for all pages they are handle with the Account
 * sets parameter and generate the data for the views
 *
 * @author Marco Petzold, Christian König, Danny Steinbrecher
 */
@Controller
public class AccountController {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @RequestMapping(path = "/accountView")
    public String listAllrentedTool() {

        return "accountView";
    }

    /**
     * @return mav
     * generate the mav for the EditCustomerAccount Page
     */
    @RequestMapping("/editCustomerAccount")
    public ModelAndView showEditCustomerAccountPage() {

        NameControllerAdvice nameControllerAdvice = new NameControllerAdvice();
        int AccountId = nameControllerAdvice.getAuthUser();
        Customer customer = customerRepository.findByAccount_id(AccountId);

        ModelAndView mav = new ModelAndView("editAccount");

        mav.addObject("customerAccount", customer);
        mav.addObject("customerId", customer.getAccount().getId());

        return mav;
    }

    /**
     * @param aCustomer Customer
     * update the Customer Account in the Database
     * redirect to index.html
     */
    @PostMapping("/updateCustomerAccount")
    public String editCustomerAccount(@ModelAttribute("customerAccount") Customer aCustomer,
                           BindingResult bindingResultAccount,
                           BindingResult bindingResultCustomer,
                           BindingResult bindingResultAddress) {


        Customer oldCustomer = customerRepository.findById(aCustomer.getId());

        //TODO: dosnt check the errors correct, need to be improved
        while (bindingResultAccount.hasErrors() || bindingResultCustomer.hasErrors() || bindingResultAddress.hasErrors()) {
            return "editAccount";
        }

        Optional<Account> checkAccount = accountRepository.findByEmail(aCustomer.getAccount().getEmail());

        if (checkAccount.isPresent() && checkAccount.get().getId() != oldCustomer.getAccount().getId()) {
            System.out.println(checkAccount.get().getId() + " = " + oldCustomer.getId());
            bindingResultAccount.rejectValue("account.email", "error.userAccount", "An account already exists for this email.");
            return "editAccount";
        }

        oldCustomer.getAccount().setEmail(aCustomer.getAccount().getEmail());

        if (!aCustomer.getAccount().getPassword().equals("")) {
            String encodedPassword = passwordEncoder.encode(aCustomer.getAccount().getPassword());
            oldCustomer.getAccount().setPassword(encodedPassword);
        }

        oldCustomer.setFirstname(aCustomer.getFirstname());
        oldCustomer.setLastname(aCustomer.getLastname());
        oldCustomer.setBirthday(aCustomer.getBirthday());
        oldCustomer.setPhoneNumber(aCustomer.getPhoneNumber());

        oldCustomer.getAddress().setCity(aCustomer.getAddress().getCity());
        oldCustomer.getAddress().setCountry(aCustomer.getAddress().getCountry());
        oldCustomer.getAddress().setHouseNr(aCustomer.getAddress().getHouseNr());
        oldCustomer.getAddress().setStreet(aCustomer.getAddress().getStreet());
        oldCustomer.getAddress().setZip(aCustomer.getAddress().getZip());

        customerRepository.save(oldCustomer);
        return "redirect:/";
    }


    /**
     * @param model Model
     * @return rentedToolView
     * listed all rented Tools
     */
    @RequestMapping(path = "/rentedToolView")
    public String rentedToolView(Model model) {
        NameControllerAdvice nameControllerAdvice = new NameControllerAdvice();
        int AccountId = nameControllerAdvice.getAuthUser();
        Customer customer = customerRepository.findByAccount_id(AccountId);
        List<Tool> listTools = (List<Tool>) customer.getInventory();
        model.addAttribute("listTools", listTools);
        return "rentedToolView";
    }
}
