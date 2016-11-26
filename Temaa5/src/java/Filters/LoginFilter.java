/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Filters;

/**
 *
 * @author Vlad
 */
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest reqt = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession ses = reqt.getSession(false);

        String reqURI = reqt.getRequestURI();
        if(reqURI.contains("faces") && !reqURI.contains("resource"))
        {
            if(reqURI.contains("login.xhtml"))
            {
                if(ses != null)
                {
                    String username = (String)ses.getAttribute("username");
                    String type = (String)ses.getAttribute("type");
                    if(username != null && type != null)
                    {
                        if(type.equals("admin")) 
                            resp.sendRedirect(reqt.getContextPath() + "/faces/schoolsPreferences.xhtml");
                        else if(type.equals("user") && reqURI.contains("schoolsPreferences.xhtml"))
                            resp.sendRedirect(reqt.getContextPath() + "/faces/editStudent.xhtml");
                        return;
                    }
                }
                chain.doFilter(request, response);
                return;
            }
            else
            {
                if(ses != null)
                {
                    String username = (String)ses.getAttribute("username");
                    String type = (String)ses.getAttribute("type");
                    if(username != null && type != null)
                    {
                        if(type.equals("admin")){
                            if(reqURI.contains("schoolsPreferences.xhtml") || reqURI.contains("resultsPage.xhtml")) 
//                                resp.sendRedirect(reqt.getContextPath() + "/faces/schoolsPreferences.xhtml");
                                chain.doFilter(request, response);
                            else 
                                resp.sendRedirect(reqt.getContextPath() + "/faces/schoolsPreferences.xhtml");
                            return;
                        }
                        if(type.equals("user"))
                        {
                            if(reqURI.contains("schoolsPreferences.xhtml"))
                                resp.sendRedirect(reqt.getContextPath() + "/faces/editStudent.xhtml");
                            else
                                chain.doFilter(request, response);
                            return;
                        }
                        return;
                    }
                    else
                    {
                        resp.sendRedirect(reqt.getContextPath() + "/faces/login.xhtml");
                        return;
                    }
                }
                resp.sendRedirect(reqt.getContextPath() + "/faces/login.xhtml");
                return;
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
    
}