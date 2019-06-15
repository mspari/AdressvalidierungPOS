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
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
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
@WebServlet(name = "ValidationServlet", urlPatterns
        =
        {
            "/ValidationServlet"
        },
        initParams
        =
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
        //called only the first time when the Servlet is started
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
        //open the welcome page
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
            // "batten" stores the value of the clicked button to take further steps
            String str = request.getParameter("batten");

            if (str != null)
            {
                // executed when a user wants to validate a single address
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

                // executed when a user uploads a csv-file with multiple addresses
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
                    List<Address> correctedAddresses = new ArrayList<>();

                    for (Address address : addresslist)
                    {
                        Address newAddress = WebserviceValidation.executeWebserviceVaidation(address);
                        correctedAddresses.add(newAddress);
                    }

                    request.getSession().removeAttribute("addresslist");
                    request.getSession().setAttribute("addresslist", correctedAddresses);
                    request.getRequestDispatcher("csvPage.jsp").forward(request, response);
                }
                
                // executed if a user accepts a single address to store it in the database
                if (str.equals("accept"))
                {
                    String street = request.getParameter("streetname");
                    int housenr = Integer.parseInt(request.getParameter("housenr"));
                    int zipCode = Integer.parseInt(request.getParameter("zipcode"));
                    String city = request.getParameter("city");
                    String region = request.getParameter("region");
                    String coutry = request.getParameter("country");
                    Address addr = new Address(street, housenr, zipCode, city, region, coutry);
                    DB_Access dba = DB_Access.getInstance();
                    dba.insertAddress(addr);
                    request.getRequestDispatcher("successfullySaved.jsp").forward(request, response);
                }

                // executed when a user requests the correction of multiple addresses
                // and wants to write the selected ones into the db (accept them)
                if (str.equals("accept selected"))
                {
                    List<Address> acceptedList = new ArrayList<>();
                    List<Address> addresslist = (List<Address>) request.getSession().getAttribute("addresslist");
                    for (int i = 0; i < addresslist.size(); i++)
                    {
                        if (request.getParameter("cb" + i) != null)
                        {
                            acceptedList.add(addresslist.get(i));
                        }
                    }

                    for (Address address : acceptedList)
                    {
                        DB_Access dba = DB_Access.getInstance();
                        dba.insertAddress(address);
                    }
                    request.getRequestDispatcher("successfullySaved.jsp").forward(request, response);
                }
            }
        }
        // in case of any Exception, an Error page is opened
        catch (Exception ex)
        {
            //ex.printStackTrace();
            request.getRequestDispatcher("unexpectedError.jsp").forward(request, response);
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
