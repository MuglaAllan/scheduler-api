//package je.dvs.echo.registrationPoolapi;
//
//import je.dvs.echo.registrationPoolapi.controller.v1.RegistrationPoolController;
//import org.apache.camel.json.simple.JsonObject;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.mockito.BDDMockito.given;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(RegistrationPoolController.class)
//@Ignore
//public class RegistrationPoolApiControllerTest {
//
//
//    @Autowired
//    private MockMvc mvc;
//
//    @MockBean
//    private RegistrationPoolController registrationPoolController;
//
//    @Test
//    public void getregistrationNumberHistory() throws  Exception{
//
//        String registrationNumber = "J1234";
//
//        JsonObject registrationNumberHistory = new JsonObject();
//
//        given(registrationPoolController.registrationNumberHistory(registrationNumber)).willReturn(registrationNumberHistory.toJson());
//
//        mvc.perform(get("/registrationNumber/history/" + registrationNumber)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//
//
//
//
//}
