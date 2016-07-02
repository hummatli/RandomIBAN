
/*
 Copyright [2016] [Fariz]

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
package com.random;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 *
 * @author fariz.aghayev
 */
public class RandomIBAN {

    private final int lengthIBAN;
    private final String codeCountry;
    private final String bbanFormat;

    private String checkDigits = "";
    private String iban = "";

    public RandomIBAN(String codeCountry, String bbanFormat, int lengthIBAN) {
        this.codeCountry = codeCountry;
        this.lengthIBAN = lengthIBAN;
        this.bbanFormat = bbanFormat;
    }
    

    private void generatingCheckDigits(String iban) {


        /*
         Calculate mod-97 of the new number, which results in the remainder
         Subtract the remainder from 98, and use the result for the two check digits. If the result is a single digit number, pad it with a leading 0 to make a two-digit number
         */
        BigInteger bi = iban2BigInteger(iban);
        int remainder = bi.mod(new BigInteger("97")).intValue();

        checkDigits = String.valueOf(98 - remainder);
        if (checkDigits.length() == 1) {
            checkDigits = "0" + checkDigits;
        }
    }

    private static BigInteger iban2BigInteger(String iban) {

        /*
         Move the four initial characters to the end of the string
         Replace the letters in the string with digits, expanding the string as necessary, such that A or a = 10, B or b = 11, and Z or z = 35. Each alphabetic character is therefore replaced by 2 digits
         Convert the string to an integer 
         */
        iban = iban.substring(4) + iban.substring(0, 4);

        StringBuilder numericIBAN = new StringBuilder();
        for (int i = 0; i < iban.length(); i++) {
            if (Character.isDigit(iban.charAt(i))) {
                numericIBAN.append(iban.charAt(i));
            } else {
                numericIBAN.append(Character.getNumericValue(Character.toUpperCase(iban.charAt(i))));
            }
        }

        return new BigInteger(numericIBAN.toString());
    }

    public int getLengthIBAN() {
        return lengthIBAN;
    }

    public String getCodeCountry() {
        return codeCountry;
    }

    public static boolean isValid(String iban) {

        /*
         Check that the total IBAN length is correct as per the country. If not, the IBAN is invalid
         Move the four initial characters to the end of the string
         Replace each letter in the string with two digits, thereby expanding the string, where A = 10, B = 11, ..., Z = 35
         Interpret the string as a decimal integer and compute the remainder of that number on division by 97
         If the remainder is 1, the check digit test is passed and the IBAN might be valid.
         */
        if (iban == null || iban.isEmpty()) {
            return false;
        }

        iban = iban.replace("-", "");
        iban = iban.replace(" ", "");
        iban = iban.replace("\t", "");

        if (iban.length() > 34) {
            return false;
        }
        RandomIBAN ra = CountriesIBANFormat.getInstance().getIbanFormatByCountry(iban.substring(0, 2));
        if (ra == null) {
            return false;
        }

        int validIBANLength = ra.getLengthIBAN();
        if (validIBANLength < 4) {
            return false;
        }

        if (iban.length() != validIBANLength) {
            return false;
        }

        BigInteger numericIBAN = iban2BigInteger(iban);

        int checkDigit = numericIBAN.mod(new BigInteger("97")).intValue();
        return checkDigit == 1;
    }

    public String generate() {
        RandomBBAN rb = new RandomBBAN.Builder().fromString(bbanFormat).build();

        if (!rb.getRandomBBAN().isEmpty()) {
            //Replace the two check digits by 00 (e.g. GB00 for the UK)
            iban = codeCountry + "00" + rb.getRandomBBAN();
            checkDigits = "";
            //generate Check digit 
            generatingCheckDigits(iban);
            iban = codeCountry + checkDigits + rb.getRandomBBAN();

        }
        return iban;
    }

    public static void main(String[] args) {
        RandomIBAN randomIBAN = CountriesIBANFormat.getInstance().getIbanFormatByCountry("DE");

        if (randomIBAN != null) {
            System.out.println(randomIBAN.generate());
        } else {
            System.out.println("Country code not exists or not exist in ResourceBundle file");
        }

        randomIBAN = CountriesIBANFormat.getInstance().getIbanFormatByCountry("AT");
        if (randomIBAN != null) {
            System.out.println(randomIBAN.generate());
        } else {
            System.out.println("Country code not exists or not exist in ResourceBundle file");
        }

        randomIBAN = CountriesIBANFormat.getInstance().getIbanFormatByCountry("NL");
        if (randomIBAN != null) {
            System.out.println(randomIBAN.generate());
        } else {
            System.out.println("Country code not exists or not exist in ResourceBundle file");
        }

        ArrayList<String> uniqueIbans = new ArrayList<>();
        RandomIBAN randomIBAN_NL = CountriesIBANFormat.getInstance().getIbanFormatByCountry("NL");
        for (int i = 0; i < 100; i++) {
            String iban = randomIBAN_NL.generate();
            System.out.println("iban : " + iban +" isValid : "+RandomIBAN.isValid(iban));

            if (uniqueIbans.contains(iban)) {
                System.out.println("Ouups iban already exists ... ");
            }
            uniqueIbans.add(iban);
        }
    }

}
