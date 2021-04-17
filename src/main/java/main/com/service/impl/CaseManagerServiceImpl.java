package main.com.service.impl;

import main.com.model.Case;
import main.com.repository.CaseRepo;
import main.com.service.CaseManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaseManagerServiceImpl implements CaseManagerService {

    private final CaseRepo caseRepo;

    @Autowired
    public CaseManagerServiceImpl(CaseRepo caseRepo) {
        this.caseRepo = caseRepo;
    }

    @Override
    public List<Case> getCasesList() {
        return caseRepo.findAll();
    }

    @Override
    public void addCase(Case newCase) {
        newCase.setId((int)(caseRepo.count() + 1));
        caseRepo.save(newCase);
    }

    @Override
    public void deleteAllCases() {
        caseRepo.deleteAll();
    }

    @Override
    public void updateCasesList(List<Case> casesList) {
        casesList.forEach(caseRepo::save);
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
