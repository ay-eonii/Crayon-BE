package com.domain.recruitment.application.usecase;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.domain.ApplicationTest;
import com.application.domain.entity.Application;
import com.application.domain.entity.enums.Rating;
import com.application.domain.repository.ApplicationRepository;
import com.application.domain.repository.EvaluationMemoRepository;
import com.application.domain.repository.EvaluationRepository;
import com.club.domain.entity.Club;
import com.club.domain.repository.ClubMangerRepository;
import com.club.domain.repository.ClubRepository;
import com.domain.fixture.TestFixture;
import com.recruitment.application.usecase.RecruitmentManageUseCase;
import com.recruitment.domain.entity.Process;
import com.recruitment.domain.entity.Recruitment;
import com.recruitment.domain.repository.ProcessRepository;
import com.recruitment.domain.repository.RecruitmentRepository;
import com.user.domain.entity.User;
import com.user.domain.repository.UserRepository;

class RecruitmentManageUseCaseTest extends ApplicationTest {

	@Autowired
	UserRepository userRepository;

	@Autowired
	ClubMangerRepository clubMangerRepository;

	@Autowired
	ClubRepository clubRepository;

	@Autowired
	RecruitmentRepository recruitmentRepository;

	@Autowired
	ProcessRepository processRepository;

	@Autowired
	ApplicationRepository applicationRepository;

	@Autowired
	RecruitmentManageUseCase recruitmentManageUseCase;

	@Autowired
	EvaluationRepository evaluationRepository;

	@Autowired
	EvaluationMemoRepository evaluationMemoRepository;

	@DisplayName("동아리 관리자는 모집을 삭제할 수 있다.")
	@Test
	void deleteRecruitment() {
		// given
		User manager = userRepository.save(TestFixture.user());
		User applicant = userRepository.save(TestFixture.user());
		User applicant2 = userRepository.save(TestFixture.user());
		Club club = clubRepository.save(TestFixture.club());
		clubMangerRepository.save(TestFixture.clubManager(club, manager));

		Process process = processRepository.save(TestFixture.process(1));
		Recruitment recruitment = recruitmentRepository.save(TestFixture.recruitment(club, process));
		process.addRecruitment(recruitment);
		processRepository.save(process);

		Application application = applicationRepository.save(
			TestFixture.application(applicant, process, recruitment.getId()));
		Application application2 = applicationRepository.save(
			TestFixture.application(applicant2, process, recruitment.getId()));

		evaluationRepository.save(TestFixture.evaluation(application, applicant, Rating.HIGH));
		evaluationRepository.save(TestFixture.evaluation(application2, applicant, Rating.HIGH));

		evaluationMemoRepository.save(TestFixture.evaluationMemo(application, manager, process.getId()));
		evaluationMemoRepository.save(TestFixture.evaluationMemo(application2, manager, process.getId()));

		// when
		recruitmentManageUseCase.cancel(recruitment.getId().toString(), manager);

		// then
		assertThat(recruitmentRepository.findById(recruitment.getId())).isEmpty();
		List<Application> applications = applicationRepository.findAll();
		assertThat(evaluationRepository.findAll()).isEmpty();
		assertThat(evaluationMemoRepository.findAll()).isEmpty();
		assertThat(applications).isEmpty();
	}
}

