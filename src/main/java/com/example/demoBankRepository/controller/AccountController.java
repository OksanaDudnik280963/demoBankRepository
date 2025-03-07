package com.example.demoBankRepository.controller;

import com.example.demoBankRepository.dto.AccountRequest;
import com.example.demoBankRepository.dto.BankResponse;
import com.example.demoBankRepository.entity.Account;
import com.example.demoBankRepository.service.AccountService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static com.example.demoBankRepository.utils.JsonUtils.toJson;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Controller
@RequestMapping("/api/accounts")
public class AccountController {
    public static final String ERR_MSG = "err";
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
        log.debug("Created account...");
        return accountService.createAccount(accountRequest);
    }

    @GetMapping(value = "/list", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView listAll(@RequestParam("page") Optional<Integer> page,
                                @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        Page<Account> accountsPage;
        accountsPage = this.accountService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
        List<Account> accounts = (List<Account>) accountsPage.getContent();
        ModelAndView modelAndView = new ModelAndView(ACCOUNTS_PAGE);
        modelAndView.addObject("accounts", accountsPage.getContent());
        modelAndView.addObject("currentPage", currentPage);
        modelAndView.addObject("pageSize", pageSize);
        modelAndView.addObject("accountPage", accountsPage);
        modelAndView.addObject("pageNumbers", accountsPage.getTotalPages());
        int totalPages = accountsPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(java.util.stream.Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }
        try {
            accounts.forEach(usr ->
                    log.info(usr.getEmail() == null ? "no email" : usr.getEmail())
            );
            return modelAndView;
        } catch (Exception ex) {
            String exMessage = ex.getMessage();
            ModelAndView model = new ModelAndView(ERR_PAGE);
            model.addObject(ERR_MSG, exMessage);
            log.error(exMessage);
            return model;
        }

    }

    @GetMapping(value = "/new")
    public String showNewAccount(Model model) {
        model.addAttribute("account", new Account());
        return NEW_ACCOUNT;
    }

    @PostMapping(value = "/new")
    public ModelAndView saveNewAccount(@ModelAttribute("account") Account account, Model model,
                                 @RequestParam("page") Optional<Integer> page,
                                 @RequestParam("size") Optional<Integer> size) throws Exception{

            Account realAccount = this.accountService.save(account);
            model.addAttribute("account", realAccount);
            return listAll(page, size);
        }

        @GetMapping(value = "/edit/{id}")
        public ModelAndView editAccount (@ModelAttribute("account") Account account) throws Exception {

            ModelAndView modelAndView = new ModelAndView(EDIT_ACCOUNT);
            Account realAccount = this.accountService.getAccountById(account.getId()).orElseThrow(() -> new Exception("Id is wrong!"));
            ;
            modelAndView.addObject("account", realAccount);
            this.accountService.save(realAccount);
            return modelAndView;
        }

        @PostMapping(value = "/edit")
        public ModelAndView save (@ModelAttribute("account") Account account){

            ModelAndView modelAndView = new ModelAndView(EDIT_ACCOUNT);
            modelAndView.addObject("account", account);
            try {
                this.accountService.save(account);
            } catch (Exception ex) {
                String exMessage = ex.getMessage();
                ModelAndView model = new ModelAndView(ERR_PAGE);
                model.addObject(ERR_MSG, exMessage);
                log.error(exMessage);
                return model;
            }
            return listAll(Optional.of(1), Optional.of(10));
        }

        public Account save (AccountRequest accountRequest) throws RuntimeException {

            try {
                return this.accountService.save(accountRequest);
            } catch (Exception ex) {
                String exMessage = ex.getMessage();
                log.info(exMessage);
                throw new RuntimeException(exMessage);
            }
        }

        @GetMapping(value = "/delete/{id}")
        public ModelAndView deleteAccount (@ModelAttribute("account") Account account,
                @RequestParam("page") Optional < Integer > page,
                @RequestParam("size") Optional < Integer > size){
            this.accountService.delete(account);
            return listAll(page, size);
        }
    }



