package az.et.unitech.account.architecture;

import az.et.unitech.account.dao.entity.BaseEntity;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import javax.persistence.Entity;

import static az.et.unitech.account.architecture.ArchConstants.ENTITY_PKG;
import static az.et.unitech.account.architecture.ArchConstants.ENTITY_SUFFIX;
import static az.et.unitech.account.architecture.CommonRules.classesShouldBeNamedProperly;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public class EntityRules {

    @ArchTest
    static final ArchRule entity_classes_should_be_public = classes()
            .that().resideInAPackage(ENTITY_PKG)
            .and().areTopLevelClasses()
            .should().bePublic();

    @ArchTest
    static final ArchRule classes_should_be_annotated_with_entity = classes()
            .that().resideInAPackage(ENTITY_PKG)
            .and().areTopLevelClasses()
            .and().areNotAssignableFrom(BaseEntity.class)
            .should().beAnnotatedWith(Entity.class);

    @ArchTest
    static final ArchRule class_name_should_be_ending_with_entity =
            classesShouldBeNamedProperly(ENTITY_PKG, ENTITY_SUFFIX);

    @ArchTest
    static final ArchRule classes_should_override_equals_and_hashCode = classes()
            .that().resideInAnyPackage(ENTITY_PKG)
            .and().areNotMemberClasses()
            .should(CustomConditions.HAVE_EQUALS_AND_HASH_CODE)
            .because("Entity classes should override equals and hashCode methods");

}

