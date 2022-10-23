package az.et.unitech.account.architecture;

import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeJars;
import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchTests;

import static az.et.unitech.account.architecture.ArchConstants.BASE_PACKAGE;

@AnalyzeClasses(packages = BASE_PACKAGE, importOptions = {DoNotIncludeTests.class, DoNotIncludeJars.class})
public class ArchRuleSetsTest {

    // -----  Layer Rules ----- //

    @ArchTest
    static final ArchTests CONTROLLER_RULES = ArchTests.in(ControllerRules.class);

    @ArchTest
    static final ArchTests SERVICE_RULES = ArchTests.in(ServiceRules.class);

    @ArchTest
    static final ArchTests REPOSITORY_RULES = ArchTests.in(RepositoryRules.class);

    @ArchTest
    static final ArchTests ENTITY_RULES = ArchTests.in(EntityRules.class);

    @ArchTest
    static final ArchTests DTO_RULES = ArchTests.in(DtoRules.class);

}

