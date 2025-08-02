package com.uzayr.journalApp.repository;

import com.uzayr.journalApp.entity.ConfigJournalAppEntity;
import com.uzayr.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJournalAppRepository extends MongoRepository<ConfigJournalAppEntity, ObjectId> {

}
