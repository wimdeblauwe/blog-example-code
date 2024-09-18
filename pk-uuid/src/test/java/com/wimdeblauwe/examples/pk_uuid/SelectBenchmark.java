package com.wimdeblauwe.examples.pk_uuid;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedEpochGenerator;
import io.hypersistence.tsid.TSID;
import net.datafaker.Faker;
import org.openjdk.jmh.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.random.RandomGenerator;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class SelectBenchmark extends AbstractBenchmark {
    private static final int NUMBER_OF_RECORDS = 10_000;
    private static JdbcTemplate jdbcTemplate;
    private static JdbcClient jdbcClient;
    private final Faker faker = new Faker();
    private final TimeBasedEpochGenerator uuiv7Generator = Generators.timeBasedEpochGenerator();

    private final RandomGenerator randomGenerator = RandomGenerator.getDefault();
    private final List<String> textIds = new ArrayList<>();
    private final List<UUID> uuidIds = new ArrayList<>();
    private final List<UUID> uuidV7Ids = new ArrayList<>();
    private final List<Long> tsidIds = new ArrayList<>();


    @Autowired
    void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        SelectBenchmark.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    void setJdbcClient(JdbcClient jdbcClient) {
        SelectBenchmark.jdbcClient = jdbcClient;
    }

    @Setup
    public void clearTablesAndInsertData() {
        jdbcTemplate.execute("TRUNCATE TABLE guitar");
        jdbcTemplate.execute("TRUNCATE TABLE guitar_uuid");
        jdbcTemplate.execute("TRUNCATE TABLE guitar_uuid_v7");
        jdbcTemplate.execute("TRUNCATE TABLE guitar_tsid");

        insertWithTextType();
        insertWithUuidType();
        insertWithUuidV7Type();
        insertWithTsidType();
    }

    @Benchmark
    public void selectWithTextType() {
        for (int i = 0; i < 5000; i++) {
            jdbcClient.sql("SELECT * FROM guitar WHERE id = :id").param("id", textIds.get(randomGenerator.nextInt(textIds.size())))
                    .query()
                    .singleRow();
        }
    }

    @Benchmark
    public void selectWithUuidType() {
        for (int i = 0; i < 5000; i++) {
            jdbcClient.sql("SELECT * FROM guitar_uuid WHERE id = :id").param("id", uuidIds.get(randomGenerator.nextInt(uuidIds.size())))
                    .query()
                    .singleRow();
        }
    }

    @Benchmark
    public void selectWithUuidV7Type() {
        for (int i = 0; i < 5000; i++) {
            jdbcClient.sql("SELECT * FROM guitar_uuid_v7 WHERE id = :id").param("id", uuidV7Ids.get(randomGenerator.nextInt(uuidV7Ids.size())))
                    .query()
                    .singleRow();
        }
    }

    @Benchmark
    public void selectWithTsidType() {
        for (int i = 0; i < 5000; i++) {
            jdbcClient.sql("SELECT * FROM guitar_tsid WHERE id = :id").param("id", tsidIds.get(randomGenerator.nextInt(tsidIds.size())))
                    .query()
                    .singleRow();
        }
    }

    private void insertWithTextType() {
        jdbcTemplate.batchUpdate("INSERT INTO guitar (id, brand, model) VALUES(?,?,?)",
                new BatchPreparedStatementSetter() {

                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        String id = UUID.randomUUID().toString();
                        textIds.add(id);
                        ps.setString(1, id);
                        ps.setString(2, faker.brand().sport());
                        ps.setString(3, faker.camera().model());
                    }

                    @Override
                    public int getBatchSize() {
                        return NUMBER_OF_RECORDS;
                    }
                });
    }

    private void insertWithUuidType() {
        jdbcTemplate.batchUpdate("INSERT INTO guitar_uuid (id, brand, model) VALUES(?,?,?)",
                new BatchPreparedStatementSetter() {

                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        UUID id = UUID.randomUUID();
                        uuidIds.add(id);
                        ps.setObject(1, id);
                        ps.setString(2, faker.brand().sport());
                        ps.setString(3, faker.camera().model());
                    }

                    @Override
                    public int getBatchSize() {
                        return NUMBER_OF_RECORDS;
                    }
                });
    }


    private void insertWithUuidV7Type() {
        jdbcTemplate.batchUpdate("INSERT INTO guitar_uuid_v7 (id, brand, model) VALUES(?,?,?)",
                new BatchPreparedStatementSetter() {

                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        UUID id = uuiv7Generator.generate();
                        uuidV7Ids.add(id);
                        ps.setObject(1, id);
                        ps.setString(2, faker.brand().sport());
                        ps.setString(3, faker.camera().model());
                    }

                    @Override
                    public int getBatchSize() {
                        return NUMBER_OF_RECORDS;
                    }
                });
    }

    private void insertWithTsidType() {
        jdbcTemplate.batchUpdate("INSERT INTO guitar_tsid (id, brand, model) VALUES(?,?,?)",
                new BatchPreparedStatementSetter() {

                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        long id = TSID.Factory.getTsid().toLong();
                        tsidIds.add(id);
                        ps.setLong(1, id);
                        ps.setString(2, faker.brand().sport());
                        ps.setString(3, faker.camera().model());
                    }

                    @Override
                    public int getBatchSize() {
                        return NUMBER_OF_RECORDS;
                    }
                });
    }
}
