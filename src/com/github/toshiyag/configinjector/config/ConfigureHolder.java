package com.github.sunayy.configinjector.config;

import com.github.sunayy.configinjector.annotation.Configuration;
import com.github.sunayy.configinjector.annotation.Property;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public interface ConfigureHolder {

     static <T extends ConfigureHolder> T createInstance(Class<T> holderClass) throws IOException, ReflectiveOperationException {
        // Get a filepath specified in annotation given with the configuration class.
        final Configuration annotation = holderClass.getAnnotation(Configuration.class);
        final String confPath = annotation.resource();

        // Get target fields and load the .properties file.
        final Field[] confFields = holderClass.getDeclaredFields();
        final Properties properties = new Properties() {{
            load(Files.newBufferedReader(Paths.get(confPath)));
        }};

        // Set a value fetched from the .properties file to Configure fields.
         final Constructor<T> constructor = holderClass.getConstructor();
         constructor.setAccessible(true);

         final T confInstance = constructor.newInstance(null);

         for (final Field field : confFields) {
            final Property property = field.getAnnotation(Property.class);
            if (property == null) {
                continue;
            }
            final String key = property.name();
            final String foundValue = properties.getProperty(key);

            field.setAccessible(true);
            field.set(confInstance, foundValue);
        }

        return confInstance;
    }
}
