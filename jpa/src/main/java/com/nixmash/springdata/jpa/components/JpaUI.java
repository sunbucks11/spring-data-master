package com.nixmash.springdata.jpa.components;

import com.nixmash.springdata.jpa.common.ApplicationSettings;
import com.nixmash.springdata.jpa.common.ISiteOption;
import com.nixmash.springdata.jpa.common.SiteOptions;
import com.nixmash.springdata.jpa.dto.PostDTO;
import com.nixmash.springdata.jpa.dto.SiteOptionDTO;
import com.nixmash.springdata.jpa.enums.PostDisplayType;
import com.nixmash.springdata.jpa.enums.PostType;
import com.nixmash.springdata.jpa.exceptions.ContactNotFoundException;
import com.nixmash.springdata.jpa.exceptions.SiteOptionNotFoundException;
import com.nixmash.springdata.jpa.model.UserConnection;
import com.nixmash.springdata.jpa.service.ContactService;
import com.nixmash.springdata.jpa.service.PostService;
import com.nixmash.springdata.jpa.service.SiteService;
import com.nixmash.springdata.jpa.service.UserService;
import com.nixmash.springdata.jpa.utils.ContactUtils;
import com.nixmash.springdata.jpa.utils.PostUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class JpaUI {

    // region  Beans

    @Autowired
    private PostService postService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    @Autowired
    private SiteService siteService;

    @Autowired
    private ApplicationSettings applicationSettings;

    @Autowired
    DefaultListableBeanFactory beanfactory;

    @Autowired
    private SiteOptions siteOptions;

    // endregion

    private Boolean reset = true;

    public void init() {
        displayRandomUserIdString();
    }

    private void displayRandomUserIdString() {
        System.out.println(RandomStringUtils.randomAlphanumeric(16));
    }

    private void addPostDemo() {
        String title = "Best way to create SEO friendly URI string";
        PostDTO postDTO = PostDTO.getBuilder(
                1L,
                title,
                PostUtils.createSlug(title),
                "http://nixmash.com/java/variations-on-json-key-value-pairs-in-spring-mvc/",
                "This is the post content",
                PostType.LINK,
                PostDisplayType.LINK_FEATURE
        ).build();
        postService.add(postDTO);
    }

    // region Unused demos

    private void siteOptionsDemo() {
        System.out.println("Initialized SiteOptions Bean Property: " +
                siteOptions.getGoogleAnalyticsTrackingId());

        String siteName = reset ? "My Site" : "My Updated Site Name";
        String integerProperty = reset ? "1" : "8";

        try {
            siteService.update(new SiteOptionDTO(ISiteOption.SITE_NAME, siteName));
            siteService.update(new SiteOptionDTO(ISiteOption.INTEGER_PROPERTY, integerProperty));
        } catch (SiteOptionNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("New SiteOptions values: " + siteOptions.getSiteName() + " -- " + siteOptions.getIntegerProperty());
        System.out.println("GoogleAnalyticsId: " + siteOptions.getGoogleAnalyticsTrackingId());
    }

    public void entityDemo() {

        UserConnection userConnection = userService.getUserConnectionByUserId("daver");
        ContactUtils.listUserConnection("My User Connection", userConnection);
        ContactUtils.listUsersWithDetail(userService.getUsersByAuthorityId(1L));
        ContactUtils.listUser("USER BY EMAIL",
                userService.getByEmail("user@aol.com").get());
        try {
            ContactUtils.listContact("CONTACT BY EMAIL",
                    contactService.findContactById(1L));
        } catch (ContactNotFoundException e) {
            e.printStackTrace();
        }

        ContactUtils.listContacts("ENTITIES FIND ALL", contactService.findAll());
        ContactUtils.listContacts("ENTITIES FIND BY FIRST NAME",
                contactService.findByFirstName("Barry"));
        ContactUtils.listContacts("ENTITIES FIND BY FIRST AND LAST NAME",
                contactService.findByFirstNameAndLastName("Tad", "Grant"));

        ContactUtils.listContact("SINGLE CONTACT: ",
                contactService.getContactByEmail("Nam.nulla@pedenonummyut.edu"));
        ContactUtils.listContactsWithDetail(contactService.getContactsWithDetail());

        ContactUtils.listContactWithDetail(contactService.getContactByIdWithDetail(2L));

        ContactUtils.listContacts("FIND BY FIRST NAME",
                contactService.findByFirstName("Summer"));

        ContactUtils.listContactWithDetail(contactService.getContactByIdWithDetail(1L));
        ContactUtils.contactToContactDTO(contactService.getContactByIdWithDetail(2L));

        try {
            contactService.update(ContactUtils.contactToContactDTO(contactService.getContactByIdWithDetail(2L)));
        } catch (ContactNotFoundException e) {
            e.printStackTrace();
        }
    }

    // endregion

}
