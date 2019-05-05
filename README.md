
1 .提交注释规范说明：

    特别注意：冒号后面跟一个空格
    feat：新功能（feature）
    fix：修补bug
    docs：文档（documentation）
    style： 格式（不影响代码运行的变动）
    refactor：重构（即不是新增功能，也不是修改bug的代码变动）
    test：增加测试
    chore：构建过程或辅助工具的变动



2. 本地代码提交前checkstyle & 注释规范
       依赖环境：python（2.7版本） node
    a.父工程执行目录下(具体路径参考本地配置)
    
        * git config --add checkstyle.jar config/checkstyle-8.9.jar
       
        * git config --add checkstyle.checkfile config/check_style.xml
       
        * cp config/pre-commit ./.git/hooks/
        
        * cp config/check.py ./.git/hooks/

    b.  首次clone 执行npm install (父工程执行目录下)

3. 代码结构说明



4. 代码生成说明：参考 asset  AssetGenerator 
        
