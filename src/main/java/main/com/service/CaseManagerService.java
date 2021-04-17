package main.com.service;

import main.com.model.Case;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

public interface CaseManagerService {

    //Получение списка дел
    List<Case> getCasesList();

    //Создание нового дела
    void addCase(Case newCase);

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
