-- V2__Insert_initial_users.sql
INSERT INTO users (rfc, curp, username, first_name, last_name, email, phone_number, birth_date, status, created_by)
VALUES ('AERA850315ABC', 'AERA850315HDFNGL01', 'admin','Angel', 'Ruiz', 'admin@mars.com', '5551234567', '1985-03-15', 'ACTIVE', 'system'),
       ('GOMA920710XYZ', 'GOMA920710MDFNZR02', 'maria.gonzalez','Maria', 'Gonzalez', 'maria.gonzalez@mars.com','5552345678', '1992-07-10', 'ACTIVE', 'admin'),
       ('LOPE880525DEF', 'LOPE880525HDFMPR03', 'pedro.lopez','Pedro', 'Lopez', 'pedro.lopez@mars.com','5553456789', '1988-05-25', 'ACTIVE', 'admin'),
       ('HECA901203GHI', 'HECA901203MDFRLR04', 'ana.hernandez','Ana', 'Hernandez', 'ana.hernandez@mars.com','5554567890', '1990-12-03', 'INACTIVE', 'admin'),
       ('JIME870918JKL', 'JIME870918HDFMNR05', 'carlos.jimenez','Carlos', 'Jimenez', 'carlos.jimenez@mars.com','5555678901', '1987-09-18', 'BLOCKED', 'admin'),
       ('RAMA940820MNO', 'RAMA940820MDFMTR06', 'laura.ramirez','Laura', 'Ramirez', 'laura.ramirez@mars.com','5556789012', '1994-08-20', 'ACTIVE', 'admin');

-- Additional insert for testing
INSERT INTO users (username, password, first_name, last_name, email, status, created_by)
VALUES ('testuser', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Test', 'User', 'test@mars.com','ACTIVE', 'system');
