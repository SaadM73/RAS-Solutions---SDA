
package application;

import Member.*;
import Staff.*;
import Admin.*;

public class SessionManager 
{
    private static Member loggedInMember;

    private static SessionManager sessionmanager;
    
    private SessionManager() {
        // Private constructor to prevent instantiation
    }
    
    public static SessionManager getInstance()
    {
    	return sessionmanager;
    }

    public static void setLoggedInMember(Member member) {
        loggedInMember = member;
    }

    public static Member getLoggedInMember() {
        return loggedInMember;
    }

    public static boolean isLoggedIn() {
        return loggedInMember != null;
    }

    public static void logout() {
        loggedInMember = null;
    }
}
