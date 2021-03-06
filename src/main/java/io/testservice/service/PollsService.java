package io.testservice.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.testservice.model.Choices;
import io.testservice.model.Questions;
import io.testservice.model.ReqQuestion;
import io.testservice.repository.PollsRepository;

@Service
public class PollsService {

	@Autowired
	PollsRepository pollsRepo;

	public PollsService() {

	}

	// gets All questions from database
	public List<Questions> getAllQuestions() {
		List<Questions> questList = new ArrayList<Questions>();
		pollsRepo.findAll().forEach(elem -> {
			questList.add(elem);
		});
		;
		return questList;
	}

	// gets questions from database for a given Id
	public Questions getQuestionAtId(int id) {
		Optional<Questions> quest = pollsRepo.findById(id);
		if (quest.isPresent())
			return (Questions) quest.get();
		else
			return null;
	}

	// add question to database
	@Transactional
	public int addQuestion(ReqQuestion req) {

		Questions questions = new Questions();
		questions.setQuestion(req.getQuestion());
		Instant timestamp = Instant.now();
		questions.setPublished_at(timestamp.toString());
		List<Choices> choicesList = new ArrayList<>();
		req.getChoices().forEach(s -> {
			Choices choice = new Choices(s, "0");
			choicesList.add(choice);
		});
		questions.setChoicesList(choicesList);
		Questions q = pollsRepo.save(questions);
		return q.getId();

	}

}
