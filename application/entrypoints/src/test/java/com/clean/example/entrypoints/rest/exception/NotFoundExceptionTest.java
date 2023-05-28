package com.clean.example.entrypoints.rest.exception;


import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.assertj.core.api.Assertions.assertThat;

public class NotFoundExceptionTest {

    @Test
    public void representsHttp404() {
        ResponseStatus responseStatus = NotFoundException.class.getAnnotation(ResponseStatus.class);
        assertThat(responseStatus.value()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}