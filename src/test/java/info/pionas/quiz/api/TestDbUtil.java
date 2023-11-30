package info.pionas.quiz.api;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Table;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class TestDbUtil {

    public static final String DOT_DELIMITER = ".";
    private final String schema;
    private final EntityManager entityManager;
    private final List<String> tablesToClean;

    TestDbUtil(String schema, EntityManager entityManager, String... excludes) {
        final var tablesExcludeFromCleaning = Arrays.asList(excludes);
        this.schema = schema;
        this.entityManager = entityManager;
        this.tablesToClean = entityManager.getMetamodel().getEntities().stream()
                .filter(entityType -> entityType.getJavaType().getAnnotation(Table.class) != null)
                .map(entityType -> entityType.getJavaType().getAnnotation(Table.class))
                .map(this::convertToTableName)
                .filter(Predicate.not(tablesExcludeFromCleaning::contains))
                .toList();
    }

    public EntityManager em() {
        return entityManager;
    }

    @Transactional
    void clean() {
        final var tables = getListOfTables();
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();
        tablesToClean.forEach(s -> truncateTable(tables, s));
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
    }

    private String convertToTableName(Table table) {
        final var tableName = table.name();
        final var convertedSchema = StringUtils.hasText(schema) ? schema.toLowerCase() + DOT_DELIMITER : "";
        final var convertedTableName = tableName.replaceAll("([a-z])([A-Z])", "$1_$2");
        return convertedSchema + convertedTableName;
    }


    private List<String> getListOfTables() {
        @SuppressWarnings("unchecked")
        final var rows = (List<String>) entityManager.createNativeQuery("SELECT table_name FROM INFORMATION_SCHEMA.tables", String.class).getResultList();
        return rows
                .stream()
                .map(String::toLowerCase)
                .toList();
    }

    private void truncateTable(List<String> tables, String tableName) {
        if (tableExists(tables, tableName)) {
            entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
        }
    }

    private boolean tableExists(List<String> tables, String tableName) {
        final var convertedTableName = StringUtils.hasText(schema) ? tableName.replace(schema.toLowerCase() + DOT_DELIMITER, "") : tableName;
        return tables.contains(convertedTableName);
    }
}
