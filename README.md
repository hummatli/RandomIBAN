# RandomIBAN
Java library for generate and validate random International Bank Account Numbers 

The library provide functionality to generate valid RANDOM IBAN's for different countries. Valid in this context means syntactically valid according to the ISO standard. At the beginning, library to support Germany, Austria and the Netherlands.

Sample Example to generate random IBAN :
```java
RandomIBAN randomIBAN = new RandomIBAN("DE", "18n", 22);
String randomIban1=randomIBAN.generate(); 
String randomIban2=randomIBAN.generate();
String randomIban3=randomIBAN.generate();
```

Sample Example to test valid Iban and uniqueness of the generated IBAN: 
```java
RandomIBAN.isValid("DE89370400440532013000"); //to test valid

ArrayList<String> uniqueIbans = new ArrayList<>(); // to test uniqueness random iban
RandomIBAN randomIBAN_NL = CountriesIBANFormat.getInstance().getIbanFormatByCountry("NL");
        for (int i = 0; i < 100; i++) {
            String iban = randomIBAN_NL.generate();
            System.out.println("iban : " + iban +" isValid : "+RandomIBAN.isValid(iban));
            
            if (uniqueIbans.contains(iban)) {
                System.out.println("Ouups iban already exists ... ");
            }
            uniqueIbans.add(iban);
        }
```
####References
https://en.wikipedia.org/wiki/International_Bank_Account_Number
