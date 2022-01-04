package com.example.kakologin.Controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.SocketUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.xml.ws.spi.http.HttpHandler;

@RestController
public class KakaoLoginController {
    @GetMapping(value="/auth/kakao/callback")
    public String login(String code) {
        System.out.println("인가 Code 값은 "+code);
        //post방식으로 카카오 인증 서버에서 토큰을 가져오자
        //http통신을 수월하게 해주시 위한 RestTemplate
        RestTemplate rt =new RestTemplate();

        //헤더에 값을 담기
        HttpHeaders headers=new HttpHeaders();
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        //body에 넣자 key value형태로
        MultiValueMap<String,String> params=new LinkedMultiValueMap<>();
        params.add("grant_type","authorization_code");
        params.add("client_id","3ea182ba51102e6bbde7dfd5fb019880");
        params.add("redirect_uri","http://localhost:8080/auth/kakao/callback");
        params.add("code",code);
        params.add("client_secret","thtX0ki5BCktmJuqGsU0GCd5Ayv5KabB");
        //헤더와 바디 하나로 담기
        HttpEntity<MultiValueMap<String,String>> kakaoTokenRequest=new HttpEntity<>(params,headers);

        //post로 카카오로 보내서 토큰 값을 response에 받아온다.
        ResponseEntity<String> response=rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );
        return "카카오 엑세스 토큰의 값은 : "+response;
    }
}
