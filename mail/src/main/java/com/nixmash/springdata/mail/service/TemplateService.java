package com.nixmash.springdata.mail.service;

import com.nixmash.springdata.jpa.model.Post;

public interface TemplateService {

    String getRobotsTxt();

    String createPostHtml(Post post);
}
