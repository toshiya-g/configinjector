# configinjector

車輪の再発明的なものです。<br>
外部からの設定値の注入を目的としています。

## 説明

各設定値はConfigureHolderの実装クラスによって管理される想定です。<br>
ConfigureHolder実装クラス及びそのフィールドにアノテーションを付与することで外部の値の注入を行います。<br>

### annotations
#### @Configuration
ConfigureHolder実装クラスへ指定します。<br>
設定値は.propertiesファイルに記述される想定の実装であり、'resource'パラメタへ該当ファイルパスを指定します。<br>

```java
@Configuration(resource = "resource/dummy.properties")
class TestConfigHolder implements ConfigureHolder {

    public TestConfigHolder() {}
}
```
#### @Property
.propertiesにて記述されている、key値の対応付けをします。
```java
@Configuration(resource = "resource/dummy.properties")
class TestConfigHolder implements ConfigureHolder {
    @Property(name = "name")
    private String name = null;

    @Property(name = "pass")
    private String pass = null;

    @Property(name = "name")
    private String name2 = null;
    
    public TestConfigHolder() {}
}
```
### 使い方
アノテーションが付与された、ConfigureHolder実装クラスについて、ConfigureHolder#createInstanceにて参照を得られます。<br>
（ConfigureHolderTest.javaのような感じで）
