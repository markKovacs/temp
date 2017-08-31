package com.codecool.appsystem.admin.service;

import com.codecool.appsystem.admin.model.Application;
import com.codecool.appsystem.admin.model.Email;
import com.codecool.appsystem.admin.model.Location;
import com.codecool.appsystem.admin.model.User;
import com.codecool.appsystem.admin.repository.EmailTemplateRepository;
import com.codecool.appsystem.admin.repository.LocationRepository;
import com.codecool.appsystem.admin.repository.UserRepository;
import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.MustacheException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class EmailService {

    @Autowired
    private RestTemplate emailSenderRestTemplate;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private EmailTemplateRepository emailTemplateRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Mustache.Compiler compiler;

    //@Value("${mailgun.adapter.send.url}")
    private String mailgunAdapterUrl;

    //@Value("${server.redirecturl.basepath}")
    private String serverBasePath;

    //@Value("${server.frontend.basepath}")
    private String frontendUrl;

    //@Value("${application.expiry.days:1}")
    private Integer days;

    public void sendInitialEmail(User user){

        log.info("Sending initial email to: {}", user.getId());

        Location location = locationRepository.findOne(user.getLocationId());

        Map<String, String> context = new HashMap<>();
        context.put("firstName", user.getGivenName());
        context.put("location", location.getName());
        context.put("nextCourseStart", location.getNextCourseStart());
        context.put("replyToAddress", location.getEmailAddress());
        context.put("unsubscribeLink", getUnsubscribeLink(user));
        context.put("appUrl", frontendUrl);

        String html = "";

        try {
            html = compiler
                    .compile(getTemplate(location, "initial"))
                    .execute(context);

            Email email = Email.builder()
                    .fromAddress(location.getEmailAddress())
                    .subject(location.getSubjectPrefix() + " - Welcome")
                    .toAddress(user.getId())
                    .html(html)
                    .build();

            send(email);

        } catch (MustacheException e){
            log.error("Failed to parse template: {}", e.getMessage());
        }

    }

    public void sendStepPassed(User user, Application application, String testName, Integer result){
        sendStepMail(user, true, testName, result, application.getProcessStartedAt());
    }

    public void sendStepFailed(User user, Application application, String testName, Integer result){
        sendStepMail(user, false, testName, result, application.getProcessStartedAt());
    }

    public void sendDeadlinePassed(Application application){

        User user = userRepository.findOne(application.getApplicantId());

        Location location = locationRepository.findOne(user.getLocationId());

        log.info("Sending deadline passed email to: {}", user.getId());

        Date deadLine = Date.from(
                application.getProcessStartedAt()
                        .toInstant()
                        .plus(days, ChronoUnit.DAYS)
        );

        Map<String, String> context = new HashMap<>();
        context.put("firstName", user.getGivenName());
        context.put("deadline", new SimpleDateFormat("yyyy.MM.dd").format(deadLine));
        context.put("replyToAddress", location.getEmailAddress());
        context.put("unsubscribeLink", getUnsubscribeLink(user));
        context.put("appUrl", frontendUrl);

        String template = getTemplate(location, "deadline-passed");

        String html = null;/*compiler
                .compile(template)
                .execute(context);
*/

        Email email = Email.builder()
                .fromAddress(location.getEmailAddress())
                .subject(location.getSubjectPrefix() + " - Deadline passed")
                .toAddress(user.getId())
                .html(html)
                .build();

        send(email);
    }

    private void sendStepMail(User user, Boolean success, String testName, Integer result, Date processStartedAt){

        log.info("Sending step ({}) {} email to: {}", testName, success ? "success": "failed", user.getId());

        Location location = locationRepository.findOne(user.getLocationId());

        Date deadLine = Date.from(
                processStartedAt
                        .toInstant()
                        .plus(days, ChronoUnit.DAYS)
        );

        Map<String, String> context = new HashMap<>();
        context.put("firstName", user.getGivenName());
        context.put("testName", testName);
        context.put("result", result.toString());
        context.put("deadline", new SimpleDateFormat("yyyy.MM.dd").format(deadLine));
        context.put("appUrl", frontendUrl);
        context.put("replyToAddress", location.getEmailAddress());
        context.put("unsubscribeLink", getUnsubscribeLink(user));

        String template = success ?
                getTemplate(location, "step-success") :
                getTemplate(location, "step-failed");

        String html = null;/*compiler
                .compile(template)
                .execute(context);
*/
        String subjectPartial = success ? " passed" : " failed";

        Email email = Email.builder()
                .fromAddress(location.getEmailAddress())
                .subject(location.getSubjectPrefix() + " - " + testName + subjectPartial)
                .toAddress(user.getId())
                .html(html)
                .build();

        send(email);
    }

    private String getUnsubscribeLink(User user){
        return serverBasePath + "/unsubscribe?key=" + user.getUserHash();
    }

    private String getTemplate(Location loc, String name){
        String partial = emailTemplateRepository.findOne(loc.getId() + "-" + name).getTemplate();
        String master = emailTemplateRepository.findOne(loc.getId() + "-master").getTemplate();
        return master.replace("({[content]})", partial);
    }

    private void send(Email email){

        email.setApplication("APPSYSTEM-ADMIN");

        log.trace("Sending to URL: {}", mailgunAdapterUrl);
        log.trace("Email body: {}", email);

        ResponseEntity<String> response = null;

        try {
            response = emailSenderRestTemplate.postForEntity(mailgunAdapterUrl, email, String.class);

            log.trace("Response: {}", response.toString());

            if (response.getStatusCode().is2xxSuccessful()) {
                log.debug("Email sent successfully");
            } else {
                log.error("Error while sending email: {}", response.getBody());
            }

        } catch (Exception ex){
            log.error("Failed to send email: {}, {}", response == null ? "-" : response.getBody(), ex.getMessage(), ex);
        }
    }

}
