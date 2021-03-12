package ru.geekbrains.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import ru.geekbrains.java4.lesson6.db.dao.CategoriesMapper;
import ru.geekbrains.java4.lesson6.db.dao.ProductsMapper;

import java.io.IOException;
import java.io.InputStream;

@UtilityClass
public class DBUtils {
    private static String resource = "mybatis-config.xml";

    @SneakyThrows
    public CategoriesMapper getCategoriesMapper () {
        SqlSession session = getSqlSession();
        return session.getMapper(CategoriesMapper.class);
    }

    @SneakyThrows
    public ProductsMapper getProductsMapper () {
        SqlSession session = getSqlSession();
        return session.getMapper(ProductsMapper.class);
    }

    private static SqlSession getSqlSession() throws IOException {
        SqlSessionFactory sqlSessionFactory;
        InputStream is = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        return sqlSessionFactory.openSession(true);
    }


}
