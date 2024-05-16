INSERT INTO Lecture (LEC_CREDIT, LEC_DAY, LEC_GRADE, LEC_SEMESTER, LEC_TIME_END, LEC_TIME_START, LEC_YEAR, LEC_NAME, LEC_PROFESSOR, LECTURE_ROOM) VALUES
    (3, '월요일', 1, 1, '12:00:00', '09:00:00', 2024, 'lecName1', 'professorName1','효행관 401호');
INSERT INTO Lecture (LEC_CREDIT, LEC_DAY, LEC_GRADE, LEC_SEMESTER, LEC_TIME_END, LEC_TIME_START, LEC_YEAR, LEC_NAME, LEC_PROFESSOR, LECTURE_ROOM) VALUES
    (2, '월요일', 1, 1, '15:00:00', '13:00:00', 2024, 'lecName2', 'professorName2','효행관 402호');
INSERT INTO Lecture (LEC_CREDIT, LEC_DAY, LEC_GRADE, LEC_SEMESTER, LEC_TIME_END, LEC_TIME_START, LEC_YEAR, LEC_NAME, LEC_PROFESSOR, LECTURE_ROOM) VALUES
    (2, '화요일', 1, 1, '12:00:00', '10:00:00', 2024, 'lecName3', 'professorName3','효행관 403호');
INSERT INTO Lecture (LEC_CREDIT, LEC_DAY, LEC_GRADE, LEC_SEMESTER, LEC_TIME_END, LEC_TIME_START, LEC_YEAR, LEC_NAME, LEC_PROFESSOR, LECTURE_ROOM) VALUES
    (3, '화요일', 1, 1, '16:00:00', '13:00:00', 2024, 'lecName4', 'professorName4','효행관 404호');
INSERT INTO Lecture (LEC_CREDIT, LEC_DAY, LEC_GRADE, LEC_SEMESTER, LEC_TIME_END, LEC_TIME_START, LEC_YEAR, LEC_NAME, LEC_PROFESSOR, LECTURE_ROOM) VALUES
    (3, '수요일', 1, 1, '13:00:00', '10:00:00', 2024, 'lecName5', 'professorName5','효행관 405호');
INSERT INTO Lecture (LEC_CREDIT, LEC_DAY, LEC_GRADE, LEC_SEMESTER, LEC_TIME_END, LEC_TIME_START, LEC_YEAR, LEC_NAME, LEC_PROFESSOR, LECTURE_ROOM) VALUES
    (2, '수요일', 1, 1, '16:00:00', '14:00:00', 2024, 'lecName6', 'professorName6','효행관 406호');
INSERT INTO Lecture (LEC_CREDIT, LEC_DAY, LEC_GRADE, LEC_SEMESTER, LEC_TIME_END, LEC_TIME_START, LEC_YEAR, LEC_NAME, LEC_PROFESSOR, LECTURE_ROOM) VALUES
    (2, '목요일', 1, 1, '11:00:00', '09:00:00', 2024, 'lecName7', 'professorName7','효행관 407호');
INSERT INTO Lecture (LEC_CREDIT, LEC_DAY, LEC_GRADE, LEC_SEMESTER, LEC_TIME_END, LEC_TIME_START, LEC_YEAR, LEC_NAME, LEC_PROFESSOR, LECTURE_ROOM) VALUES
    (3, '목요일', 1, 1, '16:00:00', '13:00:00', 2024, 'lecName8', 'professorName8','효행관 408호');
INSERT INTO Lecture (LEC_CREDIT, LEC_DAY, LEC_GRADE, LEC_SEMESTER, LEC_TIME_END, LEC_TIME_START, LEC_YEAR, LEC_NAME, LEC_PROFESSOR, LECTURE_ROOM) VALUES
    (2, '금요일', 1, 1, '12:00:00', '10:00:00', 2024, 'lecName9', 'professorName9','효행관 409호');
