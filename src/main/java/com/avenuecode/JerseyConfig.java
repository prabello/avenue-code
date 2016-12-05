package com.avenuecode;

import com.avenuecode.service.ProductEndpoint;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig(){
        register(ProductEndpoint.class);
    }
}
