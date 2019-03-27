/*
 * Copyleft
 */
package com.wong.schedule.core.config;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

/**
 * web mvc 相关拓展配置项
 * @author 黄小天
 * @date 2019-03-11 17:00
 * @version 1.0
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converterList) {
        // 配置 fastJaon 为默认的 mvc 数据转换器
        Iterator<HttpMessageConverter<?>> iterator = converterList.iterator();
        while(iterator.hasNext()){
            HttpMessageConverter<?> converter = iterator.next();
            if(converter instanceof MappingJackson2HttpMessageConverter){
                iterator.remove();
            }
        }
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);
        List<MediaType> mediaType = new ArrayList<>();
        mediaType.add(MediaType.APPLICATION_JSON_UTF8);
        converter.setSupportedMediaTypes(mediaType);
        converter.setFastJsonConfig(config);
        converterList.add(converter);
    }
}
