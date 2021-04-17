package com.service;

import com.model.Case;

import java.util.List;

public interface CaseManagerService {

    //Получение списка дел
    List<Case> getCasesList();

    //Создание нового дела
    int addCase(Case newCase);

    //Удаление всех дел
    void deleteAllCases();

    //Массовое обновление списка дел
    void updateCasesList(List<Case> casesList);

    //Возврат дела по id
    Case getCase(int id);

    //Удаление дела с id
    boolean deleteCase(int id);

    //Обновление дела по id
    boolean updateCase(Case newCase);
}
