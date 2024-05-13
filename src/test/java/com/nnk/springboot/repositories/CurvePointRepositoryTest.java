package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.CurvePoint;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CurvePointRepositoryTest {

    @Autowired
    private CurvePointRepository curvePointRepository;


    @Test
    void shouldReturnSavedCurvePointWhenSaveCurvePoint() {
        CurvePoint curvePointToSave = CurvePoint.builder().curveId(20).term(30d).value(40d).build();

        CurvePoint savedCurvePoint = curvePointRepository.save(curvePointToSave);

        assertNotNull(savedCurvePoint.getId());
        assertEquals(curvePointToSave, savedCurvePoint);
    }

    @ParameterizedTest
    @CsvSource({
            ",,",
            "-1, -1.0, -1.0",
            "0, 0.0, 0.0",
            "'2147483647', '1.7976931348623157E309', '1.7976931348623157E309'"
    })
    void shouldThrowExceptionWhenCurvePointToSaveIsNotValid(Integer curveId, Double term, Double value) {
        CurvePoint curvePointToSave = CurvePoint.builder().curveId(curveId).term(term).value(value).build();

        assertThrows(ConstraintViolationException.class, () -> curvePointRepository.save(curvePointToSave));
    }

    @Test
    void shouldReturnUpdatedCurvePointWhenUpdateCurvePoint() {
        CurvePoint curvePointToUpdate = CurvePoint.builder().curveId(20).term(30d).value(40d).build();

        CurvePoint updatedCurvePoint = curvePointRepository.save(curvePointToUpdate);

        assertNotNull(updatedCurvePoint.getId());
        assertEquals(curvePointToUpdate, updatedCurvePoint);
    }

    @Test
    void shouldCreateNewCurvePointWhenCurvePointToUpdateDoesNotExist() {
        CurvePoint curvePointToUpdate = CurvePoint.builder().id(9999).curveId(20).term(30d).value(40d).build();

        CurvePoint savedCurvePoint = curvePointRepository.save(curvePointToUpdate);

        assertNotEquals(9999, savedCurvePoint.getId());
    }

    @Test
    void shouldReturnFoundCurvePointsWhenFindCurvePoints() {
        List<CurvePoint> foundCurvePoints = curvePointRepository.findAll();

        assertFalse(foundCurvePoints.isEmpty());
    }

    @Test
    void shouldReturnFoundCurvePointWhenFindCurvePoint() {
        int curvePointToFindId = 5;
        Optional<CurvePoint> foundCurvePoint = curvePointRepository.findById(curvePointToFindId);

        assertTrue(foundCurvePoint.isPresent());
        assertEquals(foundCurvePoint.get().getId(), 5);
    }

    @Test
    void shouldNotFindCurvePointWhenCurvePointDoesNotExist() {
        int curvePointToFindId = 99;
        Optional<CurvePoint> optionalFoundCurvePoint = curvePointRepository.findById(curvePointToFindId);

        assertTrue(optionalFoundCurvePoint.isEmpty());
    }

    @Test
    void shouldNotFindCurvePointWhenDeleteCurvePoint() {
        int curvePointToDeleteId = 5;
        curvePointRepository.deleteById(curvePointToDeleteId);
        Optional<CurvePoint> optionalCurvePoint = curvePointRepository.findById(curvePointToDeleteId);

        assertFalse(optionalCurvePoint.isPresent());
    }
}
