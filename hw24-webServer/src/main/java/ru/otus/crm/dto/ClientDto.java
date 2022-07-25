package ru.otus.crm.dto;


import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class ClientDto {

    private Long id;
    private String name;
    private String address;
    private List<String> phones;
}
