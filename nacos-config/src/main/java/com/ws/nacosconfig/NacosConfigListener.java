package com.ws.nacosconfig;

import com.alibaba.nacos.api.config.ConfigChangeEvent;
import com.alibaba.nacos.api.config.ConfigChangeItem;
import com.alibaba.nacos.client.config.listener.impl.AbstractConfigChangeListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class NacosConfigListener extends AbstractConfigChangeListener {

    @Autowired
    private ApplicationContext context;

    @Override
    public void receiveConfigChange(ConfigChangeEvent event) {
        List<ConfigChangeItem> items = (List<ConfigChangeItem>) event.getChangeItems();

        log.info("Nacos 配置改变: {}", items);
    }
}
