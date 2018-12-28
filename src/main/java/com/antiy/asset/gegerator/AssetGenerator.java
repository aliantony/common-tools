package com.antiy.asset.gegerator;

import com.antiy.asset.utils.MpGenerator;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * @author liuyu
 * @since 2018/11/12
 */

public class AssetGenerator extends MpGenerator {

    /**
     * 代码输出路径
     */
    private String outPutDir = "F:\\antiy\\project\\antiy-csos\\csos-asset\\src\\main\\java";

    /**
     * 代码作者
     */
    private String author    = "zhangyajun";

    /**
     * 代码包路径
     */
    private String parent    = "com.antiy.asset";

    /**
     * <p> 根据MySQL 表生成对象（controller,service,dao,entity,mappper.xml ） </p>
     *
     */
    public static void main(String[] args) {
        AssetGenerator ag = new AssetGenerator();
        ag.exceute(ag.outPutDir, ag.author, ag.parent);
    }


    /**
     *
     * 配置需要生成代码的表
     * @param mpg
     */
    @Override
    public void setStrategy(AutoGenerator mpg) {
        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // 表名生成策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        // 需要生成的表
        strategy.setInclude(new String[] {"asset","asset_category_model","asset_cpu","asset_department",
                "asset_group","asset_group_relation","asset_hard_disk","asset_label_relation","asset_lable",
                "asset_link_relation","asset_mainborad","asset_memory","asset_network_card","asset_network_equipment",
                "asset_port_protocol","asset_safety_equipment","asset_software","asset_software_license","asset_software_relation","asset_user"});

//        strategy.setInclude(new String[] {"asset_user"});
        strategy.setEntityColumnConstant(true);
        mpg.setStrategy(strategy);
    }

    /**
     * 数据源配置
     *
     * @param mpg mp
     */
    @Override
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
     * @param mpg mp
     */

    @Override
    public void setTemplate(AutoGenerator mpg) {
        TemplateConfig tc = new TemplateConfig();
        tc.setController("/template/controller.java.vm");
        tc.setService("/template/service.java.vm");
        tc.setServiceImpl("/template/serviceImpl.java.vm");
        tc.setEntity("/template/entity.java.vm");
        tc.setMapper("/template/mapper.java.vm");
        tc.setXml("/template/mapper.xml.vm");
        mpg.setTemplate(tc);
    }

}
