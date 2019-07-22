package com.ocean.cloudoauth.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ocean.cloudoauth.serialzer.BootOAuthExceptionJacksonSerializer;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;


@JsonSerialize(using = BootOAuthExceptionJacksonSerializer.class)
public class BootOAuth2Exception extends OAuth2Exception {
    public BootOAuth2Exception(String msg, Throwable t) {
        super(msg, t);
    }

    public BootOAuth2Exception(String msg) {
        super(msg);
    }
}


