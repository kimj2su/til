// package com.example.mongodbpractice.user.domain;
//
// import com.example.mongodbpractice.sequence.SequenceGeneratorService;
// import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
// import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
// import org.springframework.stereotype.Component;
//
// @Component
// public class UserListener extends AbstractMongoEventListener<User> {
//
//     private final SequenceGeneratorService generatorService;
//
//     public UserListener(SequenceGeneratorService generatorService) {
//         this.generatorService = generatorService;
//     }
//
//     @Override
//     public void onBeforeConvert(BeforeConvertEvent<User> event) {
//         event.getSource().setId(generatorService.generateSequence(User.SEQUENCE_NAME));
//     }
// }
