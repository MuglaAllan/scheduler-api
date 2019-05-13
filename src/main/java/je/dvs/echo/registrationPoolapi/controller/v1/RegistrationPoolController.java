package je.dvs.echo.registrationPoolapi.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.json.simple.JsonArray;
import org.apache.camel.json.simple.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/registrationNumber")
@CrossOrigin(origins = "*")
public class RegistrationPoolController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    ObjectMapper objectMapper;


    @CrossOrigin(origins = "*")
    @GetMapping(path = "/history/{registrationNumber}")
    public String registrationNumberHistory(@PathVariable String registrationNumber) {

        try {
            String urlDecoded = URLDecoder.decode(registrationNumber, "UTF-8");
            LOGGER.info("Request Received for Registration Number History:" + urlDecoded);
            return rabbitTemplate.convertSendAndReceive("registrationPoolHistory", urlDecoded).toString();
        } catch (Exception e) {
            LOGGER.info("Error : " + e.getMessage());
            return null;
        }


    }

    @CrossOrigin(origins = "*")
    @GetMapping(path = "/history/{registrationNumber}/{date}")
    public String registrationNumberHistory(@PathVariable String registrationNumber, @PathVariable String date) {

        try {
            String RegistrationNumber = URLDecoder.decode(registrationNumber, "UTF-8");
            String Date = URLDecoder.decode(date, "UTF-8");

            LocalDateTime createdDate = LocalDateTime.from(DateTimeFormatter.ISO_DATE_TIME.parse(Date));


            JsonObject message = new JsonObject();

            message.put("registrationnumber", RegistrationNumber);
            message.put("date", createdDate.toString());

            LOGGER.info("Request Received for Registration Number History:" + message.toJson().toString());
            return rabbitTemplate.convertSendAndReceive("registrationPoolHistoryWithDate", message.toJson()).toString();
        } catch (Exception e) {
            LOGGER.info("Error : " + e.getMessage());
            return null;
        }


    }


    @CrossOrigin(origins = "*")
    @GetMapping("/search/classification/{classification}/{page}/{count}")
    public String classificationSearch(@PathVariable String classification, @PathVariable String page, @PathVariable String count) {

        try {
            String searchV = URLDecoder.decode(classification, "UTF-8");
            String pageV = URLDecoder.decode(page, "UTF-8");
            String countV = URLDecoder.decode(count, "UTF-8");
            LOGGER.info("Request Received for Registration Number Classification:" + searchV + ":" + page);

            JsonObject searchValueObj = new JsonObject();
            searchValueObj.put("classification", searchV);
            searchValueObj.put("page", pageV);
            searchValueObj.put("count", countV);

            return rabbitTemplate.convertSendAndReceive("regClassificationSearch", searchValueObj.toJson()).toString();
        } catch (Exception e) {
            LOGGER.info("Error : " + e.getMessage());
            return null;
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/search/classification/{classification}")
    public String classificationSearch(@PathVariable String classification) {

        try {
            String searchV = URLDecoder.decode(classification, "UTF-8");
            LOGGER.info("Request Received for Registration Number Classification:" + searchV);

            JsonObject searchValueObj = new JsonObject();
            searchValueObj.put("classification", searchV);
            searchValueObj.put("page", 0);
            searchValueObj.put("count", Integer.MAX_VALUE);

            return rabbitTemplate.convertSendAndReceive("regClassificationSearch", searchValueObj.toJson()).toString();
        } catch (Exception e) {
            LOGGER.info("Error : " + e.getMessage());
            return null;
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/search/classificationOwnerSearch/{iscompany}/{classification}/{ownerid}")
    public String classificationSearchwithOwnerID(@PathVariable boolean iscompany, @PathVariable String classification, @PathVariable String ownerid) {

        try {
            String classificationValue = URLDecoder.decode(classification, "UTF-8");
            String owneridValue = URLDecoder.decode(ownerid,"UTF-8");
            String iscompanyValue = URLDecoder.decode(String.valueOf(iscompany),"UTF-8");
            LOGGER.info("Request Received for Registration Number Classification:" + classificationValue + "Owner" + owneridValue);

            JsonObject searchValueObj = new JsonObject();
            searchValueObj.put("classification", classificationValue);
            searchValueObj.put("ownerid", owneridValue);
            searchValueObj.put("iscompany", iscompanyValue);

            return rabbitTemplate.convertSendAndReceive("regClassificationSearchWithOwner", searchValueObj.toJson()).toString();
        } catch (Exception e) {
            LOGGER.info("Error : " + e.getMessage());
            return null;
        }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(method = RequestMethod.POST, path = "/update/status")
    public String updateregistrationNumberStatus(@RequestBody @Valid String statusUpdate) {

        try {
            JsonObject Request = new ObjectMapper().readValue(statusUpdate, JsonObject.class);

            if (Request.containsKey("registrationnumber")) {

                String RegistrationNumber = Request.get("registrationnumber").toString();
                String OwnerId = (Request.get("owner_docuuid") != null) ? Request.get("owner_docuuid").toString() : null;
                String Classification = Request.get("classification").toString();
                String Is_Company = (Request.getInteger("is_company") == 0) ? "false" : "true";

                JsonObject message = new JsonObject();

                message.put("registrationnumber", RegistrationNumber);
                if (OwnerId != null) {
                    message.put("owner_id", OwnerId);
                    message.put("is_company", Is_Company);
                }
                message.put("classification", Classification);

                LOGGER.info("Request Received for Update of Registration Number Classification:" + RegistrationNumber);
                return rabbitTemplate.convertSendAndReceive("registrationPoolUpdate", message.toJson()).toString();
            } else {

                String TradeLicenseNumber = Request.get("tradelicensenumber").toString();
                String OwnerId = (Request.get("owner_id") != null) ? Request.get("owner_id").toString() : null;
                String Classification = Request.get("classification").toString();
                String Is_Company = (Request.getInteger("is_company") == 0) ? "false" : "true";


                JsonObject message = new JsonObject();

                message.put("tradelicensenumber", TradeLicenseNumber);
                if (OwnerId != null) {
                    message.put("owner_id", OwnerId);
                    message.put("is_company", Is_Company);
                }
                message.put("classification", Classification);

                LOGGER.info("Request Received for Update of Registration Number Classification:" + TradeLicenseNumber);
                return rabbitTemplate.convertSendAndReceive("regUpdateTradeStatus", message.toJson()).toString();

            }
        } catch (Exception e) {
            LOGGER.info("Error : " + e.getMessage());
            return null;
        }

    }

    @CrossOrigin(origins = "*")
    @RequestMapping(method = RequestMethod.POST, path = "/search/range")
    public String getRangeofRegistrationNumbers(String FirstReg, String LastReg) {

        try {
            String First = URLDecoder.decode(FirstReg, "UTF-8");
            String Last = URLDecoder.decode(LastReg, "UTF-8");

            JsonObject message = new JsonObject();
            message.put("First", First);
            message.put("Last", Last);


            LOGGER.info("Request Received for Range of Registration Numbers");
            return rabbitTemplate.convertSendAndReceive("registrationSearchRange", message.toJson()).toString();
        } catch (Exception e) {
            LOGGER.info("Error : " + e.getLocalizedMessage());
            return null;
        }

    }

    @CrossOrigin(origins = "*")
    @RequestMapping(method = RequestMethod.POST, path = "/search/trade/range/")
    public String getRangeofTradeRegistrationNumbers(String count) {
        try {
            String Count = URLDecoder.decode(count, "UTF-8");

            JsonObject message = new JsonObject();
            message.put("count", Count);

            LOGGER.info("Request Received for Trade License Range");
            return rabbitTemplate.convertSendAndReceive("tradeBulkRegistration", message.toJson()).toString();
        } catch (Exception e) {
            LOGGER.info("Error : " + e.getLocalizedMessage());
            return null;
        }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(method = RequestMethod.POST, path = "/update/bulk/status")
    public String updatebulkregistrationNumberStatus(@RequestBody @Valid String statusUpdate) {
        try {
            JsonArray Request = new ObjectMapper().readValue(statusUpdate, JsonArray.class);

            if (Request.contains("registrationnumber")) {

                LOGGER.info("Request Received for Update of Registration Number Classification:" + Request);
                return rabbitTemplate.convertSendAndReceive("registrationPoolUpdateBulk", Request.toJson()).toString();
            }
            else
            {
                LOGGER.info("Request Received for Update of Registration Number Classification:" + Request);
                return rabbitTemplate.convertSendAndReceive("TradeUpdateBulk", Request.toJson()).toString();
            }
        } catch (Exception e) {
            LOGGER.info("Error : " + e.getMessage());
            return null;
        }


    }

    @CrossOrigin(origins = "*")
    @RequestMapping(method = RequestMethod.POST, path = "/exchange")
    public String exchangeRegistrationNumber(@RequestBody @Valid String ExchangeRequest) {

        try {
            LOGGER.info("Request Received for Exchange of Registration Number" + ExchangeRequest);
            return rabbitTemplate.convertSendAndReceive("ExchangeRegMark", ExchangeRequest).toString();
        }catch (Exception e)
        {
            LOGGER.info("Error: " + e.getMessage());
            return null;
        }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(method = RequestMethod.POST, path = "/retchange")
    public String retainChangeRegistrationNumber(@RequestBody @Valid String RetChangeRequest) {
        try {
            LOGGER.info("Request Received for Retain/Change of Registration Number" + RetChangeRequest);
            return rabbitTemplate.convertSendAndReceive("RetChangeRegMark", RetChangeRequest).toString();
        }catch (Exception e)
        {
            LOGGER.info("Error: " + e.getMessage());
            return null;
        }
    }

}
