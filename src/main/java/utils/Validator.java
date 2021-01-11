package utils;

public class Validator {

    public final String EmptyOrNullMessage = "can not be empty";
    public final int ExpectedLength = 3;
    public String ErrorMessage = "";

    public boolean NullOrEmptyStringCheck(String param){
        boolean res = false;
        if(param.isEmpty() || param == null){
            res = true;
        }
        return res;
    }

    public boolean ContainsOnlyLettersCheck(String param){
        return !param.chars().allMatch(Character::isLetter);
    }

    public boolean ContainsOnlyDigitsCheck(String param){
        return !param.chars().allMatch(Character::isDigit);
    }

    public boolean VerifyStringLength(String actual, int expected){
            return  actual.length() < expected;
    }

    public boolean CheckFieldForErrors(Validator validator, String name) {
        boolean ifErrorFirstName;
        ifErrorFirstName = validator.NullOrEmptyStringCheck(name);
        if(!ifErrorFirstName)
        {
            ifErrorFirstName = validator.ContainsOnlyLettersCheck(name);
            if (!ifErrorFirstName)
            {
                ifErrorFirstName = validator.VerifyStringLength(name, validator.ExpectedLength);
                if (ifErrorFirstName)
                {
                    ErrorMessage = String.format("Inserted value has %d characters. Expected minimal length is %d.",
                            name.length(), validator.ExpectedLength);
                }
            }else
            {
                ErrorMessage = "Only letters are allowed";
            }
        }else
        {
            ErrorMessage = "Input field " + validator.EmptyOrNullMessage;
        }
        return ifErrorFirstName;
    }

    public int GetNumberOfPages(int number){
       int pages = 0;
        if(number > 9){
            pages = number / 10;
            if(number % 10 != 0){
                pages+=1;
            }
        }
        return pages;
    }
}
