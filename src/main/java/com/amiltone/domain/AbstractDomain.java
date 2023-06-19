package com.amiltone.domain;

import lombok.Data;

import java.util.UUID;

@Data
public abstract class AbstractDomain {

    private UUID id;
}
