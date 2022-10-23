package az.et.unitech.exchange.architecture;

import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.thirdparty.com.google.common.collect.Maps;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public class DtoRules {

    @ArchTest
    static final ArchRule fields_should_have_getter = CommonRules.fieldsShouldHaveGetterRule(Maps.newHashMap(), ArchConstants.DTO_PKG);

    @ArchTest
    static final ArchRule public_and_final_fields_are_not_allowed = CommonRules.publicAndFinalFieldsAreNotAllowedRule(ArchConstants.DTO_PKG);

    @ArchTest
    static final ArchRule methods_should_be_public = CommonRules.methodsShouldBePublicRule(ArchConstants.DTO_PKG);

    @ArchTest
    static final ArchRule static_methods_are_not_allowed = CommonRules.staticMethodsAreNotAllowedRule(ArchConstants.DTO_PKG);

    @ArchTest
    static final ArchRule classes_should_override_equals_and_hashCode = classes()
            .that().resideInAnyPackage(ArchConstants.DTO_PKG)
            .and().areNotMemberClasses()
            .should(CustomConditions.HAVE_EQUALS_AND_HASH_CODE)
            .because("DTO classes should override equals and hashCode methods");

}
