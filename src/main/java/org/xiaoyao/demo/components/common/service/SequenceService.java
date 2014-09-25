package org.xiaoyao.demo.components.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xiaoyao.demo.components.common.model.Sequence;
import org.xiaoyao.demo.components.common.repository.SequenceDao;

@Component
public class SequenceService {

	protected SequenceDao sequenceDao;

	public Sequence getSequence(String id) {
		return sequenceDao.findOne(id);
	}

	public void saveSequence(Sequence entity) {
		sequenceDao.save(entity);
	}

	public void deleteSequence(String id) {
		sequenceDao.delete(id);
	}

	public List<Sequence> getAllSequence() {
		return (List<Sequence>) sequenceDao.findAll();
	}

	@Autowired
	public void setSequenceDao(SequenceDao sequenceDao) {
		this.sequenceDao = sequenceDao;
	}

	public Long getNextSequenceId(String id, Long start) {

		Sequence sequence = this.getSequence(id);
		if (sequence == null) {
			sequence = new Sequence();
			sequence.setId(id);
			sequence.setValue(start);
		} else {
			sequence.setValue(sequence.getValue() + 1);
		}
		this.saveSequence(sequence);
		return sequence.getValue();

	}
}