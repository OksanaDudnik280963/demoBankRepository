package com.example.demoBankRepository.controller;

import com.example.demoBankRepository.dto.AccountRequest;
import com.example.demoBankRepository.dto.BankResponse;
import com.example.demoBankRepository.entity.Account;
import com.example.demoBankRepository.service.AccountService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static com.example.demoBankRepository.utils.JsonUtils.toJson;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);
    public static final String ERR_MSG = "errMsg";
    public static final String ERR_PAGE = "/errors/error";
    public static final String ACCOUNTS_PAGE = "/accounts/accountsList";
    public static final String EDIT_ACCOUNT = "/accounts/edit_account";
    public static final String NEW_ACCOUNT = "/accounts/new_account";
    public static final String REDIRECT = "redirect:";

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    @PostMapping(value = "/create", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public BankResponse createAccount(@RequestBody AccountRequest accountRequest) {
        LOGGER.debug("Created account...");
        return accountService.createAccount(accountRequest);
    }

    /*
        @PostMapping(value = "/new")
        public String newAccount(@ModelAttribute("account") AccountRequest accountRequest) {
            LOGGER.debug("New account...");
           //BankResponse bankResponse = accountService.createAccount(accountRequest);
            //LOGGER.debug(bankResponse.getResponseCode());
            //LOGGER.debug(bankResponse.getResponseMessage());
            ModelAndView modelAndView = new ModelAndView(NEW_ACCOUNT);
            try {
                Account newAccount = accountService.newAccount(accountRequest);
                modelAndView.addObject("account", newAccount);
                List<Account> accounts = accountService.listAccounts()      ;
                modelAndView.addObject("accounts", accounts);
                accounts.forEach(usr ->
                        LOGGER.info(toJson(usr))
                );
                return REDIRECT + ACCOUNTS_PAGE;
            } catch (Exception ex) {
                String errMsg = ex.getMessage();
                modelAndView.addObject(ERR_MSG, errMsg);
                modelAndView.setViewName(ERR_PAGE);
                return REDIRECT + ERR_PAGE;
            }
        }
    */
    @GetMapping(value = "/list", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView listAll(@RequestParam("page") Optional<Integer> page,
                                @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        Page<Account> accountsPage = this.accountService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
        List<Account> accounts = accountsPage.getContent();
        ModelAndView modelAndView = new ModelAndView(ACCOUNTS_PAGE);
        modelAndView.addObject("accounts", accounts);
        modelAndView.addObject("currentPage", currentPage);
        modelAndView.addObject("accountPage", accountsPage);
        int totalPages = accountsPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(java.util.stream.Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }
        try {
            accounts.forEach(usr ->
                    LOGGER.info(usr.getEmail()==null?"no email":usr.getEmail())
            );
            return  modelAndView;
        } catch (Exception ex) {
            String exMessage = ex.getMessage();
            ModelAndView model = new ModelAndView(ERR_PAGE);
            model.addObject(ERR_MSG, exMessage);
            LOGGER.error(exMessage);
            return modelAndView;
        }
    }
    @PostMapping(value = "/new")
    public String showNewAccount(Model model) {
        AccountRequest accountRequest = new AccountRequest();
        model.addAttribute("account", accountRequest);
            LOGGER.info(toJson(accountRequest));
        return NEW_ACCOUNT;
    }


}



