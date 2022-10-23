package az.et.unitech.identity.architecture;

import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeJars;
import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static az.et.unitech.identity.architecture.ArchConstants.*;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(packages = BASE_PACKAGE, importOptions = {DoNotIncludeTests.class, DoNotIncludeJars.class})
class LayeredArchitectureTest {

    private static final boolean ALLOW_EMPTY_LAYERS = true;

    @ArchTest
    static final ArchRule layered_architecture_rule = layeredArchitecture()
            .layer(CONTROLLER_LAYER).definedBy(CONTROLLER_PKG)
            .layer(SERVICE_LAYER).definedBy(SERVICE_PKG)
            .layer(REPOSITORY_LAYER).definedBy(REPOSITORY_PKG)
            .layer(ENTITY_LAYER).definedBy(ENTITY_PKG)
            .layer(MAPPER_LAYER).definedBy(MAPPER_PKG)
            .withOptionalLayers(ALLOW_EMPTY_LAYERS)

            .whereLayer(CONTROLLER_LAYER).mayNotBeAccessedByAnyLayer()
            .whereLayer(ENTITY_LAYER).mayOnlyBeAccessedByLayers(REPOSITORY_LAYER, SERVICE_LAYER, MAPPER_LAYER)
            .whereLayer(REPOSITORY_LAYER).mayOnlyBeAccessedByLayers(SERVICE_LAYER);

}
