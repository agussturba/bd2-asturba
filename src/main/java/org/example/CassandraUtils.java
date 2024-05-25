package org.example;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;

public class CassandraUtils {
    public static void updateCassandraData(CqlSession session, String tableName, String columnToUpdate, String newValue,
                                            String conditionColumn, String conditionValue) {
        String updateQuery = "UPDATE " + tableName + " SET " + columnToUpdate + " = ? WHERE " + conditionColumn + " = ?";
        session.execute(updateQuery, newValue, conditionValue);

        System.out.println("Data updated in '" + tableName + "' successfully.");
    }

    /**
     *
     * @param session
     * @param tableName
     * @param conditionColumn el conditionColumn tiene que ser el id
     * @param conditionValue
     */
    public static void deleteCassandraData(CqlSession session, String tableName, String conditionColumn, String conditionValue) {
        String deleteQuery = "DELETE FROM " + tableName + " WHERE " + conditionColumn + " = ?";
        session.execute(deleteQuery, conditionValue);

        System.out.println("Data deleted from '" + tableName + "' successfully.");
    }

    /**
     *
     * @String Columns las columnas van con el nombre y su tipo, por ejemplo Id UUID PRIMARY KEY, Name TEXT,etc
     * @param tableName
     * @param columns
     */
    public static void createCassandraTable(CqlSession session, String tableName, String... columns) {
        StringBuilder queryBuilder = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
                .append(tableName).append(" (");

        for (String column : columns) {
            queryBuilder.append(column).append(",");
        }

        // Remove the trailing comma
        queryBuilder.deleteCharAt(queryBuilder.length() - 1);
        queryBuilder.append(");");

        session.execute(queryBuilder.toString());

        System.out.println("Table '" + tableName + "' created successfully.");
    }

    public static void insertData(CqlSession session, String tableName, String... columnsAndValues) {
        StringBuilder queryBuilder = new StringBuilder("INSERT INTO ")
                .append(tableName).append(" (");

        for (int i = 0; i < columnsAndValues.length; i += 2) {
            queryBuilder.append(columnsAndValues[i]);
            if (i < columnsAndValues.length - 2) {
                queryBuilder.append(", ");
            }
        }

        queryBuilder.append(") VALUES (");

        for (int i = 1; i < columnsAndValues.length; i += 2) {
            queryBuilder.append(columnsAndValues[i]);
            if (i < columnsAndValues.length - 1) {
                queryBuilder.append(", ");
            }
        }

        queryBuilder.append(");");

        session.execute(queryBuilder.toString());

        System.out.println("Data inserted into '" + tableName + "' successfully.");
    }

    /**
     *
     * @param session
     * @param tableName
     * @param columnName el column name tiene que ser si o si el id si no no te deja buscar por la particion
     * @param columnValue
     * @return
     */
    public static ResultSet searchByOne(CqlSession session, String tableName, String columnName, String columnValue) {
        String selectQuery = "SELECT * FROM " + tableName + " WHERE " + columnName + " = ? ALLOW FILTERING";
        return session.execute(selectQuery, columnValue);
    }
}
