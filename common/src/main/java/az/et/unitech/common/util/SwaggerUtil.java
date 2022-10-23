package az.et.unitech.common.util;

import az.et.unitech.common.config.properties.SwaggerProperties;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;

import java.util.Collections;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SwaggerUtil {

    public static ApiInfo convertToSpringFoxApiInfo(SwaggerProperties props) {
        return new ApiInfo(
                props.getTitle(),
                props.getDescription(),
                props.getVersion(),
                props.getTermsOfServiceUrl(),
                new Contact(props.getContactName(), props.getContactUrl(), props.getContactEmail()),
                props.getLicense(),
                props.getLicenseUrl(),
                Collections.emptyList());
    }

}
