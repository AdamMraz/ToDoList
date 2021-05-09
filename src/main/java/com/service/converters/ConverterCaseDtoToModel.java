package com.service.converters;

import com.dto.CaseDTO;
import com.model.Case;

public class ConverterCaseDtoToModel {
    public static Case ConverterDtoToModel(CaseDTO caseDTO) {
        try {
            return Case.builder()
                    .id(caseDTO.getId())
                    .value(caseDTO.getValue())
                    .deadLine(caseDTO.getDeadLine())
                    .user(ConvertUserDtoToModel.ConverterDtoToModel(caseDTO.getUser()))
                    .build();
        }
        catch (Exception e) {
            return Case.builder()
                    .id(0)
                    .value(caseDTO.getValue())
                    .deadLine(caseDTO.getDeadLine())
                    .user(ConvertUserDtoToModel.ConverterDtoToModel(caseDTO.getUser()))
                    .build();
        }
    }
}
