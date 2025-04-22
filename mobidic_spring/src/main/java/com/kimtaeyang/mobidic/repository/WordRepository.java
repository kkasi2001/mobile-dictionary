package com.kimtaeyang.mobidic.repository;

import com.kimtaeyang.mobidic.entity.Vocab;
import com.kimtaeyang.mobidic.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WordRepository extends JpaRepository<Word, UUID> {
    List<Word> findByVocab(Vocab vocab);

    int countByExpressionAndVocabAndIdNot(String expression, Vocab vocab, UUID id);

    int countByExpressionAndVocab(String expression, Vocab vocab);
}
