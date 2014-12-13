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

@WebServlet("/AppResponse")
public class AppResponse extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// TODO: deze moeten in servlet 1 komen, als static
	public String receivedUsername;
	public String receivedUuid;

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
            receivedUsername = receivedStringArray[0];
            receivedUuid = receivedStringArray[1];
            System.out.println(receivedUsername + " : " + receivedUuid);
 
        } catch (IOException e) {
 
 
            try{
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().print(e.getMessage());
                response.getWriter().close();
            } catch (IOException ioe) {
            }
        }   
	}
}
