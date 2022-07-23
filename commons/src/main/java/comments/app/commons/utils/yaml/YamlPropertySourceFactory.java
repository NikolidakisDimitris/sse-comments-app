package comments.app.commons.utils.yaml;

import org.springframework.beans.factory.config.*;
import org.springframework.core.env.*;
import org.springframework.core.io.support.*;

import java.io.*;
import java.util.*;

public class YamlPropertySourceFactory implements PropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource encodedResource) throws IOException {

        YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
        factory.setResources(encodedResource.getResource());

        Properties properties = factory.getObject();

        if (encodedResource.getResource().getFilename() == null || properties == null){
            //IOException here is quite loose, they could be other reasons
            throw new IOException();
        }

        return new PropertiesPropertySource(encodedResource.getResource().getFilename(), properties);
    }
}