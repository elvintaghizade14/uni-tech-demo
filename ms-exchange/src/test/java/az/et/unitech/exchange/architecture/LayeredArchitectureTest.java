package az.et.unitech.exchange.architecture;

import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeJars;
import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(packages = ArchConstants.BASE_PACKAGE, importOptions = {DoNotIncludeTests.class, DoNotIncludeJars.class})
class LayeredArchitectureTest {

    private static final boolean ALLOW_EMPTY_LAYERS = true;

    @ArchTest
    static final ArchRule layered_architecture_rule = layeredArchitecture()
            .layer(ArchConstants.CONTROLLER_LAYER).definedBy(ArchConstants.CONTROLLER_PKG)
            .layer(ArchConstants.SERVICE_LAYER).definedBy(ArchConstants.SERVICE_PKG)
            .layer(ArchConstants.REPOSITORY_LAYER).definedBy(ArchConstants.REPOSITORY_PKG)
            .layer(ArchConstants.ENTITY_LAYER).definedBy(ArchConstants.ENTITY_PKG)
            .layer(ArchConstants.MAPPER_LAYER).definedBy(ArchConstants.MAPPER_PKG)
            .withOptionalLayers(ALLOW_EMPTY_LAYERS)

            .whereLayer(ArchConstants.CONTROLLER_LAYER).mayNotBeAccessedByAnyLayer()
            .whereLayer(ArchConstants.ENTITY_LAYER).mayOnlyBeAccessedByLayers(ArchConstants.REPOSITORY_LAYER, ArchConstants.SERVICE_LAYER, ArchConstants.MAPPER_LAYER)
            .whereLayer(ArchConstants.REPOSITORY_LAYER).mayOnlyBeAccessedByLayers(ArchConstants.SERVICE_LAYER);

}
