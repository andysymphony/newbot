package com.stampbot.repository;

import com.stampbot.entity.UserWorkflowLogEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserWorkflowRepository extends CrudRepository<UserWorkflowLogEntity, Long>{

	@Query("select count(1) = 0 from UserWorkflowLogEntity log where log.userId = :userId and log.conversationId = :conversationId and log.passed = false")
	boolean isEmpty(@Param("userId") Long userId, @Param("conversationId") String conversationId);

	@Query("select log from UserWorkflowLogEntity log where log.passed = false and log.conversationId = :conversationId")
	UserWorkflowLogEntity getUnansweredQuestion(@Param("conversationId") String conversationId);

	List<UserWorkflowLogEntity> findByConversationIdOrderById(@Param("conversationId") String conversationId);

	@Query("select log from UserWorkflowLogEntity log, WorkflowEntity workflowEntity where workflowEntity.id = log.workflowId " +
			"and log.passed = false and workflowEntity.name = :workflowName order by log.questionId")
	UserWorkflowLogEntity getUnansweredQuestionGivenWorkflow(@Param("workflowName") String workflowName);

	@Query("select log from UserWorkflowLogEntity log, " +
								"WorkflowQuestionEntity question " +
			"where log.questionId = question.id and question.id = :id ")
	UserWorkflowLogEntity findQuestionWithNextQuestionId(@Param("id") Long id);
}