INSERT INTO Lecture (LEC_CREDIT, LEC_DAY, LEC_GRADE, LEC_SEMESTER, LEC_TIME_END, LEC_TIME_START, LEC_YEAR, LEC_NAME, LEC_PROFESSOR, LECTURE_ROOM) VALUES
    (3, '금요일', 1, 1, '16:00:00', '13:00:00', 2024, 'lecName10', 'professorName10','효행관 410호');
-- lecturePreset 삽입
INSERT INTO LECTURE_PRESET (LP_INDEX, PRESET_NAME, LEC_SEMESTER, LEC_YEAR) VALUES(1,'A',1,2024);
INSERT INTO LECTURE_PRESET (LP_INDEX, PRESET_NAME, LEC_SEMESTER, LEC_YEAR) VALUES(2,'B',1,2024);
INSERT INTO LECTURE_PRESET (LP_INDEX, PRESET_NAME, LEC_SEMESTER, LEC_YEAR) VALUES(3,'C',1,2024);

-- lecturePreset A data
INSERT INTO Lecture (LEC_CREDIT, LEC_DAY, LEC_GRADE, LEC_SEMESTER, LP_INDEX, LEC_TIME_END, LEC_TIME_START, LEC_YEAR, LEC_NAME, LEC_PROFESSOR, LECTURE_ROOM) VALUES
    (2, '화요일', 1, 1, 1, '12:00:00', '10:00:00', 2024, 'lecName3', 'professorName3','효행관 403호');
INSERT INTO Lecture (LEC_CREDIT, LEC_DAY, LEC_GRADE, LEC_SEMESTER, LP_INDEX, LEC_TIME_END, LEC_TIME_START, LEC_YEAR, LEC_NAME, LEC_PROFESSOR, LECTURE_ROOM) VALUES
    (3, '화요일', 1, 1, 1, '16:00:00', '13:00:00', 2024, 'lecName4', 'professorName4','효행관 404호');
INSERT INTO Lecture (LEC_CREDIT, LEC_DAY, LEC_GRADE, LEC_SEMESTER, LP_INDEX, LEC_TIME_END, LEC_TIME_START, LEC_YEAR, LEC_NAME, LEC_PROFESSOR, LECTURE_ROOM) VALUES
    (3, '수요일', 1, 1, 1, '13:00:00', '10:00:00', 2024, 'lecName5', 'professorName5','효행관 405호');
INSERT INTO Lecture (LEC_CREDIT, LEC_DAY, LEC_GRADE, LEC_SEMESTER, LP_INDEX, LEC_TIME_END, LEC_TIME_START, LEC_YEAR, LEC_NAME, LEC_PROFESSOR, LECTURE_ROOM) VALUES
    (2, '수요일', 1, 1, 1, '16:00:00', '14:00:00', 2024, 'lecName6', 'professorName6','효행관 406호');
INSERT INTO Lecture (LEC_CREDIT, LEC_DAY, LEC_GRADE, LEC_SEMESTER, LP_INDEX, LEC_TIME_END, LEC_TIME_START, LEC_YEAR, LEC_NAME, LEC_PROFESSOR, LECTURE_ROOM) VALUES
    (2, '목요일', 1, 1, 1, '11:00:00', '09:00:00', 2024, 'lecName7', 'professorName7','효행관 407호');
INSERT INTO Lecture (LEC_CREDIT, LEC_DAY, LEC_GRADE, LEC_SEMESTER, LP_INDEX, LEC_TIME_END, LEC_TIME_START, LEC_YEAR, LEC_NAME, LEC_PROFESSOR, LECTURE_ROOM) VALUES
    (3, '목요일', 1, 1, 1, '16:00:00', '13:00:00', 2024, 'lecName8', 'professorName8','효행관 408호');
INSERT INTO Lecture (LEC_CREDIT, LEC_DAY, LEC_GRADE, LEC_SEMESTER, LP_INDEX, LEC_TIME_END, LEC_TIME_START, LEC_YEAR, LEC_NAME, LEC_PROFESSOR, LECTURE_ROOM) VALUES
    (2, '금요일', 1, 1, 1, '12:00:00', '10:00:00', 2024, 'lecName9', 'professorName9','효행관 409호');
