/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import BL.InputData;
import BL.WebserviceValidation;
import data.Address;
import java.io.File;
import database.DB_Access;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author marinaspari
 */
@WebServlet(name = "ValidationServlet", urlPatterns =
{
    "/ValidationServlet"
},
        initParams =
        {
            @WebInitParam(name = "path", value = "/var/www/upload/")
        })
@MultipartConfig
public class ValidationServlet extends HttpServlet
{

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
            throws ServletException, IOException
    {
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
            throws ServletException, IOException
    {
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
            throws ServletException, IOException
    {

        try
        {

            String str = request.getParameter("batten");

            if (str != null)
            {
                if (str.equals("validate"))
                {
                    Address oldAddress = new Address(request.getParameter("streetnamehousenr"),
                            Integer.parseInt(request.getParameter("zipcode")),
                            request.getParameter("city"),
                            request.getParameter("country"));
                    Address correctedAddress = WebserviceValidation.executeWebserviceVaidation(oldAddress);

                    request.setAttribute("correctedAddress", correctedAddress);
                    request.getRequestDispatcher("singleMapPage.jsp").forward(request, response);
                }

                if (str.equals("upload Files"))
                {
                    ServletConfig sc = getServletConfig();
                    String path = sc.getInitParameter("uploadpath");

                    Part filePart = request.getPart("file");

                    String fileName = filePart.getSubmittedFileName();
                    InputStream is = filePart.getInputStream();

                    Path realpath = Paths.get(path + fileName);

                    Files.copy(is, realpath,
                            StandardCopyOption.REPLACE_EXISTING);

                    File file = new File(realpath.toUri());
                    List<Address> addresslist = InputData.readCsv(file);

                    request.setAttribute("addresslist", addresslist);
                    request.getRequestDispatcher("csvPage.jsp").forward(request, response);

                    /*
                    ServletOutputStream os = response.getOutputStream();
                    for (Address address : addresslist)
                    {
                        os.print(address + "\n");
                    }

                    if (str.equals("accept"))
                    {
                        String street = request.getParameter("streetnamehousenr");
                        String zipCode = request.getParameter("zipcode");
                        String city = request.getParameter("city");
                        String country = request.getParameter("country");
                        Address addr = new Address(street, Integer.parseInt(zipCode), city, country);
                        DB_Access dba = DB_Access.getInstance();
                        dba.insertAddress(addr);
                    }
                     */
                }

            }
        }
        catch (Exception ex)
        {
            Logger.getLogger(ValidationServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
        return "Short description";
    }// </editor-fold>
}
