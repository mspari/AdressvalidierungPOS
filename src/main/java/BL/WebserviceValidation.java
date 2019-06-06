package BL;

import data.Address;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public static Address executeWebserviceVaidation(Address oldAddress) throws IOException, Exception
    {
        try (CloseableHttpClient client = HttpClientBuilder.create().build())
        {
            HttpPost request = new HttpPost(WEBSERVICE_URL);
            request.setHeader("Authorization", USERCREDS);
            request.setHeader("content-type", CONTENTTYPE);
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

            //JSON Parser
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(builder.toString());

            String straße = json.get("std_addr_prim_name_full").toString();
            int hausnr = Integer.parseInt(json.get("std_addr_prim_number").toString());
            int postcode = Integer.parseInt(json.get("std_addr_postcode1").toString());
            String city = json.get("std_addr_locality_full").toString();
            String region = json.get("std_addr_region_full").toString();
            String country = json.get("std_addr_country_2char").toString();

            return new Address(straße, hausnr, postcode, city, region, country);
        }
    }

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

            /***************************************************************************/
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
}
