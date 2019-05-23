package BL;

import data.Address;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

/**
 *
 * @author marinaspari
 */
public class WebserviceValidation implements StaticData
{

    public static JSONObject executeWebserviceVaidation(Address oldAddress) throws IOException, Exception
    {
        try (CloseableHttpClient client = HttpClientBuilder.create().build())
        {
            System.err.println(new StringEntity(
                    "{\n"
                    + "  \"addressInput\": \n"
                    + "  {\n"
                    + "    \"mixed\": \"" + oldAddress.getStreet() + " " + oldAddress.getHouseNr() + "\",\n"
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
                    + "    \"std_addr_postcode1\",\n"
                    + "  ]\n"
                    + "}"
            ));

            HttpPost request = new HttpPost(WEBSERVICE_URL);
            request.setHeader("Authorization", USERCREDS);
            request.setHeader("content-type", CONTENTTYPE);
            request.setEntity(new StringEntity(
                    "{\n"
                    + "  \"addressInput\": \n"
                    + "  {\n"
                    + "    \"mixed\": \"Graazer Strasse 202\",\n"
                    + "    \"postcode\": \"8430\",\n"
                    + "    \"locality\": \"Kaintorf\",\n"
                    + "    \"country\": \"AT\"\n"
                    + "  },\n"
                    + "  \"outputFields\": \n"
                    + "  [\n"
                    + "    \"std_addr_address_delivery\",\n"
                    + "    \"std_addr_postcode_full\",\n"
                    + "    \"std_addr_locality_full\",\n"
                    + "    \"std_addr_region_full\",\n"
                    + "    \"std_addr_country_2char\",\n"
                    + "    \"addr_asmt_level\",\n"
                    + "    \"addr_info_code\"\n"
                    + "  ]\n"
                    + "}"
            ));

            HttpResponse response = client.execute(request);

            BufferedReader bufReader = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent()));

            StringBuilder builder = new StringBuilder();

            String line;

            while ((line = bufReader.readLine()) != null)
            {
                builder.append(line);
                builder.append(System.lineSeparator());
            }

            System.err.println(builder);
            return new JSONObject(builder);
        }
    }

    public static void main(String[] args)
    {
        try
        {
            Address oldAddress = new Address("Graazer Strasse", 202, 8430, "Kaintorf", "AT");
            JSONObject obj = executeWebserviceVaidation(oldAddress);
            /*
            System.out.println(obj.get("std_addr_prim_number"));
            Address newAddress = new Address(obj.getString("std_addr_prim_name_full"),
                    obj.getInt("std_addr_prim_number"),
                    obj.getInt("std_addr_postcode1"),
                    obj.getString("std_addr_locality_full"),
                    obj.getString("std_addr_region_full"),
                    obj.getString("std_addr_country_2char"));
            System.out.println(newAddress);
             */

        }
        catch (Exception ex)
        {
            Logger.getLogger(WebserviceValidation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
