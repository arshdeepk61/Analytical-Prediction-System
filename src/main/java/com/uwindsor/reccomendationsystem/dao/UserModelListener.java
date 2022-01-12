//package com.uwindsor.reccomendationsystem.dao;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
//import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
//import org.springframework.stereotype.Component;
//
//@Component
//public class UserModelListener extends AbstractMongoEventListener {
//
//    private SequenceGeneratorService sequenceGeneratorService;
//
//    @Autowired
//    public UserModelListener(SequenceGeneratorService sequenceGeneratorService) {
//        this.sequenceGeneratorService = sequenceGeneratorService;
//    }
//
//    @Override
//    public void onBeforeConvert(BeforeConvertEvent event) {
//        if (((AppUser) event.getSource()).getId() < 1) {
//            ((AppUser) event.getSource()).setId(sequenceGeneratorService.generateSequence(AppUser.SEQUENCE_NAME));
//        }
//    }
//}