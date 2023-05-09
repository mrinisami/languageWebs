package app.sami.languageWeb.testUtils;

import app.sami.languageWeb.config.TestConfig;
import app.sami.languageWeb.dtos.AuthenticationRequest;
import app.sami.languageWeb.dtos.TokenDto;
import app.sami.languageWeb.user.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles({"test"})
@Import(TestConfig.class)
public abstract class IntegrationTests {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    protected MockHttpServletRequestBuilder get(String url) {
        return MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON);
    }

    protected MockHttpServletRequestBuilder post(String url){
        return MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON);
    }

    protected String authUser(User user) throws Exception {
        Object body = AuthenticationRequest.builder()
                .email(user.getEmail())
                .userPassword(user.getUserPassword())
                .build();
        String response =  mockMvc.perform(post("/auth/authenticate")
                .content(objectMapper.writeValueAsString(body)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        TokenDto token = objectMapper.readValue(response, TokenDto.class);
        return token.getToken();
    }
}
