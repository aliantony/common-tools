package com.antiy.asset.convert;

import com.antiy.asset.util.NodeUtilsConverter;
import com.antiy.asset.util.ReflectionUtils;
import com.antiy.common.utils.LogUtils;
import org.slf4j.Logger;

import java.util.Objects;

public class NodeConverter extends NodeUtilsConverter {
    public final String         parentId = "parentId";
    private static final Logger logger   = LogUtils.get();

    @Override
    protected void convert(Object o, Object o2) {
        try {
            Integer i = (Integer) ReflectionUtils.invokeGetterMethod(o, parentId);
            ReflectionUtils.invokeSetterMethod(o2, parentId, Objects.toString(i));
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage());
        } catch (InstantiationException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        super.convert(o, o2);
    }
}
