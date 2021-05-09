package com.service.impl;

import com.model.Case;
import com.repository.CaseRepo;
import com.service.CaseManagerService;
import com.service.exceptions.CaseIsNotException;
import com.service.exceptions.NullDateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CaseManagerServiceImpl implements CaseManagerService {
    private final CaseRepo caseRepo;

    @Override
    public List<Case> getCasesList() {
        return caseRepo.findAll();
    }

    @Override
    public int addCase(Case newCase) throws Exception {
        if (newCase.getValue() == null) {
            throw new NullDateException();
        }
        int id = (int)(caseRepo.count() + 1);
        newCase.setId(id);
        caseRepo.save(newCase);
        return id;
    }

    @Override
    public void deleteAllCases() {
        caseRepo.deleteAll();
    }

    @Override
    public Case getCase(int id) throws CaseIsNotException {
        Optional<Case> optional = caseRepo.findById(id);

        if (optional.isEmpty()) {
            throw new CaseIsNotException();
        }

        return optional.get();
    }

    @Override
    public void deleteCase(int id) throws CaseIsNotException {
        if(caseRepo.findById(id).isEmpty()) {
            throw new CaseIsNotException();
        }
        for(int i = id; i < caseRepo.count(); i++) {
            Case newCase = new Case();
            newCase.setId(i);
            newCase.setValue(caseRepo.findById(i + 1).get().getValue());
            newCase.setDeadLine(caseRepo.findById(i + 1).get().getDeadLine());
            newCase.setUser(caseRepo.findById(i + 1).get().getUser());
            caseRepo.save(newCase);
        }
        caseRepo.deleteById((int)caseRepo.count());
    }

    @Override
    public void updateCase(Case newCase) throws CaseIsNotException {
        if(caseRepo.findById(newCase.getId()).isEmpty()) {
            throw new CaseIsNotException();
        }
        caseRepo.save(newCase);
    }

    @Override
    public List<Case> getUserCasesList(String apiKey) {
        return caseRepo.findByApiKey(apiKey);
    }

    @Override
    public void deleteAllUserCases(String apiKey) {
        List<Case> caseList = new ArrayList<>(getUserCasesList(apiKey));
        while (!caseList.isEmpty()) {
            try {
                deleteCase(caseList.get(0).getId());
            }
            catch (Exception e) {
            }
            caseList.clear();
            caseList.addAll(getUserCasesList(apiKey));
        }
    }
}
