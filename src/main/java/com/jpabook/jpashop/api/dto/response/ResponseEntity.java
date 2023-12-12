package com.jpabook.jpashop.api.dto.response;

import lombok.Data;

@Data
public class ResponseEntity<T> {
    private final Integer code;
    private final T body;

    public ResponseEntity() {
        code = null;
        body = null;
    }

    public ResponseEntity(final T body) {
        code = 200;
        this.body = body;
    }
}
