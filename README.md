# JWT Study Project

1. generateToken : AccessToken(ex.1일), RefreshToken(ex.30일) 발행
2. Access Token 을 사용하여 접근
3. if Access Token이 만료 된 경우 : refreshToken 호출
4.   accessToken은 무조건 재발급. 
5.   if RefreshToken 이 만료된 경우 : refreshToken 도 재발급.(generateToken과 동일)
