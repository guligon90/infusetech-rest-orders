package com.infusetech.rest.orders;

import java.util.Locale;

import org.junit.AfterClass;
import org.junit.BeforeClass;

public abstract class LocaleAwareUnitTest {
    private static Locale previousDefault;

    @BeforeClass
    public static void setupLocale() {
        previousDefault = Locale.getDefault();

        Locale.setDefault(Locale.forLanguageTag("pt-BR"));
    }

    @AfterClass
    public static void resetLocale() {
        Locale.setDefault(previousDefault);
    }
}
