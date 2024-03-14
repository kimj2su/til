// package com.example.mongodbpractice.utils;
//
// import com.mongodb.client.MongoClient;
// import com.mongodb.client.MongoClients;
// import de.bwaldvogel.mongo.MongoServer;
// import de.bwaldvogel.mongo.backend.memory.MemoryBackend;
//
// public class MongoTestServer {
//
//     private MongoServer server;
//     private MongoClient client;
//
//     public void start() {
//         // 메모리 백엔드를 사용하여 mongo 서버 인스턴스 생성
//         server = new MongoServer(new MemoryBackend());
//         // 서버 주소 할당
//         server.bind("localhost", 27017);
//         String connectionString = server.getConnectionString();
//         client = MongoClients.create(connectionString);
//     }
//
//     public void stop() {
//         if (server != null) {
//             // 서버 종료
//             client.close();
//             server.shutdownNow();
//         }
//     }
// }
//
