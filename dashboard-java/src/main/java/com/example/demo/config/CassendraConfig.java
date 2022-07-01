package com.example.demo.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.DropKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

//@Configuration
@EnableCassandraRepositories(basePackages = "com.example.demo.repository")
public class CassendraConfig extends  AbstractCassandraConfiguration{
	
	public static final String KEYSPACE = "dashboardcomponents";

	@Override
	public SchemaAction getSchemaAction() {
		// TODO Auto-generated method stub
		return SchemaAction.CREATE_IF_NOT_EXISTS;
	}

	@Override
	protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
		CreateKeyspaceSpecification spec = CreateKeyspaceSpecification.createKeyspace(KEYSPACE)
				.with(KeyspaceOption.DURABLE_WRITES, false);
		return Arrays.asList(spec);
	}

	@Override
	protected String getKeyspaceName() {
		return KEYSPACE;
	}

	@Override
	protected List<DropKeyspaceSpecification> getKeyspaceDrops() {
		// TODO Auto-generated method stub
		return Arrays.asList(DropKeyspaceSpecification.dropKeyspace(KEYSPACE));
	}

	@Override
	public String[] getEntityBasePackages() {
		return new String[] {"com.example.demo.model"};
	}
}
