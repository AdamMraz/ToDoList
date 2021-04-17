package main.com.service;

import main.com.dto.CaseDTO;
import main.com.model.Case;

public class ConverterDtoToModel {
    public static Case ConverterDtoToModel(CaseDTO caseDTO) {
        return Case.builder()
                .id(Integer.parseInt(caseDTO.getId()))
                .value(caseDTO.getValue())
                .deadLine(caseDTO.getDeadLine())
                .build();
    }
}
