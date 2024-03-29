package com.zwc.community.controller;

import com.zwc.community.controller.dto.AccessTokenDTO;
import com.zwc.community.controller.dto.GitHubUser;
import com.zwc.community.controller.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class OAuthController {

    @Autowired
    public GithubProvider githubProvider;

    @Value("${gitgub,client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam (name = "code" ) String code,
                           @RequestParam (name = "state") String state,
                           HttpServletRequest request){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setClient_id(clientId);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GitHubUser user = githubProvider.getUser(accessToken);
//        System.out.println(user.getName());
        if (user != null){
            request.getSession().setAttribute("user",user);
            return "redirect:/";
        }else {
            return "redirect:/";
        }
    }
}
