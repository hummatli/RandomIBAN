# RandomIBAN
Java library for generate and validate random International Bank Account Numbers 


Sample Example to generate random IBAN :
```java
RandomIBAN randomIBAN = new RandomIBAN("DE", "18n", 22);
String randomIban1=randomIBAN.generate(); 
String randomIban2=randomIBAN.generate();
String randomIban3=randomIBAN.generate();
```

Sample Example to validate Iban and uniqueness of the generated IBAN: 
```java
RandomIBAN.isValid("DE89370400440532013000");
```
