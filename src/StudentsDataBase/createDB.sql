DO $$
     BEGIN
         CREATE TABLE IF NOT EXISTS STUDENTS
         (
             FIRST_NAME CHARACTER VARYING NOT NULL,
             LAST_NAME  CHARACTER VARYING NOT NULL,
             AGE        INTEGER           NOT NULL,
             NUMBER     CHARACTER VARYING NOT NULL,
             CLASS      INTEGER           NOT NULL,
             SUBCLASS   CHARACTER VARYING NOT NULL,
             UNIQUE (NUMBER)
         );

IF NOT EXISTS (SELECT * FROM STUDENTS) THEN
         INSERT INTO STUDENTS
         VALUES ('David', 'Lake', 15, 380995486203, 10, 'B'),
                ('Bryan', 'Hereon', 15, 380996584503, 8, 'A'),
                ('Tim', 'Holcomb', 15, 38065341296, 9, 'B'),
                ('Dallas', 'Kearns', 15, 380994853495, 8, 'A'),
                ('Sidney', 'Lozano', 15, 380753842395, 10, 'A'),
                ('Damien', 'Devore', 15, 380995847596, 10, 'B'),
                ('Sylvester', 'Belcher', 15, 380931234567, 10, 'B'),
                ('Ignacio', 'Machado', 15, 380501234567, 10, 'B'),
                ('Elvis', 'Masterson', 15, 380945648666, 9, 'A'),
                ('Adrian', 'Hills', 15, 380541234567, 7, 'A');
         END IF;

    END$$;









