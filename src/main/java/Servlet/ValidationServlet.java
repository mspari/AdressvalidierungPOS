/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import BL.WebserviceValidation;
import data.Address;
import database.DB_Access;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author marinaspari
 */
@WebServlet(name = "ValidationServlet", urlPatterns
        = {
            "/ValidationServlet"
        })
public class ValidationServlet extends HttpServlet {

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
        request.getRequestDispatcher("welcomePage.jsp").forward(request, response);
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
        processRequest(request, response);
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

        try {
            String str = request.getParameter("batten");

            if (str != null) {
                if (str.equals("validate")) {
                    Address oldAddress = new Address(request.getParameter("streetnamehousenr"),
                            Integer.parseInt(request.getParameter("zipcode")),
                            request.getParameter("city"),
                            request.getParameter("country"));
                    Address correctedAddress = WebserviceValidation.executeWebserviceVaidation(oldAddress);

                    request.setAttribute("correctedAddress", correctedAddress);
                    request.getRequestDispatcher("singleMapPage.jsp").forward(request, response);
                }
                if (str.equals("upload Files")) {
                    request.getRequestDispatcher("csvPage.jsp").forward(request, response);
                }
                if (str.equals("accept")) {
                    String street = request.getParameter("streetnamehousenr");
                    String zipCode = request.getParameter("zipcode");
                    String city = request.getParameter("city");
                    String country = request.getParameter("country");
                    Address addr = new Address(street, Integer.parseInt(zipCode), city, country);
                    DB_Access dba = DB_Access.getInstance();
                    dba.insertAddress(addr);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(ValidationServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
