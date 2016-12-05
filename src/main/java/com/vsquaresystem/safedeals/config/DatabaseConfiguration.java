
package com.vsquaresystem.safedeals.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 *
 * @author ruchita
 */
@Configuration
@Profile("container")
public class DatabaseConfiguration {
    
    @Value("${safedeals.db.driver_class}")
    private String driverClassname;

    @Value("${safedeals.db.connection_string}")
    private String connectionString;

    @Bean
    public DataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setProperties(getMySqlProperties());
        cpds.setDriverClass(driverClassname);
        cpds.setJdbcUrl(connectionString);
        cpds.setAcquireIncrement(2);
        return cpds;
    }

    public Properties getMySqlProperties() {
        Properties mysqlProperties = new Properties();
        mysqlProperties.setProperty("characterEncoding", "UTF-8");
        mysqlProperties.setProperty("useUnicode", "true");
        return mysqlProperties;
    }
    
}
