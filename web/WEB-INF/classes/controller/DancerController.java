package aorlov.ashdb.web.controller;

import aorlov.ashdb.core.Dancer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mouse on 2/7/14.
 */
public class DancerController extends HttpServlet {

    public void doPost( HttpServletRequest request,
                        HttpServletResponse response)
            throws IOException, ServletException {
        String c = request.getParameter("personal_code");

        // Now use our Coffee Model above
//        CoffeeExpert ce = new CoffeeExpert();
//
        List result = new ArrayList();
        Dancer dancer = new Dancer();
        dancer.setPersonalCode(123);
        dancer.setName("Test");
        result.add(dancer);

        // Use the below code to debug the program if you get problems
        //response.setContentType("text/html"):
        //PrintWriter out = response.getWriter();
        //out.println("Coffee Selection Advise<br>");

        //Iterator it = result.iterator();
        //while(it.hasNext()) {
        //  out.print("<br>try: " + it.next());
        //}

        // The results will be passed back (as an attribute) to the JSP view
        // The attribute will be a name/value pair, the value in this case will be a List object
        request.setAttribute("dancer", result);
        RequestDispatcher view = request.getRequestDispatcher("result.jsp");
        view.forward(request, response);
    }
}