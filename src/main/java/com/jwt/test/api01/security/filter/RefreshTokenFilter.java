package com.jwt.test.api01.security.filter;

import com.google.gson.Gson;
import com.jwt.test.api01.security.exception.RefreshTokenException;
import com.jwt.test.api01.util.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Ref;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
public class RefreshTokenFilter extends OncePerRequestFilter {
    private final String refreshPath;
    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        if(!path.equals(refreshPath)){
            log.info("skip refresh token filter");
            filterChain.doFilter(request,response);
            return;
        }
        log.info("Refresh Token Filter ... run ......1");

        //전송된 JSON에서 accessToken과 refreshToken을 얻어온다.
        Map<String,String> tokens = parseRequestJSON(request);

        String accessToken = tokens.get("accessToken");
        String refreshToken = tokens.get("refreshToken");
        log.info("accessToken: "+accessToken);
        log.info("refreshToken: "+ refreshToken);
        try{
            checkAccessToken(accessToken);
        }catch (RefreshTokenException refreshTokenException){
            refreshTokenException.sendResponseError(response);
        }
        Map<String,Object> refreshClaims = null;

        try{
            refreshClaims = checkRefreshToken(refreshToken);
            log.info(refreshClaims);

            //Refresh Token의 유효 시간이 얼마 남지 않은 경우
            Integer exp = (Integer) refreshClaims.get("exp");

            Date expTime = new Date(Instant.ofEpochMilli(exp).toEpochMilli() * 1000);
            Date current = new Date(System.currentTimeMillis());

            //만료 시간과 현재 시간의 간격 계산
            //만일 3일 미만의 경우에는 Refresh Token도 다시 생성
            Long gapTime = (expTime.getTime() - current.getTime());
            log.info("-----------------------------------");
            log.info("current: "+current);
            log.info("expTime: "+ expTime);
            log.info("gap: "+ gapTime);

            String mid = (String)refreshClaims.get("mid");

            //이 상태까지 오면 무조건 AccessToken은 새로 발행
            String accessTokenValue = jwtUtil.generateToken(Map.of("mid",mid),1);
            String refreshTokenValue = tokens.get("refreshToken");

            //Refresh Token이 3일도 안 남았다면..
            if(gapTime < (1000 * 60 * 60 * 24 * 3 )){
                log.info("new Refresh Token Required..");
                refreshTokenValue = jwtUtil.generateToken(Map.of("mid",mid),30);
            }
            log.info("Refresh Token result....");
            log.info("accessToken: "+ accessTokenValue);
            log.info("refreshToken: "+refreshTokenValue);

            sendToken(accessTokenValue,refreshTokenValue,response);
            
        }catch (RefreshTokenException refreshTokenException){
            refreshTokenException.sendResponseError(response);
            return; //더 이상 실행할 코드가 없음.
        }
    }

    private Map<String,String> parseRequestJSON(HttpServletRequest request){
        //JSON 데이터를 분석해서 mid, mpw 전달 값을 Map으로 처리
        try(Reader reader = new InputStreamReader(request.getInputStream())){
            Gson gson = new Gson();
            return gson.fromJson(reader,Map.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkAccessToken(String accessToken) throws RefreshTokenException{
        try {
            jwtUtil.validateToken(accessToken);
        }catch (ExpiredJwtException expiredJwtException){
            log.info("Access Token has expired");
        }catch (Exception exception){
            throw new RefreshTokenException(RefreshTokenException.ErrorCase.NO_ACCESS);
        }

    }

    private Map<String,Object> checkRefreshToken(String refreshToken) throws RefreshTokenException{
        try{
            Map<String,Object> values = jwtUtil.validateToken(refreshToken);
            return values;
        }catch (ExpiredJwtException expiredJwtException){
            throw new RefreshTokenException(RefreshTokenException.ErrorCase.OLD_REFRESH);
        }catch (MalformedJwtException malformedJwtException){
            log.error("MalformedJwtException--------");
            throw new RefreshTokenException(RefreshTokenException.ErrorCase.NO_REFRESH);
        }catch (Exception exception){
            new RefreshTokenException(RefreshTokenException.ErrorCase.NO_REFRESH);
        }
        return null;
    }

    private void sendToken(String accessTokenValue, String refreshTokenValue, HttpServletResponse response){
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Gson gson = new Gson();
        String jsonStr = gson.toJson(Map.of("accessToken",accessTokenValue,"refreshToken",refreshTokenValue));
        try {
            response.getWriter().println(jsonStr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}