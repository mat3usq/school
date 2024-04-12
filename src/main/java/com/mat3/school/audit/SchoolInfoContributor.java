package com.mat3.school.audit;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SchoolInfoContributor implements InfoContributor {
    @Override
    public void contribute(Info.Builder builder) {
        Map<String, String> eazyMap = new HashMap<>();
        eazyMap.put("App Name", "Schoolify");
        eazyMap.put("App Description", "Schoolify Web Application for Student's and Teacher's");
        eazyMap.put("App Version", "1.0.0");
        eazyMap.put("Contact Email", "info@schoolify.com");
        eazyMap.put("Contact Mobile", "123 456 789");
        builder.withDetail("schoolify-info", eazyMap);
    }
}
