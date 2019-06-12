package org.csu.mypetstore.contorller;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/account/usernameIsExist")
    public void usernameIsExist(@RequestParam("username")String username, @RequestParam("message") String message, Model model){
        Account account = new Account();
        account.setUsername(username);
        System.out.println(username);
        System.out.println("   1");

        if(accountService.usernameIsExist(account)){
           message="Exit";
        }
        else {
            message="NotExist";
        }
        model.addAttribute("message",message);
    }

    @GetMapping("/catalog/signOn")
    public String signOn(@RequestParam("username")String username,@RequestParam("password")String password,@RequestParam("code1")String code1,@RequestParam("code")String code2,Model model){
        Account account=accountService.getAccount(username,password);
        boolean authenticated;
        String codeSession = code1;
        String code=(String)code2;
        boolean flag;
        if (!code.toLowerCase().equals(codeSession.toLowerCase())){
            flag=true;
            model.addAttribute("flag",flag);
            return "account/a_SignonForm";
        }else{
            flag=false;
            if (account==null){
                authenticated=false;
                model.addAttribute("message","Invalid username or password.  Signon failed.");
                return "account/SignonForm";
            }else{
                authenticated=true;
                model.addAttribute("authenticated",authenticated);
                model.addAttribute("account",account);
                model.addAttribute("flag",flag);
                return "catalog/c_Main";
            }
        }
    }

    @GetMapping("/catalog/signonForm")
    public String signOnForm(){
        return "account/a_SignonForm";
    }

    @GetMapping("/catalog/signOff")
    public String signOff(Model model){
        model.addAttribute("account",null);
        return "catalog/c_Main";
    }

    @GetMapping("/catalog/newAccount")
    public String newAccount(@RequestParam("username")String username,
                                    @RequestParam("password")String password,
                                    @RequestParam("account.firstName")String account_firstName,
                                    @RequestParam("account.lastName")String account_lastName,
                                    @RequestParam("account.email")String account_email,
                                    @RequestParam("account.phone")String account_phone,
                                    @RequestParam("account.address1")String account_address1,
                                    @RequestParam("account.address2")String account_address2,
                                    @RequestParam("account.city")String account_city,
                                    @RequestParam("account.state")String account_state,
                                    @RequestParam("account.zip")String account_zip,
                                    @RequestParam("account.country")String account_country){
        Account account=new Account();

        account.setUsername(username);
        account.setPassword(password);
        account.setFirstName(account_firstName);
        account.setLastName(account_lastName);
        account.setEmail(account_email);
        account.setPhone(account_phone);
        account.setAddress1(account_address1);
        account.setAddress2(account_address2);
        account.setCity(account_city);
        account.setState(account_state);
        account.setZip(account_zip);
        account.setCountry(account_country);

        accountService.insertAccount(account);

        return "account/a_SignonForm";
    }

    @GetMapping("/catalog/newAccountForm")
    public String newAccountForm(){
        return "account/a_NewAccountForm";
    }

    @GetMapping("/catalog/editAccount")
    public String editAccount(@RequestAttribute("account")Account account, Model model){
        accountService.updateAccount(account);
        model.addAttribute("account",account);
        return "account/a_EditAccountForm";
    }

    @GetMapping("/account/editAccountForm")
    public String editAccountForm(@RequestParam("username")String username, Model model){
        Account account=accountService.getAccount(username);

        model.addAttribute("username",username);
        model.addAttribute("account",account);

        return "account/a_EditAccountForm";
    }
}
