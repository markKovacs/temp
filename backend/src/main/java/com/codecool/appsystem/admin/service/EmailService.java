package com.codecool.appsystem.admin.service;

import com.codecool.appsystem.admin.model.ApplicationScreeningInfo;
import com.codecool.appsystem.admin.model.Email;
import com.codecool.appsystem.admin.model.Location;
import com.codecool.appsystem.admin.model.User;
import com.codecool.appsystem.admin.repository.EmailTemplateRepository;
import com.codecool.appsystem.admin.repository.LocationRepository;
import com.codecool.appsystem.admin.repository.UserRepository;
import com.samskivert.mustache.Mustache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
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

    @Value("${mailgun.adapter.send.url}")
    private String mailgunAdapterUrl;

    @Value("${server.redirecturl.basepath}")
    private String serverBasePath;

    @Value("${server.frontend.basepath}")
    private String frontendUrl;

    @Value("${server.appsystem.basepath}")
    private String appsystemUrl;

    @Value("${application.expiry.days:7}")
    private Integer days;

    public List<Email> findByAddressee(Integer id){
        String email = userRepository.findOne(id).getEmail();
        return emailSenderRestTemplate.getForObject(mailgunAdapterUrl + "/info?email=" + email, List.class);
    }

    public void sendMotivationSuccess(User user) {

        log.info("Sending motivation success email to: {}", user.getId());
        sendMotivationResult(user, "motivation-success");

    }

    public void sendMotivationFailed(User user) {

        log.info("Sending motivation failed email to: {}", user.getId());
        sendMotivationResult(user, "motivation-failed");

    }

    private void sendMotivationResult(User user, String templateName) {
        Location location = locationRepository.findOne(user.getLocationId());

        Map<String, String> context = new HashMap<>();
        context.put("firstName", user.getGivenName());
        context.put("replyToAddress", location.getEmailAddress());
        context.put("unsubscribeLink", getUnsubscribeLink(user));
        context.put("appUrl", appsystemUrl);

        send(context, location, templateName, " - Motivation video processed", user);

    }

    public void sendScreeningTimesAssigned(User user, ApplicationScreeningInfo screeningInfo) {

        Location location = locationRepository.findOne(user.getLocationId());




        // times will be 2017.01.01 11:00
        //new SimpleDateFormat("yyyy.MM.dd. H:mm").format(screeningInfo.getScreeningGroupTime());
        String groupTime = screeningInfo.getScreeningGroupTime()
                .toInstant()
                .atZone(ZoneId.of("Europe/Budapest"))
                .format(DateTimeFormatter.ofPattern("yyyy.MM.dd. H:mm"));


        String personalTime = screeningInfo.getScreeningPersonalTime()
                .toInstant()
                .atZone(ZoneId.of("Europe/Budapest"))
                .format(DateTimeFormatter.ofPattern("yyyy.MM.dd. H:mm"));



        Map<String, String> context = new HashMap<>();
        context.put("firstName", user.getGivenName());
        context.put("replyToAddress", location.getEmailAddress());
        context.put("unsubscribeLink", getUnsubscribeLink(user));
        // this has to take the user to the appsystem's url
        context.put("appUrl", appsystemUrl);
        context.put("groupScreeningTime", groupTime);
        context.put("personalScreeningTime", personalTime);
        context.put("address", location.getAddress());

        send(context, location, "screening-times-assigned", " - Screening times assigned", user);


    }

    public void sendResultY(User user) {

        Location location = locationRepository.findOne(user.getLocationId());

        Map<String, String> context = new HashMap<>();
        context.put("firstName", user.getGivenName());
        context.put("replyToAddress", location.getEmailAddress());
        context.put("unsubscribeLink", getUnsubscribeLink(user));
        context.put("appUrl", appsystemUrl);
        context.put("location", location.getName());
        context.put("nextCourseStart", location.getNextCourseStart());

        send(context, location, "result-yes", " - Screening result", user);


    }

    public void sendResultN(User user) {

        Location location = locationRepository.findOne(user.getLocationId());

        Map<String, String> context = new HashMap<>();
        context.put("firstName", user.getGivenName());
        context.put("replyToAddress", location.getEmailAddress());
        context.put("unsubscribeLink", getUnsubscribeLink(user));
        context.put("appUrl", appsystemUrl);

        send(context, location, "result-no", " - Screening result", user);


    }

    private String getUnsubscribeLink(User user) {
        return serverBasePath + "/unsubscribe?key=" + user.getUserHash();
    }

    private String getTemplate(Location loc, String name) {
        String partial = emailTemplateRepository.findOne(loc.getId() + "-" + name).getTemplate();
        String master = emailTemplateRepository.findOne(loc.getId() + "-master").getTemplate();
        return master.replace("({[content]})", partial);
    }

    private void send(Map<String, String> context, Location location, String templateName,
            String subjectPostfix, User user) {


        ResponseEntity<String> response = null;

        try {
            String html = compiler
                    .compile(getTemplate(location, templateName))
                    .execute(context);

            Email email = Email.builder()
                    .fromAddress(location.getEmailAddress())
                    .subject(location.getSubjectPrefix() + subjectPostfix)
                    .toAddress(user.getEmail())
                    .html(html)
                    .build();


            email.setApplication("APPSYSTEM-ADMIN");

            log.trace("Sending to URL: {}", mailgunAdapterUrl);
            log.trace("Email body: {}", email);

            response = emailSenderRestTemplate.postForEntity(mailgunAdapterUrl + "/send", email, String.class);

            log.trace("Response: {}", response.toString());

            if (response.getStatusCode().is2xxSuccessful()) {
                log.debug("Email sent successfully");
            } else {
                log.error("Error while sending email: {}", response.getBody());
            }

        } catch (Exception ex) {
            log.error("Failed to send email: {}, {}", response == null ? "-" : response.getBody(), ex.getMessage());
        }
    }

}
