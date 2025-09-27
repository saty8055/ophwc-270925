package com.gcs.ophwc.services.util;

public class Validations {

	public boolean nameValidation(String str){
            boolean valid = true;
            if (str.length() > 32 || str.length() < 8){
                    System.out.println("Username should be less than 32 and more than 8 characters in length.");
                    valid = false;
            }
		/*
		 * if (password.indexOf(userName) > -1) {
		 * System.out.println("Username Should not be same as user name"); valid =
		 * false; }
		 */
            String upperCaseChars = "(.*[A-Z].*)";
            if (!str.matches(upperCaseChars )){
                    System.out.println("Username should contain atleast one upper case alphabet");
                    valid = false;
            }
            
            String lowerCaseChars = "(.*[a-z].*)";
            if (!str.matches(lowerCaseChars )){
                    System.out.println("Username should contain atleast one lower case alphabet");
                    valid = false;
            }
            
            String numbers = "(.*[0-9].*)";
            if (!str.matches(numbers )){
                    System.out.println("Username should contain atleast one number.");
                    valid = false;
            }
			return valid;
    }
	
	public boolean passwordValidation(String password){
            boolean valid = true;
            if (password.length() > 15 || password.length() < 8)
            {
                    System.out.println("Password should be less than 15 and more than 8 characters in length.");
                    valid = false;
            }
            /*if (password.indexOf(userName) > -1)
            {
                    System.out.println("Password Should not be same as user name");
                    valid = false;
            }*/
            String upperCaseChars = "(.*[A-Z].*)";
            if (!password.matches(upperCaseChars ))
            {
                    System.out.println("Password should contain atleast one upper case alphabet");
                    valid = false;
            }
            String lowerCaseChars = "(.*[a-z].*)";
            if (!password.matches(lowerCaseChars ))
            {
                    System.out.println("Password should contain atleast one lower case alphabet");
                    valid = false;
            }
            String numbers = "(.*[0-9].*)";
            if (!password.matches(numbers ))
            {
                    System.out.println("Password should contain atleast one number.");
                    valid = false;
            }
            String specialChars = "(.*[,~,!,@,#,$,%,^,&,*,(,),-,_,=,+,[,{,],},|,;,:,<,>,/,?].*$)";
            if (!password.matches(specialChars ))
            {
                    System.out.println("Password should contain atleast one special character");
                    valid = false;
            }
            return valid;		
    }
	
	public boolean emailIdValidation(String emailId){
            boolean valid = true;
            
            String specialChars = "(.*[@].*$)";
            if (!emailId.matches(specialChars ))
            {
                    System.out.println("Email Id should contain @ special character");
                    valid = false;
            }
            return valid;
			
    }
	public boolean phoneNoValidation(String phoneNumber){
        boolean valid = true;
        
        String regexStr = "^[0-9]{10}$";
        if (!phoneNumber.matches(regexStr ))
        {
                System.out.println("Mobile Number Should contain only Numbers and must be 10 digits only");
                valid = false;
        }
        return valid;
		
}
}
