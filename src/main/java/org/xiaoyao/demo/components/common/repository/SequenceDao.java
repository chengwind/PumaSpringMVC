package org.xiaoyao.demo.components.common.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.xiaoyao.demo.components.common.model.Sequence;

public interface SequenceDao extends MongoRepository<Sequence, String> {
}
