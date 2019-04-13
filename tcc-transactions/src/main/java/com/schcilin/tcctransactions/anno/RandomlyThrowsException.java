package com.schcilin.tcctransactions.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: schicilin
 * Date: 2019/4/10 1:03
 * Content:
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RandomlyThrowsException {
}
