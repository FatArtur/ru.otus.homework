package ru.otus.crm.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PhoneDto {
    private Long id;
    private String number;
}
