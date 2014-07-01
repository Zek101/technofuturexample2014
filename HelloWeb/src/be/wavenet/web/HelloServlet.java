package be.wavenet.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		ServletContext servletContext = this.getServletContext();
		Long count = (Long)servletContext.getAttribute("count");
		if(count==null) count = 0L;
		count++;
				
		servletContext.setAttribute("count", count);
		
		String username = null;
		
		
		HttpSession session = request.getSession();
		username = (String)session.getAttribute("username");
		if(username==null) // username not found in session
			username = request.getParameter("username");
		
		servletContext.log("HelloServlet - "+username+" - "+count);

		if(username==null || username.isEmpty()){
			//response.sendRedirect("http://www.google.be");
			
			String error = "Username is required !";
			request.setAttribute("errorMessage", error);
			
			request.getRequestDispatcher("/Error")
				.forward(request, response);
			
		}
		else{
			session.setAttribute("username", username);
			
			PrintWriter out = response.getWriter();
	
			SimpleDateFormat dateFormat = new SimpleDateFormat("EEEEE dd MMMMM yyyy");
			SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
			Date now = new Date();
	
			String formattedDate = dateFormat.format(now);
			String formattedTime = timeFormat.format(now);
			
			out.println("<html><head><title>Hello Servlet</title></head>");
			out.println("<body><h1>Hello "+ username +"</h1>");
			out.println("<p>Nous sommes le : " + formattedDate + "</p>");
			out.println("<p>Il est " + formattedTime + "</p>");
			out.println("<p>Server name : "+request.getServerName()+"</p>");
			out.println("<p>Server port : "+request.getServerPort()+"</p>");
			out.println("<p>Servlet Path : "+request.getServletPath()+"</p>");
			out.println("<p>User-Agent : "+request.getHeader("User-Agent")+"</p>");
			out.println("</body></html>");
		}
	}

}
