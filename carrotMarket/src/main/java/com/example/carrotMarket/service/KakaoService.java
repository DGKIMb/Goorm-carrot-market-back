package com.example.carrotMarket.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoService {

    /**
     * 카카오 인증 서버에 code를 보내고 token을 발급받는 메서드
     * @param code
     * @return token
     * @throws IOException
     */
    public String getToken(String code) throws IOException {
        //토큰을 받아올 카카오 인증 서버. 우리의 carrot서버가 클리아언트로, 카카오 인증 서버가 서버로 동작한다고 보면 됩니다.
        String host = "https://kauth.kakao.com/oauth/token";
        //카카오 인증 서버와 통신하기 위한 설정
        URL url = new URL(host);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        String token = "";

        try {
            // OutputStream으로 POST 데이터를 넘겨주겠다는 옵션
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);

            //x-www-form-urlencoded 타입으로 Body에 담아 카카오 인증 서버에 Post로 요청하기 위한 버퍼 스트림 생성
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id="); // id에는 kakao에서 받은 rest api key를 넣어주면 됩니다.

            // 02.26. 프론트와 연동하는데 여기 3000으로 바꿔달라고 하셔서 바꿔놓았습니다 -광휘
            sb.append("&redirect_uri=http://localhost:3000/login/kakao");
            sb.append("&code=" + code);
            sb.append("&client_secret="); // client_secret 에는 카카오에서 받은 client secret 키를 넣어주면 됩니다.

            bw.write(sb.toString());

            //write 되어 버퍼에 있던 데이터를 flush를 통해 출력 스트림으로 출력하고, 카카오 인증 서버에 요청 전송
            bw.flush();

            //========카카오 인증 서버에 요청 후 응답 받음========//

            //카카오 인증 서버에서 응답으로 받은 response code 값
            int responseCode = urlConnection.getResponseCode();
            log.debug("responseCode = {}", responseCode);

            //카카오 인증 서버에서 받은 응답을 받기 위한 버퍼 스트림 생성(참고-요청과는 달리 JSON 데이터를 보내줌)
            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line = "";
            String result = "";
            //다양한 형식(한 줄 이상의 JSON 데이터)를 받기 위한 작업
            while ((line = br.readLine()) != null) {
                result += line;
            }

            //JSON parsing
            JSONParser parser = new JSONParser();
            JSONObject elem = (JSONObject) parser.parse(result);

            //access 토큰값 -> 카카오 api 서버에 사용자 정보를 받아오기 위해서 사용될 토큰
            String access_token = elem.get("access_token").toString();
            //refresh 토큰은 현재 서비스 구조상 카카오 api 서버에서 사용자 정보만 가져오면 되므로 필요하지 않음
//            String refresh_token = elem.get("refresh_token").toString();

            token = access_token;

            //버퍼스트림 닫기
            br.close();
            bw.close();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }


        return token;
    }

    /**
     * 카카오 api 서버에 token을 보내고 사용자 정보를 발급받는 메서드
     * @param access_token
     * @return 카카오 사용자 정보((kakao)id, nickname, email, profileImage)
     * @throws IOException
     */
    public Map<String, Object> getUserInfo(String access_token) {

        //사용자 정보를 받아올 카카오 api 서버. 우리의 carrot 서버가 클리아언트로, 카카오 api 서버가 서버로 동작한다고 보면 됩니다.
        String host = "https://kapi.kakao.com/v2/user/me";
        //사용자 정보를 받을 Map 객체 생성
        Map<String, Object> result = new HashMap<>();
        try {
            URL url = new URL(host);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            //Request Header에 토큰 인증 관련 값을 설정하고, GET을 통해 카카오 api 서버에 요청한다는 옵션
            urlConnection.setRequestProperty("Authorization", "Bearer " + access_token);
            urlConnection.setRequestMethod("GET");

            //========카카오 api 서버에 요청 후 응답 받음========//

            int responseCode = urlConnection.getResponseCode();
            log.debug("responseCode = {}", responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), StandardCharsets.UTF_8));
            String line = "";
            String res = "";
            //다양한 형식(한 줄 이상의 JSON 데이터)를 받기 위한 작업
            while ((line=br.readLine()) != null)
            {
                res+=line;
            }

            //JSON parsing
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(res);

            String id = obj.get("id").toString();

            JSONObject kakao_account = (JSONObject) obj.get("kakao_account");
            String email = kakao_account.get("email").toString();

            result.put("id", id);
            result.put("email", email);

            br.close();


        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return result;
    }


}
