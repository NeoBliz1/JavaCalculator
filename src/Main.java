import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class Main {
    //calculation main program

    //handler returns true if the element exists in one of the two arrays
    static Boolean presentInArrayList(List<String> numericalArrList,
                                      List<String> romanNumeralsArrList,
                                      List<String> arabicNumeralsArrList) {
        if (new HashSet<>(romanNumeralsArrList).containsAll(numericalArrList)) {
            return true;
        } else return new HashSet<>(arabicNumeralsArrList).containsAll(numericalArrList);

    }

    static List<String> convertRomanToArabicNumbers(List<String> numericalArrList, List<String> romanNumeralsArrList) {
        List<String> convertedNumeralsArrList = new ArrayList<>();
        for (String s : numericalArrList) {
            for (int u = 0; u < romanNumeralsArrList.size(); u++) {
                if (Objects.equals(romanNumeralsArrList.get(u), s)) {
                    convertedNumeralsArrList.add(Integer.toString(u + 1));
                }
            }
        }

        return convertedNumeralsArrList;
    }

    static String calculateResult(String operatorsStr, int firstNumber, int secondNumber) {
        String result = "";
        switch (operatorsStr) {
            case "+" -> result = Integer.toString(firstNumber + secondNumber);
            case "-" -> result = Integer.toString(firstNumber - secondNumber);
            case "*" -> result = Integer.toString(firstNumber * secondNumber);
            case "/" -> result = Integer.toString(firstNumber / secondNumber);
        }
        return result;
    }

    static String convertArabicToRomanNumber(String result) {
        int numberResult = Integer.parseInt(result);
        //hasMap of roman numbers
        SortedMap<Integer, String> romNumMap = new TreeMap<>(java.util.Collections.reverseOrder());

        romNumMap.put(1, "I");
        romNumMap.put(4, "IV");
        romNumMap.put(5, "V");
        romNumMap.put(9, "IX");
        romNumMap.put(10, "X");
        romNumMap.put(40, "XL");
        romNumMap.put(50, "L");
        romNumMap.put(90, "XC");
        romNumMap.put(100, "C");

        StringBuilder romanResult = new StringBuilder();
        //iterate across the map in natural order of the keys
        while (numberResult > 0) {
            boolean cycleInProgress = true;
            for (int key : romNumMap.keySet()) {
                if (key == numberResult && cycleInProgress) {
                    romanResult.append(romNumMap.get(key));
                    numberResult = 0;
                } else if (key < numberResult && cycleInProgress) {
                    romanResult.append(romNumMap.get(key));
                    numberResult = numberResult - key;
                    cycleInProgress = false;
                }
//                System.out.println(romanResult + " " + numberResult);
            }
        }

        return romanResult.toString();
    }

    public static String calc(String input) {
        String[] romanNumeralsArr = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
        List<String> romanNumeralsArrList = Arrays.asList(romanNumeralsArr);
        //create arabicNumeralsArrList based on romanNumeralsArr
        List<String> arabicNumeralsArrList = new ArrayList<>();
        for (int i = 0; i < romanNumeralsArr.length; i++) {
            arabicNumeralsArrList.add(Integer.toString(i + 1));
        }
        //System.out.println(arabicNumeralsArrList);
        StringBuilder operatorsStr = new StringBuilder();
        //create operators matcher
        Matcher operatorsMatcher = Pattern.compile("(?!^-)[+*/\\-](\\s?-)?").matcher(input);
        //get operators form input
        while (operatorsMatcher.find()) {
            operatorsStr.append(operatorsMatcher.group(0));
        }
//        System.out.println(operatorsStr);
        // Creating a new ArrayList
        List<String> numericalArrList = new ArrayList<>();
        //create numbers matcher
        Matcher numbersMatcher = Pattern.compile("[A-Za-z0-9]+").matcher(input);
        //get numbers form input
        while (numbersMatcher.find()) {
            numericalArrList.add(numbersMatcher.group(0));
        }
//        System.out.println(numericalArrList);
        //check if numbers presents in arrays
        boolean hasPresent = presentInArrayList(numericalArrList, romanNumeralsArrList, arabicNumeralsArrList);
        String result = "";
        //if the string contains zero or more than 1 operator or the
        //input has a length of 1 or every number doesn't include in
        //the numeric arrays, then throws error
        if (operatorsStr.length() != 1 || input.length() == 1 || !hasPresent) {
            throw new java.lang.RuntimeException("the input argument is not compliant");
        } //if the input contains arabic numbers
        else if (new HashSet<>(arabicNumeralsArrList).containsAll(numericalArrList)) {
            int firstNumber = Integer.parseInt(numericalArrList.get(0));
            int secondNumber = Integer.parseInt(numericalArrList.get(1));
            result = calculateResult(operatorsStr.toString(), firstNumber, secondNumber);
        } //if the string contains roman numbers
        else if (new HashSet<>(romanNumeralsArrList).containsAll(numericalArrList)) {
            List<String> convertedRomanToArabicArrList = convertRomanToArabicNumbers(numericalArrList, romanNumeralsArrList);
            int firstNumber = Integer.parseInt(convertedRomanToArabicArrList.get(0));
            int secondNumber = Integer.parseInt(convertedRomanToArabicArrList.get(1));
            if (firstNumber < secondNumber && operatorsStr.toString().equals("-")) {
                throw new java.lang.RuntimeException("Roman numerals do not have negative values");
            } else {
                String preResult = calculateResult(operatorsStr.toString(), firstNumber, secondNumber);
                if (Integer.parseInt(preResult) == 0) {
                    throw new java.lang.RuntimeException("Roman numbers cannot be zero, the result is:" + result);
                } else {
                    result = convertArabicToRomanNumber(preResult);
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        // Using Scanner for Getting Input from User
        Scanner in = new Scanner(System.in);

        while (true) {
            String s = in.nextLine();
            System.out.println(calc(s));
        }

    }
}