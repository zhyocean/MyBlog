package com.zhy.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: zhangocean
 * @Date: 2018/9/8 14:37
 * Describe: Druid 数据库连接池配置
 */
@Configuration
@ConditionalOnClass(DruidDataSource.class)
@ConditionalOnProperty(name = "spring.dataSource.type", havingValue = "com.alibaba.druid.pool.DruidDataSource", matchIfMissing = true)
@Slf4j
public class DruidDataSourceConfig {

    @Value("${druidUsername}")
    private String druidUsername;
    @Value("${druidPassword}")
    private String druidPassword;

    @Bean(name = "druidDataSource")
    @Primary
    public DataSource dataSource(@Autowired Environment environment){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(environment.getProperty("spring.datasource.url"));
        dataSource.setUsername(environment.getProperty("spring.datasource.username"));
        dataSource.setPassword(environment.getProperty("spring.datasource.password"));
        dataSource.setDriverClassName(environment.getProperty("spring.datasource.driverClassName"));

        dataSource.setInitialSize(Integer.parseInt(environment.getProperty("spring.datasource.druid.initial-size")));
        dataSource.setMinIdle(Integer.parseInt(environment.getProperty("spring.datasource.druid.min-idle")));
        dataSource.setMaxActive(Integer.parseInt(environment.getProperty("spring.datasource.druid.max-active")));
        // 配置获取连接等待超时的时间
        dataSource.setMaxWait(Long.parseLong(environment.getProperty("spring.datasource.druid.max-wait")));
        // 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        dataSource.setTimeBetweenEvictionRunsMillis(Long.parseLong(environment.getProperty("spring.datasource.druid.time-between-eviction-runs-millis")));
        // 配置一个连接在池中最小生存的时间，单位是毫秒
        dataSource.setMinEvictableIdleTimeMillis(Long.parseLong(environment.getProperty("spring.datasource.druid.min-evictable-idle-time-millis")));
        dataSource.setValidationQuery(environment.getProperty("spring.datasource.druid.validation-query"));
        dataSource.setTestWhileIdle(Boolean.parseBoolean(environment.getProperty("spring.datasource.druid.test-while-idle")));
        dataSource.setTestOnBorrow(Boolean.parseBoolean(environment.getProperty("spring.datasource.druid.test-on-borrow")));
        dataSource.setTestOnReturn(Boolean.parseBoolean(environment.getProperty("spring.datasource.druid.test-on-return")));
        dataSource.setPoolPreparedStatements(Boolean.parseBoolean(environment.getProperty("spring.datasource.druid.pool-prepared-statements")));
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(Integer.parseInt(environment.getProperty("spring.datasource.druid.max-pool-prepared-statement-per-connection-size")));
        try {
            dataSource.setFilters(environment.getProperty("spring.datasource.druid.filters"));
        } catch (SQLException e) {
            log.error("druid configuration initialization filter", e);
        }
        return dataSource;
    }

    /**
     * 配置Druid监控的StatViewServlet和WebStatFilter
     */
    @Bean
    public ServletRegistrationBean druidServlet(){
        log.info("init Druid Servlet Configuration ");
      ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
      servletRegistrationBean.setServlet(new StatViewServlet());
      servletRegistrationBean.addUrlMappings("/druid/*");
      Map<String, String> initParameters = new HashMap<String, String>();
      initParameters.put("loginUsername", druidUsername);
      initParameters.put("loginPassword", druidPassword);
      initParameters.put("resetEnable", "true");
        //下面是黑白名单，多个ip地址之间用逗号隔开
//      initParameters.put("allow", "119.23.202.55,127.0.0.1,10.24.38.152");
//      initParameters.put("deny", "119.23.202.55");
      servletRegistrationBean.setInitParameters(initParameters);

      return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
      filterRegistrationBean.setFilter(new WebStatFilter());
      filterRegistrationBean.addUrlPatterns("/*");
      filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
      return filterRegistrationBean;
    }

}
