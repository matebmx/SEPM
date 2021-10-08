-- insert initial test data
-- the IDs are hardcoded to enable references between further test data
-- negative IDs are used to not interfere with user-entered data and allow clean deletion of test data


DELETE
FROM feed
WHERE ID < 0;
INSERT INTO feed (ID, NAME, DESCRIPTION, NUTRITIONALVALUE)
VALUES (-1, 'Food1', 'Test description for the first food')
     , (-2, 'Food2', 'Test description for the second food')
     , (-3, 'Food3', 'Test description for the third food')
     , (-4, 'Food4', 'Test description for the fourth food')
     , (-5, 'Food5', 'Test description for the fifth food')
     , (-6, 'Food6', 'Test description for the sixth test food')
     , (-7, 'Food7', 'Test description for the seventh test food')
     , (-8, 'Food8', 'Test description for the eighth test food')
     , (-9, 'Food9', 'Test description for the ninth test food')
     , (-10, 'Food10', 'Test description for the tenth test food')
;

DELETE
FROM horse
WHERE ID < 0;
INSERT INTO horse (ID, NAME, DESCRIPTION, DATEBORN, GENDER, FOOD, MOTHER, FATHER)
VALUES (-1, 'TestHorse1', 'Test description 1', TO_DATE('17/12/2015', 'DD/MM/YYYY'), 'male', -1, null, null)
     , (-2, 'TestHorse2', 'Test description 2', TO_DATE('14/03/2014', 'DD/MM/YYYY'), 'female', -2, null, null)
     , (-3, 'TestHorse3', 'Test description 3', TO_DATE('11/01/2016', 'DD/MM/YYYY'), 'male', -3, -2, -1)
     , (-4, 'TestHorse4', 'Test description 4', TO_DATE('11/01/2017', 'DD/MM/YYYY'), 'female', -4, -2, -1)
     , (-5, 'TestHorse5', 'Test description 5', TO_DATE('11/01/2018', 'DD/MM/YYYY'), 'male', -5, -2, -1)
     , (-6, 'TestHorse6', 'Test description 6', TO_DATE('11/01/2018', 'DD/MM/YYYY'), 'female', -6,  -3, -4)
     , (-7, 'TestHorse7', 'Test description 7', TO_DATE('04/09/2017', 'DD/MM/YYYY'), 'male', -7, -3, -4)
     , (-8, 'TestHorse8', 'Test description 8', TO_DATE('02/09/2019', 'DD/MM/YYYY'), 'female', -8, -5, -6)
     , (-9, 'TestHorse9', 'Test description 9', TO_DATE('19/10/2019', 'DD/MM/YYYY'), 'male', -9, -7, -8)
     , (-10, 'TestHorse10', 'Test description 10', TO_DATE('01/01/2020', 'DD/MM/YYYY'), 'female', -10, -7, -9)
;