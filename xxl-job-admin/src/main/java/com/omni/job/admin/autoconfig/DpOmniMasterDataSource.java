package com.omni.job.admin.autoconfig;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;

@Slf4j
@Configuration
@MapperScan(basePackages = {"com.omni.job.admin.dao"}, sqlSessionTemplateRef  = "masterDpAllocationSqlSessionTemplate")
public class DpOmniMasterDataSource {

    @Bean(name = "masterDpAllocationDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    @Primary
    public DataSource masterDpAllocationDataSource() {
        DruidDataSource queryDataSourceMaster = new DruidDataSource();
        queryDataSourceMaster.setDriverClassName("com.mysql.jdbc.Driver");

        /**配置初始化大小、最小、最大*/
        queryDataSourceMaster.setInitialSize(10);
        queryDataSourceMaster.setMinIdle(10);
        queryDataSourceMaster.setMaxActive(30);

        /**配置获取连接等待超时的时间*/
        queryDataSourceMaster.setMaxWait(60000);

        /**配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒*/
        queryDataSourceMaster.setTimeBetweenEvictionRunsMillis(60000);

        /**配置一个连接在池中最小生存的时间，单位是毫秒*/
        queryDataSourceMaster.setMinEvictableIdleTimeMillis(300000);
        queryDataSourceMaster.setValidationQuery("select 1 ");

        /**打开后，增强timeBetweenEvictionRunsMillis的周期性连接检查，minIdle内的空闲连接，每次检查强制验证连接有效性. 参考：https://github.com/alibaba/druid/wiki/KeepAlive_cn*/
        queryDataSourceMaster.setKeepAlive(true);
        queryDataSourceMaster.setTestWhileIdle(true);

        /**这里建议配置为TRUE，防止取到的连接不可用。获取链接的时候，不校验是否可用，开启会有损性能*/
        queryDataSourceMaster.setTestOnBorrow(true);
        /**归还链接到连接池的时候校验链接是否可用*/
        queryDataSourceMaster.setTestOnReturn(false);


        /**打开PSCache，并且指定每个连接上PSCache的大小*/
        queryDataSourceMaster.setPoolPreparedStatements(true);
        queryDataSourceMaster.setMaxPoolPreparedStatementPerConnectionSize(20);
        try {
            queryDataSourceMaster.setFilters("stat");
        } catch (SQLException e) {
            log.error("queryDataSourceMaster.setFilters error", e);
        }

        return queryDataSourceMaster;
    }


    @Bean(name = "masterDpAllocationSqlSessionFactory")
    public SqlSessionFactory masterDpAllocationSqlSessionFactory(@Qualifier("masterDpAllocationDataSource") DataSource masterDpAllocationDataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(masterDpAllocationDataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "masterDpAllocationTransactionManager")
    public DataSourceTransactionManager masterDpAllocationTransactionManager(@Qualifier("masterDpAllocationDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "masterDpAllocationSqlSessionTemplate")
    public SqlSessionTemplate masterDpAllocationSqlSessionTemplate(@Qualifier("masterDpAllocationSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        SqlSessionTemplate masterSqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
        masterSqlSessionTemplate.getConfiguration().setMapUnderscoreToCamelCase(true);
        return masterSqlSessionTemplate;
    }

    @Bean(name = "masterTransactionTemplate")
    public TransactionTemplate masterTransactionTemplate(@Qualifier("masterDpAllocationTransactionManager") DataSourceTransactionManager dataSourceTransactionManager) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(dataSourceTransactionManager);
        transactionTemplate.setTimeout(15);
        return transactionTemplate;
    }

}
