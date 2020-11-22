package com.springboot;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class DatastoreSystemHealthTest {
	
	@Autowired
	DataSource dataSource;

	@Test
	void dbPrimaryIsOk() {
		try {
			DatabaseMetaData metadata = dataSource.getConnection().getMetaData();
			String catalog = dataSource.getConnection().getCatalog();
			assertThat(metadata).isNotNull();
			assertThat(catalog).isEqualTo("restapi");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
