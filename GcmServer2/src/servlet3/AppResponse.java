package servlet3;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletInputStream;

import database.UserDao;

@WebServlet("/AppResponse")
public class AppResponse extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// TODO: deze moeten in servlet 1 komen, als static
	public String receivedUsername;
	public String receivedUuid;
	public String receivedRegId;

    public AppResponse() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		writer.println("Sup");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
            int length = request.getContentLength();
            byte[] input = new byte[length];
            ServletInputStream sin = request.getInputStream();
            sin.read(input);
            sin.close();
 
            String receivedString = new String(input);
            response.setStatus(HttpServletResponse.SC_OK);
            String[] receivedStringArray = receivedString.split(":");
            String method = receivedStringArray[0];
            
            if (method.equalsIgnoreCase("register")) {
            	register(receivedStringArray[1], receivedStringArray[2]);
            	System.out.println("Register request received, username: " + receivedStringArray[1] + ", RegID: " + receivedStringArray[2]);
            } else if (method.equalsIgnoreCase("login")) {
            	login(receivedStringArray[1], receivedStringArray[2]);
                System.out.println("Authentication request received: " + receivedStringArray[1] + " : " + receivedStringArray[2]);
            } else if (method.equalsIgnoreCase("decline")) { 
            	decline(receivedStringArray[1], receivedStringArray[2]);
            	System.out.println("Decline request received: " + receivedStringArray[1]);
            } else {
            	System.out.println("Error: invalid method");
            }
            
        } catch (IOException e) {
 
            try{
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().print(e.getMessage());
                response.getWriter().close();
            } catch (IOException ioe) {
            }
        }   
	}
	
	private void decline (String username, String uuid) {
		UserDao userdao = new UserDao();
		userdao.deleteUuid(username, uuid);
	}
	
	private void register (String username, String regid) {
		receivedUsername = username;
    	receivedRegId = regid;
    	UserDao userdao = new UserDao();
    	userdao.setRegId(username, regid);
	}
	
	private void login (String username, String uuid) {
		receivedUsername = username;
        receivedUuid = uuid;
	}
}
