package com.docsarea.utility;

import com.github.f4b6a3.ulid.UlidCreator;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

public class UlidGenerator implements IdentifierGenerator {
    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) {
        return UlidCreator.getUlid().toString() ;
    }
}
