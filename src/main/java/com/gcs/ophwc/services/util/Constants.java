package com.gcs.ophwc.services.util;

public interface Constants {
	public static final int 	INSERT			=	1;
	public static final int 	UPDATE			=	2;
	public static final int 	DELETE			=	3;
	
	public static final short 	ONLY_ACTIVE		=	1;
	public static final short 	ONLY_IN_ACTIVE		=	2;
	public static final short 	ALL				=	3;
	
	public static final String 	EQUAL			= "=";
	public static final String 	LESS_THAN		= "<";
	public static final String 	GREATER_THAN	= ">";
	public static final String 	LESS_THAN_OR_EQUAL = "<=";
	public static final String 	GREATER_THAN_OR_EQUAL = ">=";
	public static final String 	TRUE			= "TRUE";
	public static final String 	FALSE			= "FALSE";
	public static final String 	ON="ON";
	public static final String 	OFF="OFF";
	public static final String 	TERMINATOR=";";
	public static final String 	YES="YES";
	public static final String 	NO="NO";
	public static final int SUCCESS = 1;
	public static final int FAILURE = 0;
	public static final int VALIDATIONFAILURE = 2;

	public static final String SUCCESSMSG="success";
	public static final String FAILURESMSG="failure";
	public static final String VALIDATIONFAILUREMSG="VALIDATIONFAILURE";
	
	
	public static final String SC1="1";//sucsess
	public static final String SC0="0";// fail
	
	public static final String TTRMSGSUCESS="success";
	public  static final String TTRMSGFAIL="failed";
	public  static final String TTRMsgInvalidsession="Session invalid please login";
	public  static final String TTRMsgInvalidLogin="Please enter valid credentials";


    public static final String DIVISION_USER_TYPE = "Division User";
    public static final String CLIENT_USER_TYPE = "Client User";
    public static final String ADMIN_USER_TYPE = "Admin User";
    public static final String HO_USER_TYPE = "HO User";


}
