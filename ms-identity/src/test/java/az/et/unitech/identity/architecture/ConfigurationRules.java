package az.et.unitech.identity.architecture;

import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.context.annotation.Configuration;

import static az.et.unitech.identity.architecture.ArchConstants.CONFIGURATION_PKG;
import static az.et.unitech.identity.architecture.ArchConstants.CONFIGURATION_SUFFIX;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class ConfigurationRules {

    @ArchTest
    static final ArchRule configuration_classes_should_be_annotated_with_configuration_annotation =
            classes()
                    .that().haveSimpleNameEndingWith(CONFIGURATION_SUFFIX)
                    .should().beAnnotatedWith(Configuration.class);

    @ArchTest
    static final ArchRule no_classes_with_configuration_annotation_should_reside_outside_of_config_package =
            noClasses()
                    .that().areAnnotatedWith(Configuration.class)
                    .should().resideOutsideOfPackage(CONFIGURATION_PKG);

}
