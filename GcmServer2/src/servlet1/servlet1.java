package servlet1;

import database.UserDao;
import gcmserver.App;
import gcmserver.Content;
import gcmserver.PostGcm;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class uuid
 */
@WebServlet("/servlet1")
public class servlet1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servlet1() {
        super();
        // TODO Auto-generated constructor stub
    }

	public static String receivedUsername;
	public static String receivedUuid;
	public static String receivedRegId;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//static voor testing
    	String username = "isaac";
    	//einde
    	
    	UserDao userDao = new UserDao();
    	userDao.createUuid(username);
    	
    	System.out.println( "Sending POST to GCM" );
        String apiKey = "AIzaSyC3clKuTxILxby8euNiyO9dqTJy2wqCWcg";
        
        Content content = App.createContent(username);
        PostGcm.post(apiKey, content);
        
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
