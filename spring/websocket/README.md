# JWT

### HEADER

알고리즘과 토큰 타입을 지정한다.

```json
{
  "alg": "HS256",
  "typ": "JWT"
}
```

### PAYLOAD

토큰에 담을 정보를 넣는다.

```json
{
  "sub": "1234567890",
  "name": "John Doe",
  "admin": true,
  "iat": 1516239022,
  "http://localhost:3000": "http://localhost:3000",
  // 공개 클레임
  "user_name": "kim"
  // 비공개 클레임
}
```

### SIGNATURE

```json
HMACSHA256(
base64UrlEncode(header) + "." + // 헤더
base64UrlEncode(payload), // 페이로드
secret // 비밀키
)
```