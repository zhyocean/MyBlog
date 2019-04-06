package com.zhy.poolTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

/**
 * @author: zhangocean
 * @Date: 2018/9/9 14:05
 * Describe:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DatasourceTest {

    @Autowired
    ApplicationContext applicationContext;

    @Test
    public void poolTest(){
        DataSource dataSource = applicationContext.getBean(DataSource.class);
        System.out.println("数据库连接池：" + dataSource.getClass().getSimpleName());
    }

}
