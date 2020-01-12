package com.github.toshiyag.configinjector.config;

import com.github.toshiyag.configinjector.annotation.Configuration;
import com.github.toshiyag.configinjector.annotation.Property;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ConfigureHolderTest {

    @Test
    void 正常_設定数1() {
        try {
            final TestConfigHolder1 holder1 = ConfigureHolder.createInstance(TestConfigHolder1.class);
            assertEquals("hogehoge", holder1.getName());
        } catch (IOException | ReflectiveOperationException e) {
            fail(e);
        }
    }

    @Test
    void 正常_設定数2() {
        try {
            final TestConfigHolder2 holder = ConfigureHolder.createInstance(TestConfigHolder2.class);
            assertEquals("hogehoge", holder.getName());
            assertEquals("fugafuga", holder.getPass());
        } catch (IOException | ReflectiveOperationException e) {
            fail(e);
        }
    }

    @Test
    void 正常_設定数3重複込() {
        try {
            final TestConfigHolder3 holder = ConfigureHolder.createInstance(TestConfigHolder3.class);
            assertEquals("hogehoge", holder.getName());
            assertEquals("fugafuga", holder.getPass());
            assertEquals("hogehoge", holder.getName2());
        } catch (IOException | ReflectiveOperationException e) {
            fail(e);
        }
    }

    @Test
    void 異常_存在しないプロパティファイル() {
        final Executable executable = () ->  ConfigureHolder.createInstance(TestConfigHolder4.class);
        assertThrows(IOException.class, () -> executable.execute());
    }

    @Test
    void 異常_明示的なコンストラクタの定義なし() {
        final Executable executable = () -> ConfigureHolder.createInstance(TestConfigHolder5.class);
        assertThrows(ReflectiveOperationException.class, () -> executable.execute());
    }

}

@Configuration(resource = "resource/conf.properties")
class TestConfigHolder1 implements ConfigureHolder {
    @Property(name = "name")
    private String name = null;

    String getName() {
        return name;
    }

    public TestConfigHolder1() {}
}

@Configuration(resource = "resource/conf.properties")
class TestConfigHolder2 implements ConfigureHolder {
    @Property(name = "name")
    private String name = null;

    @Property(name = "pass")
    private String pass = null;

    String getPass() {
        return pass;
    }

    String getName() {
        return name;
    }

    public TestConfigHolder2() {}
}

@Configuration(resource = "resource/conf.properties")
class TestConfigHolder3 implements ConfigureHolder {
    @Property(name = "name")
    private String name = null;

    @Property(name = "pass")
    private String pass = null;

    @Property(name = "name")
    private String name2 = null;

    String getPass() {
        return pass;
    }

    String getName() {
        return name;
    }

    String getName2() {
        return name2;
    }

    public TestConfigHolder3() {}
}

@Configuration(resource = "resource/dummy.properties")
class TestConfigHolder4 implements ConfigureHolder {

    public TestConfigHolder4() {}
}

@Configuration(resource = "resource/conf.properties")
class TestConfigHolder5 implements ConfigureHolder {

}