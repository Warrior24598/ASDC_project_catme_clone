package dal.asd.catme.password;

import dal.asd.catme.accesscontrol.User;
import dal.asd.catme.exception.CatmeException;

import java.sql.Connection;

public interface IPasswordDao
{

    public void resetPassword(User u, Connection con) throws CatmeException;

    public String readBannerIdFromToken(String token) throws CatmeException;

    public void generatePasswordResetToken(User u, String token) throws CatmeException;

    public void removeToken(String bannerId);

    public boolean matchWithPasswordHistory(String bannerId, String password) throws CatmeException;

    public void deleteOverLimitPasswords(String bannerId) throws CatmeException;
}