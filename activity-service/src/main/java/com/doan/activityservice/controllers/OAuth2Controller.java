package com.doan.activityservice.controllers;

import com.doan.activityservice.models.StravaToken;
import com.doan.activityservice.services.StravaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URLEncoder;
import java.nio.charset.Charset;

@Controller
public class OAuth2Controller {

    @Autowired
    private StravaService stravaService;

    @GetMapping("/strava/auth")
    public RedirectView authorize(@RequestParam String employeeId) {
        String authorizationUrl = stravaService.getAuthorizationUrl(employeeId);
        return new RedirectView(authorizationUrl);
    }

    @GetMapping("/strava/callback")
    public String callback(@RequestParam String code, @RequestParam String state) {
        try {
            StravaToken token = stravaService.exchangeCodeForToken(code, state);
            // Nếu bạn muốn truyền thông tin về trạng thái xác thực đến frontend
            return "redirect:http://localhost:5500/index.html?auth=success&userId=" + token.getEmployeeId();
        } catch (Exception e) {


            // Chuyển hướng với thông báo lỗi
            return "redirect:http://localhost:5500/index.html?auth=error&message=" + URLEncoder.encode(e.getMessage());
        }
    }

}
