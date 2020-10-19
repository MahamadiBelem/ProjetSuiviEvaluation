package suivi.evaluation;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("suivi.evaluation");

        noClasses()
            .that()
                .resideInAnyPackage("suivi.evaluation.service..")
            .or()
                .resideInAnyPackage("suivi.evaluation.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..suivi.evaluation.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
