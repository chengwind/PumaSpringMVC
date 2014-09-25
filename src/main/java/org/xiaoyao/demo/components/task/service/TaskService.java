package org.xiaoyao.demo.components.task.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.xiaoyao.demo.components.common.service.SequenceService;
import org.xiaoyao.demo.components.task.model.Task;
import org.xiaoyao.demo.components.task.repository.TaskDao;

// Spring Bean的标识.
@Component
public class TaskService extends SequenceService {

	private TaskDao taskDao;

	public Task getTask(String id) {
		return taskDao.findOne(id);
	}

	public void saveTask(Task entity) {
//		if (entity.getId() == null || "".equals(entity.getId())) {
//			Long id = getNextSequenceId(entity.getSquenceKey(), entity.getSquenceStart());
//			entity.setId(id.toString());
//		}
		taskDao.save(entity);
	}

	public void deleteTask(String id) {
		taskDao.delete(id);
	}

	public List<Task> getAllTask() {
		return (List<Task>) taskDao.findAll();
	}

	public Page<Task> getUserTask(String userId, Map<String, Object> searchParams, int pageNumber, int pageSize, String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		return taskDao.findAll(pageRequest);
	}

	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if ("title".equals(sortType)) {
			sort = new Sort(Direction.ASC, "title");
		}

		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	@Autowired
	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}
}