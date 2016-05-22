/**
 * Created by fforbeck on 21/05/16.
 */

import com.felipeforbeck.vacuum.domain.model.Microservice;
import com.felipeforbeck.vacuum.application.util.SwaggerToMicroserviceConverter;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

@RunWith(JUnit4.class)
public class SwaggerToMicroserviceConverterTest {

    private List<String> expectedPaths = new ArrayList<>();

    @Before
    public void setup() {
        expectedPaths.add("/v2/pet");
        expectedPaths.add("/v2/pet/findByStatus");
        expectedPaths.add("/v2/pet/findByTags");
        expectedPaths.add("/v2/pet/{petId}");
        expectedPaths.add("/v2/pet/{petId}/uploadImage");
        expectedPaths.add("/v2/store/inventory");
        expectedPaths.add("/v2/store/order");
        expectedPaths.add("/v2/store/order/{orderId}");
        expectedPaths.add("/v2/user");
        expectedPaths.add("/v2/user/createWithArray");
        expectedPaths.add("/v2/user/createWithList");
        expectedPaths.add("/v2/user/login");
        expectedPaths.add("/v2/user/logout");
        expectedPaths.add("/v2/user/{username}");
    }

    @Test
    public void shouldConvertToMicroserviceModelWhenSwaggerIsProvided() {
        SwaggerToMicroserviceConverter converter = new SwaggerToMicroserviceConverter();
        Swagger swagger = new SwaggerParser().read("src/test/resources/swagger.json");

        Microservice microservice = converter.convert(swagger);

        Assert.assertEquals("petstore.swagger.io", microservice.getHost());

        microservice.getEndpoints()
                .forEach(endpoint -> Assert.assertTrue(endpoint.getPath() + " not found",
                        expectedPaths.contains(endpoint.getPath())));
    }
}
