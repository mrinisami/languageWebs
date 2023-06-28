package app.sami.languageWeb.testUtils;

import app.sami.languageWeb.config.TestConfig;
import app.sami.languageWeb.auth.dtos.AuthenticationRequest;
import app.sami.languageWeb.auth.dtos.TokenDto;
import app.sami.languageWeb.user.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestConfig.class)
public abstract class IntegrationTests {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    protected MockHttpServletRequestBuilder get(String url) {
        return MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON);
    }

    protected MockHttpServletRequestBuilder get(String url, String token) {
        return MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON)
                .header("authorization", "Bearer " + token);
    }

    protected MockHttpServletRequestBuilder get(String url, String token, Object body) throws JsonProcessingException {
        return MockMvcRequestBuilders.get(url)
                .header("authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsBytes(body));
    }
    protected MockHttpServletRequestBuilder post(String url){
        return MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON);
    }

    protected MockHttpServletRequestBuilder post(String url, Object body, String token) throws JsonProcessingException {
        return MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(body))
                .header("authorization", "Bearer " + token);
    }

    protected MockHttpServletRequestBuilder put(String url, Object body, String token) throws JsonProcessingException {
        return MockMvcRequestBuilders.put(url).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(body))
                .header("authorization", "Bearer " + token);
    }
    protected MockHttpServletRequestBuilder put(String url){
        return MockMvcRequestBuilders.put(url).contentType(MediaType.APPLICATION_JSON);
    }

    protected MockHttpServletRequestBuilder delete(String url, String token) {
        return MockMvcRequestBuilders.delete(url)
                .header("authorization", "Bearer " + token);
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
