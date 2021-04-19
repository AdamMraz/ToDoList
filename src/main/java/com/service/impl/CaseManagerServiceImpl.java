package com.service.impl;

import com.model.Case;
import com.repository.CaseRepo;
import com.service.CaseManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CaseManagerServiceImpl implements CaseManagerService {


    private final CaseRepo caseRepo;

    @Override
    public List<Case> getCasesList() {
        return caseRepo.findAll();
    }

    @Override
    public int addCase(Case newCase) {
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
    public void updateCasesList(List<Case> casesList) {
        for(Case newCase : casesList) {
            if(!updateCase(newCase)) {
                addCase(newCase);
            }
        }
    }

    @Override
    public Case getCase(int id) {
        try {
            return caseRepo.findById(id).get();
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean deleteCase(int id) {
        if(caseRepo.findById(id).isEmpty()) {
            return false;
        }
        for(int i = id; i < caseRepo.count(); i++) {
            Case newCase = new Case();
            newCase.setId(i);
            newCase.setValue(caseRepo.findById(i + 1).get().getValue());
            newCase.setDeadLine(caseRepo.findById(i + 1).get().getDeadLine());
            caseRepo.save(newCase);
        }
        caseRepo.deleteById((int)caseRepo.count());
        return true;
    }

    @Override
    public boolean updateCase(Case newCase) {
        if(caseRepo.findById(newCase.getId()).isEmpty()) {
            return false;
        }
        caseRepo.save(newCase);
        return true;
    }
}
