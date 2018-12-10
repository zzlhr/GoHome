package com.lhrsite.gohome;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 配置类
 */
class Config {


    private static Properties properties;

    static {
        properties = new Properties();

        InputStream inputStream = Config.class.getClassLoader()
                .getResourceAsStream("config/gohome.properties");

        try {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("获取配置文件失败！");
        }


    }

    static String getValue(String key) {
        return properties.getProperty(key);
    }


}
