package com.hmall.Utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

import java.io.InputStreamReader;
import java.util.Properties;


public class PropsUtil {

    private static org.slf4j.Logger logger=LoggerFactory.getLogger(PropsUtil.class);

    private static Properties properties;

    static {
        String fileName="hmall.properties";
        try{
            properties.load(new InputStreamReader(PropsUtil.class.getClassLoader().getResourceAsStream(fileName),"UTF-8"));
        }
        catch (Exception e){
            logger.error("Configuration Error",e);
        }
    }

    public static String getProerty(String key){
        String value=properties.getProperty(key.trim());
        if (StringUtils.isBlank(value)){
            return null;
        }
        return value.trim();
    }

    public static String getProerty(String key,String defaultvalue){
        String value=properties.getProperty(key.trim());
        if (StringUtils.isBlank(value)){
            return defaultvalue;
        }
        return value.trim();
    }



}
