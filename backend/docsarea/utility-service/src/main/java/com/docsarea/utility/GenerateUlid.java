package com.docsarea.utility;

import org.hibernate.annotations.ValueGenerationType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@ConditionalOnClass(GenerateUlid.class)
@ValueGenerationType(generatedBy = UlidGenerator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface GenerateUlid {

}
