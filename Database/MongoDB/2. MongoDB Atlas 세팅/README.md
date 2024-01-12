# MongoDB Atlas 세팅
## 몽고 디비 계정 생성
1. 몽고디비 접속 가입
2. 뉴 프로젝트 생성
3. 아이디 비밀번호 생성
4. 샘플 데이터 추가

## Shared Clusters 구축
https://github.com/ylake/mongodb-cluster-docker-compose.git
```java
docker compose up -d
10082  docker-compose exec configsvr01 sh -c "mongo < /scripts/init-configserver.js"
10085  docker-compose exec shard01-a sh -c "mongo < /scripts/init-shard01.js"
10086  docker-compose exec shard02-a sh -c "mongo < /scripts/init-shard02.js"
10087  docker-compose exec shard03-a sh -c "mongo < /scripts/init-shard03.js"
10088  docker-compose exec router01 sh -c "mongo < /scripts/init-router.js"
10089  docker-compose exec router01 mongo --port 27017
```

```java
sh.status()
show dbs
use test
db.abs.inserOne({a: 1, b: 2})
sh.status() // partitioned: false 이다.
sh.enableSharding("test") // 샤딩 활성화 하기
show collections
db,abc,createIndex({a: 1}) // 샤딩 키로 사용할 필드에 인덱스 생성
sh.shardCollection("test.abc", {a: 1}) // 샤딩 키로 사용할 필드 지정
```

## MongoDB에 연결하는 방식
1. IP
```java
mongosh "mongodb://localhost:27017,localhost:27018,localhost:27019/?replicaSet=rs0"
```
2. DNS
```java
mongosh "mongodb+srv://cluster0.1inggwg.mongodb.net/" --apiVersion 1 --username {usernmae}
```

## MongoDB Client
1. MongoDB shell
```java
brew install mongosh
mongosh 접속정보
```
2. MongoDB Compass
https://downloads.mongodb.com/compass/mongosh-2.1.1-darwin-arm64.zip
다운로드 설치 파일

3. java driver
```java
<dependency>
    <groupId>org.mongodb</groupId>
    <artifactId>mongodb-driver-sync</artifactId>
    <version>4.3.1</version>
</dependency>

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
public class MongoClientConnectionExample {
    public static void main(String[] args) {
        String connectionString = "mongodb+srv://jisu:<password>@cluster0.1inggwg.mongodb.net/?retryWrites=true&w=majority";
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();
        // Create a new client and connect to the server
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
                // Send a ping to confirm a successful connection
                MongoDatabase database = mongoClient.getDatabase("admin");
                database.runCommand(new Document("ping", 1));
                System.out.println("Pinged your deployment. You successfully connected to MongoDB!");
            } catch (MongoException e) {
                e.printStackTrace();
            }
        }
    }
}
```

## 인증과 권한
MongoDB Roles의 작업들을 줄 수 있다.
https://www.mongodb.com/docs/manual/reference/built-in-roles/
