package thrillio.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import thrillio.managers.UserManager;

@WebServlet(urlPatterns = { "/auth", "/auth/logout", "/auth/signup" })
public class AuthController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AuthController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("Servlet path: " + request.getServletPath());

        if (!request.getServletPath().contains("logout")) {
            // Sign-up page
            if (request.getServletPath().contains("signup")) {
                String email = request.getParameter("signup_email");
                String password = request.getParameter("signup_password");
                String firstName = request.getParameter("signup_firstName");
                String lastName = request.getParameter("signup_lastName");
                String gender = request.getParameter("gender_dropdown");
                
                HttpSession session = request.getSession();

                if (email != null && password != null && firstName != null && lastName != null) {
                    long userId = UserManager.getInstance().newSignUp(email, password, firstName, lastName, gender);

                    // User already exists
                    if (userId == -1) {
                        session.setAttribute("signup_error", "User already exists!");
                        response.sendRedirect(request.getContextPath() + "/auth/signup");
                    }
                    // User does not exist
                    else {
                        session.setAttribute("userId", userId);
                        response.sendRedirect(request.getContextPath() + "/auth");
                        System.out.println("UserID:" + userId);
                    }
                } else {
                    request.getRequestDispatcher("/signup.jsp").forward(request, response);
                }
            }
            // Login handling
            else {
                String email = request.getParameter("email");
                String password = request.getParameter("password");

                if (email != null && password != null) {
                    long userId = UserManager.getInstance().authenticate(email, password);
                    System.out.println("UserID:" + userId);

                    if (userId != -1) {
                        HttpSession session = request.getSession();
                        session.setAttribute("userId", userId);
                        request.getRequestDispatcher("/bookmark/mybooks").forward(request, response);
                    } else {
                        HttpSession session = request.getSession();
                        session.setAttribute("login_error", "Your email and/or password is incorrect!");
                        response.sendRedirect(request.getContextPath() + "/auth");
                    }
                } else {
                    request.getRequestDispatcher("/login.jsp").forward(request, response);
                }
            }
        }
        // Logout handling
        else {
            request.getSession().invalidate();
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
