package main.com.rest;

import main.com.dto.CaseDTO;
import main.com.model.Case;
import main.com.service.CaseManagerService;
import main.com.service.ConverterDtoToModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/case/")
public class CaseManagerRESTApi {
    private final CaseManagerService caseManagerService;

    @Autowired
    public CaseManagerRESTApi(CaseManagerService caseManagerService) {
        this.caseManagerService = caseManagerService;
    }

    //Получение списка всех дел
    @GetMapping
    public ResponseEntity getCasesList() {
        return ResponseEntity.ok(caseManagerService.getCasesList());
    }

    //Создание нового дела
    @PostMapping
    public ResponseEntity addCase(CaseDTO newCase) {
        System.out.println(newCase.getId() + " " + newCase.getValue());
        caseManagerService.addCase(ConverterDtoToModel.ConverterDtoToModel(newCase));
        return new ResponseEntity(HttpStatus.OK);
    }

    //Удаление всех дел
    @DeleteMapping
    public ResponseEntity deleteAllCases() {
        caseManagerService.deleteAllCases();
        return new ResponseEntity(HttpStatus.OK);
    }

    //Массовое обновление списка дел
    @PutMapping
    public ResponseEntity updateCasesList(CaseDTO caseDTOList) {
        System.out.println(caseDTOList.getId() + " " + caseDTOList.getValue() + " " + caseDTOList.getDeadLine());
        List<Case> caseList = new ArrayList<>();
        String[] id = caseDTOList.getId().split(",");
        String[] value = caseDTOList.getValue().split(",");
        String[] deadLine = caseDTOList.getDeadLine().split(",");
        for (int i = 0; i < id.length; i++) {
            Case newCase = new Case();
            newCase.setId(Integer.parseInt(id[i]));
            newCase.setValue(value[i]);
            newCase.setDeadLine(deadLine[i]);
            caseList.add(newCase);
        }
        caseManagerService.updateCasesList(caseList);
        return new ResponseEntity(HttpStatus.OK);
    }

    //Возврат дела по id
    @GetMapping(value = "{id}")
    public ResponseEntity getCase(@PathVariable int id) {
        Case newCase = caseManagerService.getCase(id);

        if (newCase == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(newCase, HttpStatus.OK);
    }

    //Удаление дела по id
    @DeleteMapping(value = "{id}")
    public ResponseEntity deleteCase(@PathVariable int id) {
        if(caseManagerService.deleteCase(id)) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    //Метод запрещён
    @PostMapping(value = "{id}")
    public ResponseEntity addCase(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(null);
    }

    //Обновление дела по id
    @PutMapping(value = "{id}")
    public ResponseEntity putCase(@PathVariable int id, Case newCase) {
        newCase.setId(id);
        if(caseManagerService.updateCase(newCase)) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}




