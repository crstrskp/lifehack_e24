package app.persistence;

import app.exceptions.DatabaseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class ParmWeightMapperTest {

    private static ParmWeightMapper parmWeightMapper;
    private static ConnectionPool connectionPool;

    @BeforeAll
    public static void setUpClass() throws SQLException {
        // Initialize connection pool
        String url = "jdbc:postgresql://localhost:5432/lifehack?currentSchema=testparm";
        String db = "lifehack";
        String user = "postgres";
        String password = "postgres";

        connectionPool = ConnectionPool.getInstance(user, password, url, db);
    }

    @BeforeEach
    void setUp() {
        String url = "jdbc:postgresql://localhost:5432/lifehack";
        String user = "postgres";
        String password = "postgres";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            try (Statement stmt = connection.createStatement()) {
                // Remove all rows from all tables
                stmt.execute("DELETE FROM testparm.parmweight");
                stmt.execute("DELETE FROM testparm.users");

                // Reset the sequence numbers
                stmt.execute("SELECT setval('testparm.users_user_id_seq', 1, false)");
                stmt.execute("SELECT setval('testparm.parmweight_weight_id_seq', 1, false)");

                // Insert rows into users
                stmt.execute("INSERT INTO testparm.users (user_id, username, password, role) VALUES " +
                        "(1, 'JohnDoe', 'password123', 'USER'), " +
                        "(2, 'JaneDoe', 'password123', 'USER'), " +
                        "(3, 'AdminUser', 'adminpass', 'ADMIN')");

                // Insert rows into parmweight
                stmt.execute("INSERT INTO testparm.parmweight (user_id, weight, date) VALUES " +
                        "(1, 75.5, '2024-10-02 08:30:00'), " +
                        "(1, 76.0, '2024-10-03 08:30:00'), " +
                        "(2, 85.0, '2024-10-01 09:00:00'), " +
                        "(3, 90.3, '2024-09-30 07:45:00')");

                // Set sequence to continue from the largest IDs
                stmt.execute("SELECT setval('testparm.users_user_id_seq', COALESCE((SELECT MAX(user_id)+1 FROM public.users), 1), false)");
                stmt.execute("SELECT setval('testparm.parmweight_weight_id_seq', COALESCE((SELECT MAX(weight_id)+1 FROM public.parmweight), 1), false)");

            }
        } catch (SQLException e) {
            fail("Database connection failed: " + e.getMessage());
        }
    }



//    @Test
//    void getAllWeightPerUser() {
//    }

    @Test
    void addWeight() throws DatabaseException {

        assertTrue(ParmWeightMapper.addWeight(1,80, connectionPool));
    }

//    @Test
//    void getAverageWeightPastWeek() {
//    }
}