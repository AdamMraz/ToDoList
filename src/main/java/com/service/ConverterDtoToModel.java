package com.service;

import com.dto.CaseDTO;
import com.model.Case;

public class ConverterDtoToModel {
    public static Case ConverterDtoToModel(CaseDTO caseDTO) {
        try {
            return Case.builder()
                    .id(Integer.parseInt(caseDTO.getId()))
                    .value(caseDTO.getValue())
                    .deadLine(caseDTO.getDeadLine())
                    .build();
        }
        catch (Exception e) {
            return Case.builder()
                    .id(0)
                    .value(caseDTO.getValue())
                    .deadLine(caseDTO.getDeadLine())
                    .build();
        }
    }
}
