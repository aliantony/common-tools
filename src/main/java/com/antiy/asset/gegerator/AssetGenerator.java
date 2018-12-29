package com.antiy.asset.gegerator;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuyu
 * @since 2018/11/12
 */

public class AssetGenerator {

    /**
     * 代码输出路径
     */
    private String outPutDir = "输出路径 比如F:\\Ya";

    /**
     * 代码作者
     */
    private String author = "xxxxxxxxx";

    /**
     * 代码包路径
     */
    private String parent = "com.antiy";

    /**
     * 模块名
     */
    private String moduleName = "模块名";

    /**
     * <p> 根据MySQL 表生成对象（controller,service,dao,entity,mappper.xml ） </p>
     */
    public static void main(String[] args) {
        AssetGenerator ag = new AssetGenerator();
        ag.exceute(ag.outPutDir, ag.author, ag.parent);
    }

    public void exceute(String outPutDir, String author, String parent) {
        if (author == null || author.equals("")){
            System.out.println("作者不能为空！！！");
            return;
        }

        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(outPutDir);
        gc.setFileOverride(true);//覆盖旧文件
        gc.setActiveRecord(true);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML columList
        //gc.setKotlin(true);//是否生成 kotlin 代码
        gc.setAuthor(author);

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setMapperName("%sDao");
        // gc.setXmlName("%sDao");
        // gc.setServiceName("MP%sService");
        // gc.setServiceImplName("%sServiceDiy");
        // gc.setControllerName("%sAction");
        mpg.setGlobalConfig(gc);
        setDbConfig(mpg);


// ------------策略配置start-----------
//        配置需要生成代码的表
        StrategyConfig strategy = new StrategyConfig();
        // 表名生成策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        // 需要生成的表
//        strategy.setInclude(new String[] {"asset","asset_category_model","asset_cpu","asset_department",
//                "asset_group","asset_group_relation","asset_hard_disk","asset_label_relation","asset_lable",
//                "asset_link_relation","asset_mainborad","asset_memory","asset_network_card","asset_network_equipment",
//                "asset_port_protocol","asset_safety_equipment","asset_software","asset_software_license","asset_software_relation","asset_user","scheme"});

        strategy.setInclude(new String[]{"asset_user"});

//        strategy.setInclude(new String[] {"scheme"});
        strategy.setEntityColumnConstant(true);
        mpg.setStrategy(strategy);

        // ------------策略配置end-----------

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(parent);
        pc.setController("controller");
        pc.setEntity("entity");
        pc.setMapper("dao");
        pc.setModuleName(moduleName);
        mpg.setPackageInfo(pc);


        setTemplate(mpg);

        //-----------指定mapper.xml输出路径start-----------
        // 自定义输出配置
//        String projectPath = System.getProperty("user.dir");
        String projectPath = outPutDir;
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        String templatePath = "/template/mapper.xml.vm";
        String requestPath = "/template/request.java.vm";
        String responsePath = "/template/response.java.vm";
        String queryPath = "/template/query.java.vm";
        List<FileOutConfig> focList = new ArrayList<>();
//         自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return projectPath + "/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        focList.add(new FileOutConfig(requestPath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return projectPath + "/src/main/java/com/antiy/"+ moduleName +"/entity/vo/request/" + tableInfo.getEntityName() + "Request" + StringPool.DOT_JAVA;
            }
        });

        focList.add(new FileOutConfig(responsePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return projectPath + "/src/main/java/com/antiy/"+ moduleName + "/entity/vo/response/" + tableInfo.getEntityName() + "Response" + StringPool.DOT_JAVA;
            }
        });

        focList.add(new FileOutConfig(queryPath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return projectPath + "/src/main/java/com/antiy/"+ moduleName +"/entity/vo/query/" + tableInfo.getEntityName() + "Query" + StringPool.DOT_JAVA;
            }
        });

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        //-----------指定mapper.xml输出路径end-----------

        // 执行生成
        mpg.execute();
    }


    public DbColumnType pTypeConvert(String fieldType) {
        String t = fieldType.toLowerCase();
        if (!t.contains("char") && !t.contains("text")) {
            if (t.contains("bigint")) {
                return DbColumnType.LONG;
            } else if (t.contains("int")) {
                return DbColumnType.INTEGER;
            } else if (!t.contains("date") && !t.contains("time") && !t.contains("year")) {
                if (t.contains("text")) {
                    return DbColumnType.STRING;
                } else if (t.contains("bit")) {
                    return DbColumnType.BOOLEAN;
                } else if (t.contains("decimal")) {
                    return DbColumnType.BIG_DECIMAL;
                } else if (t.contains("clob")) {
                    return DbColumnType.CLOB;
                } else if (t.contains("blob")) {
                    return DbColumnType.BLOB;
                } else if (t.contains("binary")) {
                    return DbColumnType.BYTE_ARRAY;
                } else if (t.contains("float")) {
                    return DbColumnType.FLOAT;
                } else if (t.contains("double")) {
                    return DbColumnType.DOUBLE;
                } else {
                    return !t.contains("json") && !t.contains("enum") ? DbColumnType.STRING : DbColumnType.STRING;
                }
            } else {
                return DbColumnType.DATE;
            }
        } else {
            return DbColumnType.STRING;
        }
    }




    /**
     * 数据源配置
     *
     * @param mpg mp
     */
    public void setDbConfig(AutoGenerator mpg) {
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setTypeConvert(new MySqlTypeConvert() {
            // 自定义数据库表字段类型转换【可选】
            @Override
            public DbColumnType processTypeConvert(String fieldType) {
                return super.processTypeConvert(fieldType);
            }
        });
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("1234");
        dsc.setUrl("jdbc:mysql://10.240.17.29:3306/asset?characterEncoding=utf8");
        mpg.setDataSource(dsc);
    }

    /**
     * 模版在common 工程 resources 下 自行修改路径
     *
     * @param mpg mp
     */

    public void setTemplate(AutoGenerator mpg) {
        TemplateConfig tc = new TemplateConfig();
        tc.setController("/template/controller.java.vm");
        tc.setService("/template/service.java.vm");
        tc.setServiceImpl("/template/serviceImpl.java.vm");
        tc.setEntity("/template/entity.java.vm");
        tc.setMapper("/template/mapper.java.vm");
        mpg.setTemplate(tc);
    }

}