INSERT INTO Lecture (LEC_CREDIT, LEC_DAY, LEC_GRADE, LEC_SEMESTER, LP_INDEX, LEC_TIME_END, LEC_TIME_START, LEC_YEAR, LEC_NAME, LEC_PROFESSOR, LECTURE_ROOM) VALUES
    (3, '금요일', 1, 1, 1, '16:00:00', '13:00:00', 2024, 'lecName10', 'professorName10','효행관 410호');

--lecturePreset B data
INSERT INTO Lecture (LEC_CREDIT, LEC_DAY, LEC_GRADE, LEC_SEMESTER, LP_INDEX, LEC_TIME_END, LEC_TIME_START, LEC_YEAR, LEC_NAME, LEC_PROFESSOR, LECTURE_ROOM) VALUES
    (3, '월요일', 1, 1, 2, '12:00:00', '09:00:00', 2024, 'lecName1', 'professorName1','효행관 401호');
INSERT INTO Lecture (LEC_CREDIT, LEC_DAY, LEC_GRADE, LEC_SEMESTER, LP_INDEX,LEC_TIME_END, LEC_TIME_START, LEC_YEAR, LEC_NAME, LEC_PROFESSOR, LECTURE_ROOM) VALUES
    (2, '월요일', 1, 1, 2, '15:00:00', '13:00:00', 2024, 'lecName2', 'professorName2','효행관 402호');
INSERT INTO Lecture (LEC_CREDIT, LEC_DAY, LEC_GRADE, LEC_SEMESTER, LP_INDEX, LEC_TIME_END, LEC_TIME_START, LEC_YEAR, LEC_NAME, LEC_PROFESSOR, LECTURE_ROOM) VALUES
    (3, '수요일', 1, 1, 2, '13:00:00', '10:00:00', 2024, 'lecName5', 'professorName5','효행관 405호');
INSERT INTO Lecture (LEC_CREDIT, LEC_DAY, LEC_GRADE, LEC_SEMESTER, LP_INDEX, LEC_TIME_END, LEC_TIME_START, LEC_YEAR, LEC_NAME, LEC_PROFESSOR, LECTURE_ROOM) VALUES
    (2, '수요일', 1, 1, 2, '16:00:00', '14:00:00', 2024, 'lecName6', 'professorName6','효행관 406호');
INSERT INTO Lecture (LEC_CREDIT, LEC_DAY, LEC_GRADE, LEC_SEMESTER, LP_INDEX, LEC_TIME_END, LEC_TIME_START, LEC_YEAR, LEC_NAME, LEC_PROFESSOR, LECTURE_ROOM) VALUES
    (2, '목요일', 1, 1, 2, '11:00:00', '09:00:00', 2024, 'lecName7', 'professorName7','효행관 407호');
INSERT INTO Lecture (LEC_CREDIT, LEC_DAY, LEC_GRADE, LEC_SEMESTER, LP_INDEX, LEC_TIME_END, LEC_TIME_START, LEC_YEAR, LEC_NAME, LEC_PROFESSOR, LECTURE_ROOM) VALUES
    (3, '목요일', 1, 1, 2, '16:00:00', '13:00:00', 2024, 'lecName8', 'professorName8','효행관 408호');
INSERT INTO Lecture (LEC_CREDIT, LEC_DAY, LEC_GRADE, LEC_SEMESTER, LP_INDEX, LEC_TIME_END, LEC_TIME_START, LEC_YEAR, LEC_NAME, LEC_PROFESSOR, LECTURE_ROOM) VALUES
    (2, '금요일', 1, 1, 2, '12:00:00', '10:00:00', 2024, 'lecName9', 'professorName9','효행관 409호');
