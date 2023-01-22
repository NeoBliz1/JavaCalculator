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
        List<String> convertedNumeralsArrList = new ArrayList<String>();
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
//        enum RomNumDict = {
//            int i = 1;: "I",
//                5: "V",
//                10: "X",
//                50: "L",
//                100: "C",
//                500: "D",
//                1000: "M",
//                2000: "MM",
//                4000: "MMMM"
//		};
        return result;
    }

    public static String calc(String input) {
        String[] romanNumeralsArr = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
        List<String> romanNumeralsArrList = Arrays.asList(romanNumeralsArr);
        //create arabicNumeralsArrList based on romanNumeralsArr
        List<String> arabicNumeralsArrList = new ArrayList<String>();
        for (int i = 0; i < romanNumeralsArr.length; i++) {
            arabicNumeralsArrList.add(Integer.toString(i + 1));
        }
        //System.out.println(arabicNumeralsArrList);
        String operatorsStr = "";
        //create operators matcher
        Matcher operatorsMatcher = Pattern.compile("(?!^-)[+*/\\-](\\s?-)?").matcher(input);
        //get operators form input
        while (operatorsMatcher.find()) {
            operatorsStr = operatorsStr + operatorsMatcher.group(0);
        }
//        System.out.println(operatorsStr);
        // Creating a new ArrayList
        List<String> numericalArrList = new ArrayList<String>();
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
            result = calculateResult(operatorsStr, firstNumber, secondNumber);
        } //if the string contains roman numbers
        else if (new HashSet<>(romanNumeralsArrList).containsAll(numericalArrList)) {
            List<String> convertedRomanToArabicArrList = convertRomanToArabicNumbers(numericalArrList, romanNumeralsArrList);
            int firstNumber = Integer.parseInt(convertedRomanToArabicArrList.get(0));
            int secondNumber = Integer.parseInt(convertedRomanToArabicArrList.get(1));
            result = convertArabicToRomanNumber(calculateResult(operatorsStr, firstNumber, secondNumber));
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