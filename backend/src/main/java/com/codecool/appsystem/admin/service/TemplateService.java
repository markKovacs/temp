package com.codecool.appsystem.admin.service;

import com.codecool.appsystem.admin.model.EmailTemplate;
import com.codecool.appsystem.admin.repository.EmailTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemplateService {

    @Autowired
    private EmailTemplateRepository emailTemplateRepository;

    public void modifyTemplate(EmailTemplate emailTemplate) {
        emailTemplate.setModel(emailTemplate.getModel());
        emailTemplate.setTemplate(emailTemplate.getTemplate());
        emailTemplateRepository.save(emailTemplate);
    }
}
