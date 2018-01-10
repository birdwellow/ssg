package net.fvogel.web;

import javax.servlet.http.HttpServletResponse;

import net.fvogel.model.Account;
import net.fvogel.model.Nation;
import net.fvogel.repo.NationRepository;
import net.fvogel.service.AccountService;
import net.fvogel.service.NationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nations")
public class NationsResource {

    @Autowired
    NationService nationService;

    @Autowired
    AccountService accountService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Nation getCurrentUsersNation(HttpServletResponse response) {
        Account account = accountService.getCurrentAccount();
        if (account == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        return account.getNation();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Nation getNationById(@PathVariable() Long id) {
        return nationService.getNation(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Long createNation(@RequestBody Nation nationData) {
        Nation nation = nationService.registerNewNation(nationData);
        return nation.getId();
    }

}
