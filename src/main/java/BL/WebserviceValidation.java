package BL;

import data.Address;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.management.RuntimeErrorException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author marinaspari
 */
public class WebserviceValidation implements StaticData
{

    /**
    Takes an Address and return the validated Version of it.
    One address is taken, sent to the Webservice and received corrected 
    (if this was possible) in a JSON Format. This JSON is being formatted as 
    and Address and returned to the caller of this method.
    
    @param oldAddress
    @return
    @throws IOException
    @throws Exception 
     */
    public static Address executeWebserviceVaidation(Address oldAddress) throws IOException, Exception
    {
        try (CloseableHttpClient client = HttpClientBuilder.create().build())
        {
            HttpPost request = new HttpPost(WEBSERVICE_URL);
            request.setHeader("Authorization", USERCREDS);
            request.setHeader("content-type", CONTENTTYPE);
            request.setHeader("Accept", ACCEPTTYPE);
            StringEntity requestParams = new StringEntity(
                    "{\n"
                    + "  \"addressInput\": \n"
                    + "  {\n"
                    + "    \"mixed\": \"" + oldAddress.getStreet() + " " + (oldAddress.getHouseNr() == 0 ? "" : oldAddress.getHouseNr()) + "\",\n"
                    + "    \"postcode\": \"" + oldAddress.getZipCode() + "\",\n"
                    + "    \"locality\": \"" + oldAddress.getCity() + "\",\n"
                    + "    \"country\": \"" + oldAddress.getCountry() + "\"\n"
                    + "  },\n"
                    + "  \"outputFields\": \n"
                    + "  [\n"
                    + "    \"std_addr_prim_name_full\",\n"
                    + "    \"std_addr_prim_number\",\n"
                    + "    \"std_addr_locality_full\",\n"
                    + "    \"std_addr_region_full\",\n"
                    + "    \"std_addr_country_2char\",\n"
                    + "    \"std_addr_postcode1\"\n"
                    + "  ]\n"
                    + "}"
            );
            request.setEntity(requestParams);

            HttpResponse response = client.execute(request);

            BufferedReader bufReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));

            StringBuilder builder = new StringBuilder();

            String line;

            while ((line = bufReader.readLine()) != null)
            {
                builder.append(line);
                builder.append(System.lineSeparator());
            }
            System.out.println(builder.toString());

            //JSON Parser
            JSONParser parser = new JSONParser();
            if (parser != null && builder != null)
            {
                JSONObject json = (JSONObject) parser.parse(builder.toString());

                String straße = json.get("std_addr_prim_name_full").toString();
                int hausnr = -1;
                if (!json.get("std_addr_prim_number").toString().isEmpty())
                {
                    hausnr = Integer.parseInt(json.get("std_addr_prim_number").toString());
                }
                int postcode = Integer.parseInt(json.get("std_addr_postcode1").toString());
                String city = json.get("std_addr_locality_full").toString();
                String region = json.get("std_addr_region_full").toString();
                String country = json.get("std_addr_country_2char").toString();

                Address newAddress = new Address(straße, hausnr, postcode, city, region, country);
                return newAddress;
            }
            else
            {
                throw new RuntimeException("Error during the Webservice Validation!");
            }
        }
    }

    /*
    public static void main(String[] args)
    {
        try
        {
            Address oldAddress = new Address("Graazer Strasse", 202, 8430, "Kaintorf", "AT");
            Address newAddress = executeWebserviceVaidation(oldAddress);

            System.err.println(oldAddress);
            System.out.println(newAddress);
            System.out.println("");
            System.out.println("");

            
            oldAddress = new Address("krotentorf", 238, 8564, "krotttendorf", "AT");
            newAddress = executeWebserviceVaidation(oldAddress);
            System.err.println(oldAddress);
            System.out.println(newAddress);

        }
        catch (Exception ex)
        {
            Logger.getLogger(WebserviceValidation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     */
}
