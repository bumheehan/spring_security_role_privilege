package xyz.bumbing.domain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PrivilegeDto implements Serializable {
    private final Long id;
    private final String name;
}
