package com.hippocp.mycommand.builder;

import com.hippocp.mycommand.builder.xml.XMLMapperBuilder;
import com.hippocp.mycommand.io.Resources;
import com.hippocp.mycommand.session.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author ZhouYifan
 * @date 2022/3/2
 */
public class XmlMapperBuilderTest {
    @Test
    public void shouldSuccessfullyLoadXMLMapperFile() throws Exception {
        Configuration configuration = new Configuration();
        String resource = "command-mapper/TranscodeMapper.xml";
        try (InputStream inputStream = Resources.getResourceAsStream(resource)) {
            XMLMapperBuilder builder = new XMLMapperBuilder(inputStream, configuration, resource, configuration.getSqlFragments());
            builder.parse();
        }
    }

    @Test
    public void test() throws IOException {
        String resource = "command-mapper/TranscodeMapper.xml";
        ClassLoader cl = this.getClass().getClassLoader();
        try (InputStream inputStream = cl.getResourceAsStream(resource)) {
            Assertions.assertNotNull(inputStream);
        }
    }
}
