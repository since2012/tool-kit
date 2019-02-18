package org.mark.fastjson.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.mark.fastjson.converter.StringToDateConverter;

import javax.annotation.PostConstruct;
import java.util.List;

public class DefaultWebConfig extends DefaultFastjsonConfig {

    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;

    /**
     * 使用阿里 FastJson 作为JSON MessageConverter
     *
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //将转换器添加到converters中
        converters.add(fastJsonHttpMessageConverter());
        // 这里必须加上加载默认转换器，不然bug玩死人，
        addDefaultHttpMessageConverters(converters);
    }

    @PostConstruct
    public void addConversionConfig() {
        ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer) handlerAdapter.getWebBindingInitializer();
        GenericConversionService genericConversionService = (GenericConversionService) initializer.getConversionService();
        genericConversionService.addConverter(new StringToDateConverter());
    }

}


