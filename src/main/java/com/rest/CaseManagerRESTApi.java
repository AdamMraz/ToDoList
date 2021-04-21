package com.rest;

import com.dto.CaseDTO;
import com.model.Case;
import com.service.CaseManagerService;
import com.service.ConverterDtoToModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/case/")
@Api(value = "Case Manager", description = "Api для работы с делами")
public class CaseManagerRESTApi {
    private final CaseManagerService caseManagerService;

    @Autowired
    public CaseManagerRESTApi(CaseManagerService caseManagerService) {
        this.caseManagerService = caseManagerService;
    }

    //Получение списка всех дел
    @GetMapping
    @ApiOperation(value = "Получение списка дел")
    public ResponseEntity getCasesList() {
        return ResponseEntity.ok(caseManagerService.getCasesList());
    }

    //Создание нового дела
    @PostMapping
    @ApiOperation(value = "Создание нового дела")
    public ResponseEntity addCase(CaseDTO newCase) {
        int id = caseManagerService.addCase(ConverterDtoToModel.ConverterDtoToModel(newCase));
        return new ResponseEntity(id, HttpStatus.OK);
    }

    //Удаление всех дел
    @DeleteMapping
    @ApiOperation(value = "Удаление всех дел")
    public ResponseEntity deleteAllCases() {
        caseManagerService.deleteAllCases();
        return new ResponseEntity(HttpStatus.OK);
    }

    //Массовое обновление списка дел
    @PutMapping
    @ApiOperation(value = "Массовое обновление списка дел")
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
    @ApiOperation(value = "Возврад дела по id")
    public ResponseEntity getCase(@PathVariable int id) {
        Case newCase = caseManagerService.getCase(id);

        if (newCase == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(newCase, HttpStatus.OK);
    }

    //Удаление дела по id
    @DeleteMapping(value = "{id}")
    @ApiOperation(value = "Удаление дела по id")
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
    @ApiOperation(value = "Обновление дела по id")
    public ResponseEntity putCase(@PathVariable int id, Case newCase) {
        newCase.setId(id);
        if(caseManagerService.updateCase(newCase)) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}