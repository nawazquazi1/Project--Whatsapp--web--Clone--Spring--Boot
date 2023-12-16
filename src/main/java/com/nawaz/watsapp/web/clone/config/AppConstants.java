package com.nawaz.watsapp.web.clone.config;

public class AppConstants {
	
	public static final String SUCCESSFUL_VERIFICATION_RESPONSE = "Verification is successfull !";
	public static final String UNSUCCESSFUL_VERIFICATION_RESPONSE = "Verification is not successfull !";
	
	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60*100;
	
	public static final Integer NORMAL_USER = 502;
	public static final String []PUBLIC_URLS={"/chat/authentication/**", "/v3/api-docs/" , "/v2/api-docs/", "/swagger-ui/**", "/webjars/**"};
	public static final Integer ADMIN_USER = 501;

	public static final String SECRET_KEY = "QrY/tnOqtPjDa5gSoHQ2dA0uQwSLKidLHJfU/l9O2Hw9/eF5rOzIIE9+a2OmbTnF";

}