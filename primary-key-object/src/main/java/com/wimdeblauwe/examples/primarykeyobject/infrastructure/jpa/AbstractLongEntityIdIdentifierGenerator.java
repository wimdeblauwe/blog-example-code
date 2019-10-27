package com.wimdeblauwe.examples.primarykeyobject.infrastructure.jpa;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.Session;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;

public abstract class AbstractLongEntityIdIdentifierGenerator<T extends EntityId> implements IdentifierGenerator, Configurable {

    private String sequenceCallSyntax;

    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
        JdbcEnvironment jdbcEnvironment = serviceRegistry
                .getService(JdbcEnvironment.class);

        Dialect dialect = jdbcEnvironment.getDialect();

        final String sequencePerEntitySuffix = ConfigurationHelper
                .getString(
                        SequenceStyleGenerator.CONFIG_SEQUENCE_PER_ENTITY_SUFFIX,
                        params,
                        SequenceStyleGenerator.DEF_SEQUENCE_SUFFIX
                );

        boolean preferSequencePerEntity = ConfigurationHelper
                .getBoolean(
                        SequenceStyleGenerator.CONFIG_PREFER_SEQUENCE_PER_ENTITY,
                        params,
                        false
                );

        final String defaultSequenceName = preferSequencePerEntity
                ? params.getProperty(JPA_ENTITY_NAME) + sequencePerEntitySuffix
                : SequenceStyleGenerator.DEF_SEQUENCE_NAME;

        sequenceCallSyntax = dialect
                .getSequenceNextValString(
                        ConfigurationHelper.getString(
                                SequenceStyleGenerator.SEQUENCE_PARAM,
                                params,
                                defaultSequenceName
                        )
                );
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor session,
                                 Object obj) throws HibernateException {
        if (obj instanceof Entity) {
            Entity entity = (Entity) obj;
            EntityId id = entity.getId();

            if (id != null) {
                return id;
            }
        }

        long seqValue = ((Number)
                ((Session) session)
                        .createNativeQuery(sequenceCallSyntax)
                        .uniqueResult()
        ).longValue();

        return createEntityId(seqValue);
    }

    protected abstract T createEntityId(long seqValue);
}
