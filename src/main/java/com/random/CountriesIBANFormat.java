
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

import java.util.Enumeration;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 *
 * @author fariz.aghayev
 */
public final class CountriesIBANFormat {

    private HashMap<String, RandomIBAN> ibanFormats = null;
    private static CountriesIBANFormat instance = null;

    private CountriesIBANFormat() {
        ibanFormats = new HashMap<>();
        initFromResourse();
    }

    public void initFromResourse() {  //TO load all exist country format

        if (ibanFormats != null) {

            ResourceBundle rb = ResourceBundle.getBundle(CountriesIBANFormat.class.getCanonicalName());

            Enumeration<String> keys = rb.getKeys();
            while (keys.hasMoreElements()) {
                String countyCode = keys.nextElement();
                String[] bbanformat_length = rb.getString(countyCode).trim().split("_");

                if (bbanformat_length.length > 1) {
                    String bbanFormat = bbanformat_length[0];
                    int length = Integer.valueOf(bbanformat_length[1].trim());
                    RandomIBAN randomIBAN = new RandomIBAN(countyCode, bbanFormat, length);
                    ibanFormats.put(countyCode, randomIBAN);
                }

            }

        }
    }

    public RandomIBAN get(String codeCountry) {
        return ibanFormats.get(codeCountry);
    }

    public static CountriesIBANFormat getInstance() {
        if (instance == null) {
            instance = new CountriesIBANFormat();
        }
        return instance;
    }

    public RandomIBAN getIbanFormatByCountry(String countyCode) {
        return ibanFormats.get(countyCode);
    }

}
