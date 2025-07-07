package com.application.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.application.domain.entity.ProcessResult;
import com.application.domain.repository.dto.ProcessResultWithApplication;

public interface ProcessResultRepository extends JpaRepository<ProcessResult, Long> {

	@Query("""
		SELECT pr
		FROM ProcessResult pr
		WHERE pr.processId = :processId AND pr.applicationId IN :applicationIds
		ORDER BY
		    CASE WHEN pr.status = 'PENDING' THEN 0 ELSE 1 END ASC,
		    pr.id DESC
		""")
	List<ProcessResult> findAllByProcessId(@Param("processId") long processId,
		@Param("applicationIds") List<UUID> applicationIds);

	Optional<ProcessResult> findByApplicationIdAndProcessId(UUID applicationId, Long processId);

	@Query("""
		SELECT pr
		FROM ProcessResult pr
		WHERE pr.processId = :processId AND (pr.status = 'DOCUMENT_PASS' OR pr.status = 'FINAL_PASS')
		""")
	List<ProcessResult> findAllPassByProcessId(long processId);

	List<ProcessResult> findAllByApplicationId(UUID applicationId);

	@Query("""
		SELECT new com.application.domain.repository.dto.ProcessResultWithApplication(
		    p.status,
		    p.applicationId
		 )
		 FROM ProcessResult p WHERE p.applicationId IN :ids
		""")
	List<ProcessResultWithApplication> findAll(List<UUID> ids);

	@Query("SELECT pr.applicationId FROM ProcessResult pr WHERE pr.processId = :processId AND pr.status = 'PENDING'")
	List<UUID> findPendingApplicationId(long processId);
}
