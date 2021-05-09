package com.service;

import com.model.Case;
import com.service.exceptions.CaseIsNotException;

import java.util.List;

public interface CaseManagerService {

    //Получение списка дел
    List<Case> getCasesList();

    //Создание нового дела
    int addCase(Case newCase) throws Exception;

    //Удаление всех дел
    void deleteAllCases();

    //Возврат дела по id
    Case getCase(int id) throws CaseIsNotException;

    //Удаление дела с id
    void deleteCase(int id) throws CaseIsNotException;

    //Обновление дела по id
    void updateCase(Case newCase) throws CaseIsNotException;

    //Получение списка дел
    List<Case> getUserCasesList(String apiKey);

    //Удаление всех дел
    void deleteAllUserCases(String apiKey);
}
