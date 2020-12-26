package main;

import main.model.NewCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import main.model.Case;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
public class ToDoListController {

    @Autowired
    private NewCaseRepository newCaseRepository;

    @GetMapping("/cases/")
    public List<Case> getList() {
        List<Case> cases = new ArrayList<Case>();
        newCaseRepository.findAll().forEach(cases::add);
        cases.forEach(System.out::println);
        return cases;
    }

    @PostMapping("/cases/")
    public int addToList(Case newCase) {
        Case newCase2 = newCaseRepository.save(newCase);
        return newCase2.getId();
    }

    @DeleteMapping("/cases/")
    public ResponseEntity deleteList() {
        newCaseRepository.deleteAll();
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/cases/")
    public ResponseEntity putList(Case newCases) {
        String[] values = newCases.getValue().split(",");
        String[] deadLines = newCases.getDeadLine().split(",");
        for (int i = 0; i < values.length; i++) {
            Case one = new Case();
            one.setValue(values[i]);
            one.setDeadLine(deadLines[i]);
            Case newCase2 = newCaseRepository.save(one);
            System.out.println(newCase2.getId());
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/cases/{id}")
    public ResponseEntity getCase(@PathVariable int id) {
        Optional<Case> newCase = newCaseRepository.findById(id);
        if (newCase.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity(newCase, HttpStatus.OK);
    }

    @DeleteMapping("/cases/{id}")
    public ResponseEntity deleteCase(@PathVariable int id) {
        newCaseRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    //Метод запрещён
    @PostMapping("/cases/{id}/")
    public ResponseEntity addCase(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(null);
    }

    //Обновление данных
    @PutMapping("/cases/{id}")
    public ResponseEntity putCase(@PathVariable int id, Case newCase) {
        newCase.setId(id);
        System.out.println(newCase.getId() + ";" + newCase.getValue() + ";" + newCase.getDeadLine());
        newCaseRepository.save(newCase);
        return getCase(id);
    }
}
