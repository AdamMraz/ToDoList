package com.rest;

import com.dto.CaseDTO;
import com.model.Case;
import com.service.CaseManagerService;
import com.service.ConverterDtoToModel;
import com.service.exceptions.CaseIsNotException;
import com.service.exceptions.MethodInNotAllowedException;
import com.service.exceptions.NullDateException;
import com.service.responces.annotations.CaseExceptionHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CaseExceptionHandler
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
    public ResponseEntity addCase(CaseDTO newCase) throws NullDateException {
        if (newCase.getValue() == null) {
            throw new NullDateException();
        }
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
    public ResponseEntity updateCasesList(CaseDTO caseDTOList) throws NullDateException {
        List<Case> caseList = new ArrayList<>();
        String[] id = caseDTOList.getId().split(",");
        String[] value = caseDTOList.getValue().split(",");
        String[] deadLine = caseDTOList.getDeadLine().split(",");
        if ((id.length != value.length) || (value.length != deadLine.length)) {
            throw new NullDateException();
        }
        for (int i = 0; i < id.length; i++) {
            Case newCase = new Case();
            try {
                newCase.setId(Integer.parseInt(id[i]));
            }
            catch (NumberFormatException e) {
                newCase.setId(-1);
            }
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
    public ResponseEntity getCase(@PathVariable int id) throws Exception {
        Case newCase = caseManagerService.getCase(id);
        if (newCase == null) {
            throw new CaseIsNotException();
        }
        return new ResponseEntity(newCase, HttpStatus.OK);
    }

    //Удаление дела по id
    @DeleteMapping(value = "{id}")
    @ApiOperation(value = "Удаление дела по id")
    public ResponseEntity deleteCase(@PathVariable int id) throws CaseIsNotException {
        if(caseManagerService.deleteCase(id)) {
            return new ResponseEntity(HttpStatus.OK);
        }
        throw new CaseIsNotException();
    }

    //Метод запрещён
    @PostMapping(value = "{id}")
    public ResponseEntity addCase(@PathVariable int id) throws MethodInNotAllowedException {
        throw new MethodInNotAllowedException();
    }

    //Обновление дела по id
    @PutMapping(value = "{id}")
    @ApiOperation(value = "Обновление дела по id")
    public ResponseEntity putCase(@PathVariable int id, Case newCase) throws CaseIsNotException {
        newCase.setId(id);
        if(caseManagerService.updateCase(newCase)) {
            return new ResponseEntity(HttpStatus.OK);
        }
        throw new CaseIsNotException();
    }
}