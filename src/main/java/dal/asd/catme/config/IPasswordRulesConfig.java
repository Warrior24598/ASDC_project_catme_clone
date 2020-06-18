package dal.asd.catme.config;

public interface IPasswordRulesConfig {
	
	public boolean validateMinimumLength(String currentPassword,int minimumLength);
	public boolean validateMaximumLength(String currentPassword,int maximumLength);
	public boolean validateMinimumUpperCase(String currentPassword,int mininmumUpperLength);
	public boolean validateMinimumLowerCase(String currentPassword,int mininmumLowerLength);
	public boolean validateMinimumSymbolsSpecialCharacters(String currentPassword,int minimumSymbolSpecialLength);
	public boolean checkNotAllowedCharacters(String currentPassword,String regExpression);
	

}
