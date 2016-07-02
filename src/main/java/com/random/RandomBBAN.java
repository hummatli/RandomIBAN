
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

/**
 *
 * @author fariz.aghayev
 */
public class RandomBBAN {

    /*
     The BBAN format column shows the format of the BBAN part of an IBAN in terms of upper case alpha characters (A–Z) denoted by "a",
     numeric characters (0–9) denoted by "n" and mixed case alphanumeric characters (a–z, A–Z, 0–9) denoted by "c". 
     For example, the Bulgarian BBAN (4a,6n,8c) consists of 4 alpha characters,
     followed by 6 numeric characters, then by 8 mixed-case alpha-numeric characters*/

    private int alphaCharacters = 0;
    private int numericCharacters = 0;
    private int alphaNumericCharacters = 0;

    private String bbanFormat = "";  //ex. 4a,6n,8c
    private String randomBBAN = "";

    @Override
    public String toString() {
        return getBBANFormat() + " -> " + getRandomBBAN();
    }

    public String getRandomBBAN() {
        return randomBBAN;
    }

    public String getBBANFormat() {
        return bbanFormat;
    }

    public static class Builder {

        private final RandomBBAN instance = new RandomBBAN();

        public Builder countAlpha(final int alphaCount) {
            instance.alphaCharacters = alphaCount;

            if (instance.bbanFormat.isEmpty()) {
                instance.bbanFormat = alphaCount + "a";
            } else {
                instance.bbanFormat += "," + instance.alphaCharacters + "a";

            }

            instance.randomBBAN += Utils.randomAlphaCharacters(alphaCount);
            return this;
        }

        public Builder countAlphaNumeric(final int alphaNumericCount) {
            instance.alphaNumericCharacters = alphaNumericCount;

            if (instance.bbanFormat.isEmpty()) {
                instance.bbanFormat = instance.alphaNumericCharacters + "c";
            } else {
                instance.bbanFormat += "," + instance.alphaNumericCharacters + "c";
            }

            instance.randomBBAN += Utils.randomAlphaNumericCharacters(alphaNumericCount);
            return this;
        }

        public Builder countNumeric(final int numericCount) {
            instance.numericCharacters = numericCount;

            if (instance.bbanFormat.isEmpty()) {
                instance.bbanFormat = instance.numericCharacters + "n";
            } else {
                instance.bbanFormat += "," + instance.numericCharacters + "n";
            }

            instance.randomBBAN += Utils.randomNumericCharacters(numericCount);
            return this;
        }

        public Builder fromString(String bbanFormat) {
            boolean validformat = false;
            if (bbanFormat != null && !bbanFormat.isEmpty()) {

                String[] bbanParts = bbanFormat.trim().split(",");
                for (String part : bbanParts) {
                    if (part.length() > 1) {
                        if (part.trim().endsWith("a")) {
                            instance.alphaCharacters = Integer.valueOf(part.trim().replace("a", "").trim());
                            instance.randomBBAN += Utils.randomAlphaCharacters(instance.alphaCharacters);
                            validformat = true;
                        } else if (part.trim().endsWith("n")) {
                            instance.numericCharacters = Integer.valueOf(part.trim().replace("n", "").trim());
                            instance.randomBBAN += Utils.randomNumericCharacters(instance.numericCharacters);
                            validformat = true;
                        } else if (part.trim().endsWith("c")) {
                            instance.alphaNumericCharacters = Integer.valueOf(part.trim().replace("c", "").trim());
                            instance.randomBBAN += Utils.randomAlphaNumericCharacters(instance.alphaNumericCharacters);
                            validformat = true;
                        } else {
                            validformat = false;
                        }

                    } else {
                        validformat = false;
                        break;
                    }
                }
                if (validformat) {
                    instance.bbanFormat = bbanFormat;
                } else {
                    instance.randomBBAN = "";
                }
            }

            return this;
        }

        public RandomBBAN build() {
            return instance;
        }
    }
}
