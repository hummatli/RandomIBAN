package com.random.iban;

import com.random.CountriesIBANFormat;
import com.random.RandomIBAN;
import java.util.ArrayList;
import junit.framework.TestCase;

public class IBANTest extends TestCase {

    public IBANTest(String sTestName) {
        super(sTestName);
    }

    public void testIsIBANValid() {
        assertTrue(RandomIBAN.isValid("DE89 3704 0044 0532 0130 00"));
        assertTrue(RandomIBAN.isValid("NL39 RABO 0300 0652 64"));
        assertTrue(RandomIBAN.isValid("DE89370400440532013000"));
    }

    public void testIsRandomIBANUniquenessValid() {
        RandomIBAN randomIBAN_DE = CountriesIBANFormat.getInstance().getIbanFormatByCountry("DE");
        ArrayList<String> uniqueIbans = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            String iban = randomIBAN_DE.generate();
            System.out.println("iban : " + iban);
            assertTrue(RandomIBAN.isValid(iban));
            if (uniqueIbans.contains(iban)) {
                assertTrue(false);
            }
            uniqueIbans.add(iban);
        }
        RandomIBAN randomIBAN_NL = CountriesIBANFormat.getInstance().getIbanFormatByCountry("NL");
        for (int i = 0; i < 100; i++) {
            String iban = randomIBAN_NL.generate();
            System.out.println("iban : " + iban);
            assertTrue(RandomIBAN.isValid(iban));
            if (uniqueIbans.contains(iban)) {
                assertTrue(false);
            }
            uniqueIbans.add(iban);
        }
        assertTrue(true);
    }

    public void testIsIBANValidTooShort() {
        assertFalse(RandomIBAN.isValid("NL39 RABO 0300 0652 6"));
    }

    public void testIsIBANValidTooLong() {
        assertFalse(RandomIBAN.isValid("NL39 RABO 0300 0652 643"));
    }

    public void testIsIBANValidWrongAccountNumbers() {
        assertFalse(RandomIBAN.isValid("NL39 RABO 3300 0652 643"));
    }

    public void testIsIBANValidWrongCountryCode() {
        assertFalse(RandomIBAN.isValid("AA611904300234A73201"));
    }

}
