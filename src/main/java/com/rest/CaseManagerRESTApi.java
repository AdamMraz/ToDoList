package com.rest;

import com.dto.CaseDTO;
import com.model.Case;
import com.model.User;
import com.service.CaseManagerService;
import com.service.UserManagerService;
import com.service.converters.ConverterCaseDtoToModel;
import com.service.exceptions.CaseIsNotException;
import com.service.exceptions.MethodInNotAllowedException;
import com.service.responces.annotations.CaseExceptionHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CaseExceptionHandler
@RestController
@RequestMapping(value = "/api/case/")
@Api(value = "Case Manager", description = "Api для работы с делами")
@RequiredArgsConstructor
public class CaseManagerRESTApi {
    private final CaseManagerService caseManagerService;
    private final UserManagerService userManagerService;

    //Получение списка всех дел
    @GetMapping
    @ApiOperation(value = "Получение списка дел")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")
    })
    public ResponseEntity getCasesList() {
        return ResponseEntity.ok(caseManagerService.getCasesList());
    }


    //Создание нового дела
    @PostMapping
    @ApiOperation(value = "Создание нового дела")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Пользователя не найден"),
        @ApiResponse(code = 400, message = "Переданы пустые данные")
    })
    public ResponseEntity addCase(CaseDTO newCase) throws Exception {
        User user = userManagerService.findUserById(newCase.getUser().getId());
        Case resultCase = ConverterCaseDtoToModel.ConverterDtoToModel(newCase);
        resultCase.setUser(user);
        int id = caseManagerService.addCase(resultCase);
        return new ResponseEntity("", HttpStatus.OK);
    }

    //Удаление всех дел
    @DeleteMapping
    @ApiOperation(value = "Удаление всех дел")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")
    })
    public ResponseEntity deleteAllCases() {
        caseManagerService.deleteAllCases();
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping
    @ApiOperation(value = "Метод запрещён")
    @ApiResponses(value = {
            @ApiResponse(code = 405, message = "Метод запрещён")
    })
    public ResponseEntity updateCasesList() throws Exception {
        throw new MethodInNotAllowedException();
    }

    //Возврат дела по id
    @GetMapping(value = "{id}")
    @ApiOperation(value = "Возврат дела по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Неверный формат ID"),
            @ApiResponse(code = 500, message = "Дело с таким id не существует")
    })
    public ResponseEntity getCase(@PathVariable int id) throws Exception {
        Case newCase = caseManagerService.getCase(id);
        return new ResponseEntity(newCase, HttpStatus.OK);
    }

    //Удаление дела по id
    @DeleteMapping(value = "{id}")
    @ApiOperation(value = "Удаление дела по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Неверный формат ID"),
            @ApiResponse(code = 500, message = "Дело с таким id не существует")
    })
    public ResponseEntity deleteCase(@PathVariable int id) throws CaseIsNotException {
        caseManagerService.deleteCase(id);
        return new ResponseEntity("OK", HttpStatus.OK);
    }

    //Метод запрещён
    @PostMapping(value = "{id}")
    @ApiOperation(value = "Метод запрещён")
    @ApiResponses(value = {
            @ApiResponse(code = 405, message = "Метод запрещён")
    })
    public ResponseEntity addCase(@PathVariable int id) throws MethodInNotAllowedException {
        throw new MethodInNotAllowedException();
    }

    //Обновление дела по id
    @PutMapping(value = "{id}")
    @ApiOperation(value = "Обновление дела по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Неверный формат ID"),
            @ApiResponse(code = 500, message = "Дело с таким id не существует")
    })
    public ResponseEntity putCase(@PathVariable int id, CaseDTO newCase) throws Exception {
        User user = userManagerService.findUserById(newCase.getUser().getId());
        Case resultCase = ConverterCaseDtoToModel.ConverterDtoToModel(newCase);
        resultCase.setUser(user);
        resultCase.setId(id);
        caseManagerService.updateCase(resultCase);
        return new ResponseEntity(HttpStatus.OK);
    }
}