package com.ocean.cloudoauth.serialzer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.ocean.cloudoauth.exception.BootOAuth2Exception;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

public class BootOAuthExceptionJacksonSerializer extends StdSerializer<BootOAuth2Exception> {

    protected BootOAuthExceptionJacksonSerializer() {
        super(BootOAuth2Exception.class);
    }

    @Override
    public void serialize(BootOAuth2Exception ex, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("message", ex.getMessage());
        gen.writeStringField("data", "");
        gen.writeNumberField("timestamp", System.currentTimeMillis());
        if (ex.getAdditionalInformation() != null) {
            for (Map.Entry<String, String> entry : ex.getAdditionalInformation().entrySet()) {
                String key = entry.getKey();
                String add = entry.getValue();
                if ("code".equals(key)) {
                    gen.writeNumberField(key, new BigDecimal(add));
                } else {
                    gen.writeStringField(key, add);
                }
            }
        }
        gen.writeEndObject();
    }
}