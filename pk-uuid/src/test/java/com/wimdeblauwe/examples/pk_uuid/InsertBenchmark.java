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

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class InsertBenchmark extends AbstractBenchmark {
    private static JdbcTemplate jdbcTemplate;
    private final Faker faker = new Faker();
    private final TimeBasedEpochGenerator uuiv7Generator = Generators.timeBasedEpochGenerator();



    @Autowired
    void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        InsertBenchmark.jdbcTemplate = jdbcTemplate;
    }

    @Setup
    public void clearTables() {
        jdbcTemplate.execute("TRUNCATE TABLE guitar");
        jdbcTemplate.execute("TRUNCATE TABLE guitar_uuid");
        jdbcTemplate.execute("TRUNCATE TABLE guitar_uuid_v7");
        jdbcTemplate.execute("TRUNCATE TABLE guitar_tsid");
    }

    @Benchmark
    public void insertWithTextType() {
        jdbcTemplate.batchUpdate("INSERT INTO guitar (id, brand, model) VALUES(?,?,?)",
                new BatchPreparedStatementSetter() {

                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, UUID.randomUUID().toString());
                        ps.setString(2, faker.brand().sport());
                        ps.setString(3, faker.camera().model());
                    }

                    @Override
                    public int getBatchSize() {
                        return 10_000;
                    }
                });
    }

    @Benchmark
    public void insertWithUuidType() {
        jdbcTemplate.batchUpdate("INSERT INTO guitar_uuid (id, brand, model) VALUES(?,?,?)",
                new BatchPreparedStatementSetter() {

                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setObject(1, UUID.randomUUID());
                        ps.setString(2, faker.brand().sport());
                        ps.setString(3, faker.camera().model());
                    }

                    @Override
                    public int getBatchSize() {
                        return 10_000;
                    }
                });
    }

    @Benchmark
    public void insertWithUuidV7Type() {
        jdbcTemplate.batchUpdate("INSERT INTO guitar_uuid_v7 (id, brand, model) VALUES(?,?,?)",
                new BatchPreparedStatementSetter() {

                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setObject(1, uuiv7Generator.generate());
                        ps.setString(2, faker.brand().sport());
                        ps.setString(3, faker.camera().model());
                    }

                    @Override
                    public int getBatchSize() {
                        return 10_000;
                    }
                });
    }

    @Benchmark
    public void insertWithTsidType() {
        jdbcTemplate.batchUpdate("INSERT INTO guitar_tsid (id, brand, model) VALUES(?,?,?)",
                new BatchPreparedStatementSetter() {

                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, TSID.Factory.getTsid().toLong());
                        ps.setString(2, faker.brand().sport());
                        ps.setString(3, faker.camera().model());
                    }

                    @Override
                    public int getBatchSize() {
                        return 10_000;
                    }
                });
    }
}
