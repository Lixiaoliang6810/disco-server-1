package com.miner.disco.mch.oauth.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.miner.disco.mch.exception.MchBusinessExceptionCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * @author Created by lubycoder@163.com 2019/1/10
 */

public class CustomOauthExceptionSerializer extends StdSerializer<CustomOauthException> {

    private static final long serialVersionUID = 2908498121029026342L;

    public CustomOauthExceptionSerializer() {
        super(CustomOauthException.class);
    }

    @Override
    public void serialize(CustomOauthException value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        assert response != null;
        response.setStatus(HttpServletResponse.SC_OK);

        String message = value.getMessage();
        Integer status = value.getHttpErrorCode();
        if (StringUtils.equals("Bad credentials", message)) {
            message = "用户名或密码错误";
            status = MchBusinessExceptionCode.USERNAME_OR_PASSWORD_ERROR.getCode();
        }

        gen.writeStartObject();
        gen.writeStringField("message", message);
        gen.writeStringField("status", String.valueOf(MchBusinessExceptionCode.USERNAME_OR_PASSWORD_ERROR.getCode()));
        if (value.getAdditionalInformation() != null) {
            for (Map.Entry<String, String> entry : value.getAdditionalInformation().entrySet()) {
                String key = entry.getKey();
                String add = entry.getValue();
                gen.writeStringField(key, add);
            }
        }
        gen.writeEndObject();
    }
}
