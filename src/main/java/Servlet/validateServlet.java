/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Christoph Klampfer
 */
@WebServlet(name = "validateServlet", urlPatterns = {"/validateServlet"})
public class validateServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.getRequestDispatcher("inputPage.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String str = request.getParameter("batten");
        if (str != null) {
            if (str.equals("validate")) {
                request.getRequestDispatcher("mapPage.jsp").forward(request, response);
            }
            if (str.equals("upload Files")) {
                request.getRequestDispatcher("csvPage.jsp").forward(request, response);
            }
        }
//        String housenr = request.getParameter("streetnamehousenr");
//        String zipCode = request.getParameter("zipcode");
//        String city = request.getParameter("city");
//        String country = request.getParameter("country");
//        String url="";
//        System.out.println(housenr);
//        if (!housenr.equals(null)) {
//            housenr = housenr.replace(" ", "+");
//            url = "https://maps.google.com/maps?q=" + zipCode + "+" + housenr + "," + country + "&t=k&z=13&ie=UTF8&iwloc=&output=embed";
//        }
//        
//        System.out.println("-----URL-----\n" + url + "\n-----URL-----\n");
//        request.setAttribute("housenr", housenr);
//        request.setAttribute("zipCode", zipCode);
//        request.setAttribute("city", city);
//        request.setAttribute("housenr", country);
//        request.setAttribute("url", url);
        //request.getRequestDispatcher("inputPage.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("mapPage.jsp").forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