INSERT INTO Lecture (LEC_CREDIT, LEC_DAY, LEC_GRADE, LEC_SEMESTER, LP_INDEX, LEC_TIME_END, LEC_TIME_START, LEC_YEAR, LEC_NAME, LEC_PROFESSOR, LECTURE_ROOM) VALUES
    (3, '금요일', 1, 1, 2, '16:00:00', '13:00:00', 2024, 'lecName10', 'professorName10','효행관 410호');

-- lecturePreset C data
INSERT INTO Lecture (LEC_CREDIT, LEC_DAY, LEC_GRADE, LEC_SEMESTER, LP_INDEX, LEC_TIME_END, LEC_TIME_START, LEC_YEAR, LEC_NAME, LEC_PROFESSOR, LECTURE_ROOM) VALUES
    (3, '월요일', 1, 1, 3, '12:00:00', '09:00:00', 2024, 'lecName1', 'professorName1','효행관 401호');
INSERT INTO Lecture (LEC_CREDIT, LEC_DAY, LEC_GRADE, LEC_SEMESTER, LP_INDEX,LEC_TIME_END, LEC_TIME_START, LEC_YEAR, LEC_NAME, LEC_PROFESSOR, LECTURE_ROOM) VALUES
    (2, '월요일', 1, 1, 3, '15:00:00', '13:00:00', 2024, 'lecName2', 'professorName2','효행관 402호');
INSERT INTO Lecture (LEC_CREDIT, LEC_DAY, LEC_GRADE, LEC_SEMESTER, LP_INDEX, LEC_TIME_END, LEC_TIME_START, LEC_YEAR, LEC_NAME, LEC_PROFESSOR, LECTURE_ROOM) VALUES
    (2, '화요일', 1, 1, 3, '12:00:00', '10:00:00', 2024, 'lecName3', 'professorName3','효행관 403호');
INSERT INTO Lecture (LEC_CREDIT, LEC_DAY, LEC_GRADE, LEC_SEMESTER, LP_INDEX, LEC_TIME_END, LEC_TIME_START, LEC_YEAR, LEC_NAME, LEC_PROFESSOR, LECTURE_ROOM) VALUES
    (3, '화요일', 1, 1, 3, '16:00:00', '13:00:00', 2024, 'lecName4', 'professorName4','효행관 404호');
INSERT INTO Lecture (LEC_CREDIT, LEC_DAY, LEC_GRADE, LEC_SEMESTER, LP_INDEX, LEC_TIME_END, LEC_TIME_START, LEC_YEAR, LEC_NAME, LEC_PROFESSOR, LECTURE_ROOM) VALUES
    (2, '목요일', 1, 1, 3, '11:00:00', '09:00:00', 2024, 'lecName7', 'professorName7','효행관 407호');
INSERT INTO Lecture (LEC_CREDIT, LEC_DAY, LEC_GRADE, LEC_SEMESTER, LP_INDEX, LEC_TIME_END, LEC_TIME_START, LEC_YEAR, LEC_NAME, LEC_PROFESSOR, LECTURE_ROOM) VALUES
    (3, '목요일', 1, 1, 3, '16:00:00', '13:00:00', 2024, 'lecName8', 'professorName8','효행관 408호');
INSERT INTO Lecture (LEC_CREDIT, LEC_DAY, LEC_GRADE, LEC_SEMESTER, LP_INDEX, LEC_TIME_END, LEC_TIME_START, LEC_YEAR, LEC_NAME, LEC_PROFESSOR, LECTURE_ROOM) VALUES
    (2, '금요일', 1, 1, 3, '12:00:00', '10:00:00', 2024, 'lecName9', 'professorName9','효행관 409호');
INSERT INTO Lecture (LEC_CREDIT, LEC_DAY, LEC_GRADE, LEC_SEMESTER, LP_INDEX, LEC_TIME_END, LEC_TIME_START, LEC_YEAR, LEC_NAME, LEC_PROFESSOR, LECTURE_ROOM) VALUES
    (3, '금요일', 1, 1, 3, '16:00:00', '13:00:00', 2024, 'lecName10', 'professorName10','효행관 410호');
