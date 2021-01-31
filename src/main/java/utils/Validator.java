package utils;

import java.util.ArrayList;

import static utils.Constants.*;

public class Validator {

    private boolean nullOrEmptyStringCheck(String param){
        boolean res = false;
        if(param.isEmpty() || param == null){
            res = true;
        }
        return res;
    }

    private boolean stringContainsOnlyLetters(String param){

        return !param.matches("[a-zA-Z]+");
    }

    private boolean stringContainsOnlyDigits(String param){
        return !param.matches("[0-9]+");
    }

    private boolean verifyStringLength(String actual, int expected){
            return  actual.length() < expected;
    }

    public ArrayList<String> checkFieldForErrors(String name) {
        ArrayList<String> listOfErrors = new ArrayList <>();

        if(this.nullOrEmptyStringCheck(name)) {
            listOfErrors.add("Input field " + EMPTY_OR_NULL_MESSAGE);

        }
        if(this.stringContainsOnlyLetters(name)) {
            listOfErrors.add(ERROR_MESSAGE_LETTERS);
        }
        if(this.verifyStringLength(name, EXPECTED_MINIMAL_LENGTH)) {
            listOfErrors.add(String.format(ERROR_MESSAGE_STRING_LENGTH, name.length(), EXPECTED_MINIMAL_LENGTH));
        }
        return listOfErrors;
    }
}
