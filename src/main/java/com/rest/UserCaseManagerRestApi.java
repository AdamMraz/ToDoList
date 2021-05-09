package com.rest;

import com.model.Case;
import com.model.User;
import com.service.CaseManagerService;
import com.service.UserManagerService;
import com.service.exceptions.CaseIsNotException;
import com.service.responces.annotations.CaseExceptionHandler;
import com.service.responces.annotations.UserExceptionHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping(value = "/api/v2/case/")
@Api(value = "Case Manager", description = "Api для работы с делами конкретного пользователя")
@RequiredArgsConstructor
@UserExceptionHandler
@CaseExceptionHandler
public class UserCaseManagerRestApi {
    private final CaseManagerService caseManagerService;
    private final UserManagerService userManagerService;

    private User getUserByPrincipal (Principal principal) throws Exception {
        return userManagerService.findByUsername(principal.getName());
    }

    @GetMapping(headers = {"Api-Key"})
    @ApiOperation(value = "Получение списка дел по Api Key")
    public List<Case> getCaseByApiKey (@RequestHeader("Api-Key") String apiKey) {
        return caseManagerService.getUserCasesList(apiKey);
    }

    @GetMapping
    public List<Case> getCaseByApiKey (Principal principal) throws Exception {
        List<Case> caseList = new ArrayList<>();
        caseList.addAll(getUserByPrincipal(principal).getCases());
        return caseList;
    }

    @PostMapping(headers = {"Api-Key"})
    public int addCase (Case newCase, @RequestHeader("Api-Key") String apiKey) throws Exception {
        newCase.setUser(userManagerService.findByApiKey(apiKey));
        return caseManagerService.addCase(newCase);
    }

    @PostMapping
    public int addCase (Case newCase, Principal principal) throws Exception {
        newCase.setUser(getUserByPrincipal(principal));
        return caseManagerService.addCase(newCase);
    }

    @DeleteMapping(headers = {"Api-Key"})
    public void deleteAllCase (@RequestHeader("Api-Key") String apiKey) {
        caseManagerService.deleteAllUserCases(apiKey);
    }

    @DeleteMapping
    public void deleteAllCase (Principal principal) throws Exception {
        caseManagerService.deleteAllUserCases(getUserByPrincipal(principal).getApiKey());
    }

    @DeleteMapping(value = "{id}", headers = {"Api-Key"})
    public void deleteCase (@PathVariable int id, @RequestHeader("Api-Key") String apiKey) throws CaseIsNotException {
        if (caseManagerService.getCase(id).getUser().getApiKey().equals(apiKey)) {
            caseManagerService.deleteCase(id);
        }
    }

    @DeleteMapping(value = "{id}")
    public void deleteCase (@PathVariable int id, Principal principal) throws Exception {
        Case newCase = caseManagerService.getCase(id);
        if (newCase.getUser().getId() == getUserByPrincipal(principal).getId()) {
            caseManagerService.deleteCase(id);
        }
    }

    @PutMapping(value = "{id}", headers = {"Api-Key"})
    public void updateCase (@PathVariable int id, Case newCase, @RequestHeader("ApiKey") String apiKey) throws CaseIsNotException {
        if (caseManagerService.getCase(id).getUser().getApiKey().equals(apiKey)) {
            newCase.setId(id);
            caseManagerService.updateCase(newCase);
        }
    }

    @PutMapping(value = "{id}")
    public void updateCase (@PathVariable int id, Case newCase, Principal principal) throws Exception {
        Case oldCase = caseManagerService.getCase(id);
        if (oldCase.getUser().getId() == getUserByPrincipal(principal).getId()) {
            newCase.setId(id);
            newCase.setUser(getUserByPrincipal(principal));
            caseManagerService.updateCase(newCase);
        }
    }
}
