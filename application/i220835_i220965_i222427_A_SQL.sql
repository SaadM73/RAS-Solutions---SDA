use project;

CREATE TABLE Admin 
(
    admin_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone_number VARCHAR(15)
);

INSERT INTO Admin (name, email, password, phone_number)
VALUES 
('Arshman Khawar', 'arshman@gmail.com', 'arshman123', '03000000000' ),
('Rehan Tariq', 'rehan@gmail.com', 'rehan123', '03000000001' ),
('Saad Mursaleen', 'saad@gmail.com', 'saad123', '03000000002' );


-- Member Table
CREATE TABLE Member 
(
    member_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone_number VARCHAR(15),
    membership_type VARCHAR(50) DEFAULT 'Basic',
    registration_date DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Booking Table
CREATE TABLE Booking 
(
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    member_id INT NOT NULL,
    facility_id INT NOT NULL,
    booking_date DATE NOT NULL,
    time_slot TIME NOT NULL,
    status VARCHAR(20) DEFAULT 'Confirmed',
    FOREIGN KEY (member_id) REFERENCES Member(member_id),
    FOREIGN KEY (facility_id) REFERENCES Facility(facility_id)
);

-- Facility Table
CREATE TABLE Facility (
    facility_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(50),
    availability_status VARCHAR(20) DEFAULT 'Available',
    capacity INT
);

-- Payment Table
CREATE TABLE Payment (
    payment_id INT PRIMARY KEY AUTO_INCREMENT,
    member_id INT NOT NULL,
    booking_id INT,
    payment_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    amount DECIMAL(10, 2) NOT NULL,
    payment_method VARCHAR(50),
    status VARCHAR(20) DEFAULT 'Paid',
    FOREIGN KEY (member_id) REFERENCES Member(member_id),
    FOREIGN KEY (booking_id) REFERENCES Booking(booking_id)
);

-- Refund Table
CREATE TABLE Refund (
    refund_id INT PRIMARY KEY AUTO_INCREMENT,
    payment_id INT NOT NULL,
    refund_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    amount DECIMAL(10, 2) NOT NULL,
    reason TEXT,
    status VARCHAR(20) DEFAULT 'Pending',
    FOREIGN KEY (payment_id) REFERENCES Payment(payment_id)
);

-- Staff Table
CREATE TABLE Staff (
    staff_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    role VARCHAR(50),
    email VARCHAR(255) UNIQUE NOT NULL,
    phone_number VARCHAR(15),
	password VARCHAR(255) NOT NULL
);

-- Facility Maintenance Table
CREATE TABLE Facility_Maintenance (
    maintenance_id INT PRIMARY KEY AUTO_INCREMENT,
    facility_id INT NOT NULL,
    scheduled_date DATE NOT NULL,
    task_description TEXT,
    assigned_staff_id INT,
    status VARCHAR(20) DEFAULT 'Scheduled',
    FOREIGN KEY (facility_id) REFERENCES Facility(facility_id),
    FOREIGN KEY (assigned_staff_id) REFERENCES Staff(staff_id)
);

-- Report Table
CREATE TABLE Report (
    report_id INT PRIMARY KEY AUTO_INCREMENT,
    generated_by INT NOT NULL,
    report_type VARCHAR(50),
    date_generated DATETIME DEFAULT CURRENT_TIMESTAMP,
    content TEXT,
    FOREIGN KEY (generated_by) REFERENCES Staff(staff_id)
);

-- Membership Table
CREATE TABLE Membership (
    membership_id INT PRIMARY KEY AUTO_INCREMENT,
    member_id INT NOT NULL,
    start_date DATETIME NOT NULL,
    end_date DATETIME NOT NULL,
    status VARCHAR(20) DEFAULT 'Active',
    FOREIGN KEY (member_id) REFERENCES Member(member_id)
);

-- Insertions for Member
INSERT INTO Member (name, email, password, phone_number, membership_type)
VALUES 
('Ali Khan', 'ali.khan@example.com', 'ali123', '03111222333', 'Premium'),
('Ayesha Ahmed', 'ayesha.ahmed@example.com', 'ayesha123', '03451234567', 'Standard'),
('Bilal Akhtar', 'bilal.akhtar@example.com', 'bilal123', '03339876543', 'Basic'),
('Sara Shah', 'sara.shah@example.com', 'sara123', '03155667788', 'Premium'),
('Usman Javed', 'usman.javed@example.com', 'usman123', '03217894567', 'Basic'),
('Zainab Ali', 'zainab.ali@example.com', 'zainab123', '03149988776', 'Standard'),
('Hassan Raza', 'hassan.raza@example.com', 'hassan123', '03001234567', 'Premium'),
('Fatima Noor', 'fatima.noor@example.com', 'fatima123', '03451234566', 'Standard'),
('Hamza Malik', 'hamza.malik@example.com', 'hamza123', '03334566789', 'Premium'),
('Noor Zafar', 'noor.zafar@example.com', 'noor123', '03219876543', 'Basic'),
('Adeel Ansari', 'adeel.ansari@example.com', 'adeel123', '03441122334', 'Standard'),
('Anum Zehra', 'anum.zehra@example.com', 'anum123', '03149876545', 'Basic'),
('Fahad Siddiqui', 'fahad.siddiqui@example.com', 'fahad123', '03017788999', 'Premium'),
('Mahnoor Tariq', 'mahnoor.tariq@example.com', 'mahnoor123', '03457891234', 'Standard'),
('Hina Iqbal', 'hina.iqbal@example.com', 'hina123', '03217788901', 'Premium'),
('Waleed Abbas', 'waleed.abbas@example.com', 'waleed123', '03334566712', 'Basic'),
('Rabia Khan', 'rabia.khan@example.com', 'rabia123', '03156677890', 'Premium'),
('Yasir Malik', 'yasir.malik@example.com', 'yasir123', '03099876543', 'Basic'),
('Eman Fatima', 'eman.fatima@example.com', 'eman123', '03451239087', 'Standard'),
('Saad Ahmad', 'saad.ahmad@example.com', 'saad123', '03331234590', 'Premium');

-- Insertions for Facility
INSERT INTO Facility (name, type, availability_status, capacity)
VALUES 
('Badminton Court', 'Sports', 'Available', 4),
('Swimming Pool', 'Recreational', 'Available', 20),
('Cricket Ground', 'Sports', 'Under Maintenance', 22),
('Fitness Gym', 'Fitness', 'Available', 30),
('Tennis Court', 'Sports', 'Available', 4),
('Conference Room', 'Meeting', 'Available', 10),
('Basketball Court', 'Sports', 'Available', 10),
('Football Ground', 'Sports', 'Available', 20),
('Sauna Room', 'Recreational', 'Available', 5),
('Kids Play Area', 'Recreational', 'Available', 15),
('Yoga Hall', 'Recreational', 'Available', 25),
('Table Tennis Room', 'Sports', 'Available', 4),
('Cycling Track', 'Fitness', 'Available', 50),
('Squash Court', 'Sports', 'Available', 4),
('Library', 'Recreational', 'Available', 10),
('Running Track', 'Fitness', 'Available', 50),
('Spa Room', 'Recreational', 'Available', 8),
('Golf Course', 'Sports', 'Available', 10),
('Boxing Ring', 'Sports', 'Available', 4),
('Archery Range', 'Sports', 'Available', 5);

-- Insertions for Staff 
INSERT INTO Staff (name, role, email, phone_number, password)
VALUES 
('Ahmad Raza', 'Manager', 'ahmad.raza@example.com', '03012345678', 'manager123'),
('Bushra Tariq', 'Receptionist', 'bushra.tariq@example.com', '03219988776', 'bushra123'),
('Kamran Abbas', 'Maintenance Staff', 'kamran.abbas@example.com', '03119988777', 'kamran123'),
('Rabia Siddiqui', 'Trainer', 'rabia.siddiqui@example.com', '03451237890', 'rabia123'),
('Faisal Aziz', 'Maintenance Staff', 'faisal.aziz@example.com', '03011234567', 'faisal123'),
('Shahzad Qureshi', 'Trainer', 'shahzad.qureshi@example.com', '03115567890', 'trainer123'),
('Sadia Zafar', 'Receptionist', 'sadia.zafar@example.com', '03331239087', 'reception123'),
('Arif Ali', 'Manager', 'arif.ali@example.com', '03451234578', 'manager456'),
('Huma Ilyas', 'Receptionist', 'huma.ilyas@example.com', '03219988765', 'huma123'),
('Asif Khan', 'Trainer', 'asif.khan@example.com', '03345566778', 'trainer567'),
('Mubashir Riaz', 'Maintenance Staff', 'mubashir.riaz@example.com', '03119988788', 'staff123'),
('Iram Fatima', 'Receptionist', 'iram.fatima@example.com', '03451238901', 'iram123'),
('Waqar Malik', 'Manager', 'waqar.malik@example.com', '03098877654', 'waqar123'),
('Kiran Niaz', 'Trainer', 'kiran.niaz@example.com', '03341237890', 'trainer890'),
('Sajid Hussain', 'Maintenance Staff', 'sajid.hussain@example.com', '03214455667', 'staff456'),
('Zehra Shah', 'Receptionist', 'zehra.shah@example.com', '03458877654', 'zehra123'),
('Tariq Abbas', 'Manager', 'tariq.abbas@example.com', '03358877654', 'manager789'),
('Uzma Aftab', 'Trainer', 'uzma.aftab@example.com', '03118877654', 'trainer111'),
('Naveed Akhtar', 'Maintenance Staff', 'naveed.akhtar@example.com', '03224455667', 'staff999'),
('Afshan Raza', 'Receptionist', 'afshan.raza@example.com', '03417788999', 'afshan123');

-- Insertions for Booking
INSERT INTO Booking (member_id, facility_id, booking_date, time_slot, status)
VALUES 
(1, 1, '2024-11-25', '10:00:00', 'Confirmed'),
(2, 2, '2024-11-25', '11:00:00', 'Confirmed'),
(3, 3, '2024-11-26', '12:00:00', 'Pending'),
(4, 4, '2024-11-27', '14:00:00', 'Cancelled'),
(5, 5, '2024-11-28', '16:00:00', 'Confirmed'),
(6, 6, '2024-11-29', '18:00:00', 'Confirmed'),
(7, 7, '2024-11-30', '19:00:00', 'Confirmed'),
(8, 8, '2024-12-01', '09:00:00', 'Pending'),
(9, 9, '2024-12-02', '08:00:00', 'Confirmed'),
(10, 10, '2024-12-03', '17:00:00', 'Cancelled'),
(11, 1, '2024-12-04', '13:00:00', 'Confirmed'),
(12, 2, '2024-12-05', '15:00:00', 'Pending'),
(13, 3, '2024-12-06', '10:30:00', 'Confirmed'),
(14, 4, '2024-12-07', '16:30:00', 'Pending'),
(15, 5, '2024-12-08', '18:30:00', 'Confirmed'),
(16, 6, '2024-12-09', '08:30:00', 'Cancelled'),
(17, 7, '2024-12-10', '19:30:00', 'Confirmed'),
(18, 8, '2024-12-11', '11:30:00', 'Pending'),
(19, 9, '2024-12-12', '09:30:00', 'Confirmed'),
(20, 10, '2024-12-13', '13:30:00', 'Confirmed');

-- Insertions for Payment
INSERT INTO Payment (member_id, booking_id, amount, payment_method, status)
VALUES 
(1, 1, 1500.00, 'Credit Card', 'Paid'),
(2, 2, 1200.00, 'Cash', 'Paid'),
(3, 3, 2000.00, 'Debit Card', 'Paid'),
(4, 4, 1800.00, 'Online Transfer', 'Refunded'),
(5, 5, 2500.00, 'Credit Card', 'Paid'),
(6, 6, 3000.00, 'Cash', 'Paid'),
(7, 7, 500.00, 'Debit Card', 'Pending'),
(8, 8, 4000.00, 'Online Transfer', 'Paid'),
(9, 9, 800.00, 'Credit Card', 'Refunded'),
(10, 10, 1500.00, 'Cash', 'Paid'),
(11, 11, 1000.00, 'Debit Card', 'Pending'),
(12, 12, 3500.00, 'Online Transfer', 'Paid'),
(13, 13, 2200.00, 'Credit Card', 'Paid'),
(14, 14, 1600.00, 'Cash', 'Paid'),
(15, 15, 1400.00, 'Debit Card', 'Paid'),
(16, 16, 2700.00, 'Online Transfer', 'Refunded'),
(17, 17, 1800.00, 'Credit Card', 'Paid'),
(18, 18, 900.00, 'Cash', 'Paid'),
(19, 19, 3200.00, 'Debit Card', 'Pending'),
(20, 20, 5000.00, 'Online Transfer', 'Paid');

-- Insertions for Refund
INSERT INTO Refund (payment_id, refund_date, amount, reason, status)
VALUES 
(4, '2024-11-30 14:00:00', 1800.00, 'Booking Cancelled', 'Processed'),
(9, '2024-12-02 15:00:00', 800.00, 'Service Unavailable', 'Pending'),
(16, '2024-12-05 16:30:00', 2700.00, 'Overpayment', 'Processed'),
(18, '2024-12-07 17:00:00', 900.00, 'Technical Issues', 'Pending'),
(3, '2024-12-08 18:30:00', 2000.00, 'Facility Maintenance', 'Processed'),
(8, '2024-12-10 08:30:00', 4000.00, 'Cancellation by Member', 'Pending'),
(5, '2024-12-12 09:00:00', 2500.00, 'Change in Facility', 'Processed'),
(13, '2024-12-13 10:30:00', 2200.00, 'Event Reschedule', 'Pending'),
(19, '2024-12-14 11:30:00', 3200.00, 'Member Dissatisfaction', 'Processed'),
(1, '2024-12-15 12:30:00', 1500.00, 'Duplicate Payment', 'Processed'),
(20, '2024-12-16 13:30:00', 5000.00, 'Facility Closure', 'Pending'),
(12, '2024-12-17 14:30:00', 3500.00, 'Billing Error', 'Processed'),
(2, '2024-12-18 15:30:00', 1200.00, 'Time Slot Issues', 'Pending'),
(10, '2024-12-19 16:30:00', 1500.00, 'Facility Upgrade', 'Processed'),
(7, '2024-12-20 17:30:00', 500.00, 'Event Cancellation', 'Pending'),
(15, '2024-12-21 18:30:00', 1400.00, 'Request by Member', 'Processed'),
(17, '2024-12-22 19:30:00', 1800.00, 'Conflict in Booking', 'Processed'),
(11, '2024-12-23 20:30:00', 1000.00, 'Weather Issues', 'Pending'),
(6, '2024-12-24 21:30:00', 3000.00, 'Technical Glitch', 'Processed'),
(14, '2024-12-25 22:30:00', 1600.00, 'Overpayment', 'Processed');
 
 -- Insertions for Facility_Maintenance
INSERT INTO Facility_Maintenance (facility_id, scheduled_date, task_description, assigned_staff_id, status)
VALUES 
(1, '2024-11-26', 'Repairing court floor', 3, 'Completed'),
(2, '2024-11-27', 'Cleaning pool area', 5, 'Scheduled'),
(3, '2024-11-28', 'Grass trimming', 6, 'In Progress'),
(4, '2024-11-29', 'Equipment upgrade', 7, 'Scheduled'),
(5, '2024-11-30', 'Lighting repair', 3, 'Completed'),
(6, '2024-12-01', 'Painting walls', 5, 'Scheduled'),
(7, '2024-12-02', 'Net replacement', 6, 'In Progress'),
(8, '2024-12-03', 'Sanitizing facility', 7, 'Completed'),
(9, '2024-12-04', 'Locker room upgrade', 3, 'Scheduled'),
(10, '2024-12-05', 'Track cleaning', 5, 'In Progress'),
(1, '2024-12-06', 'Court markings', 6, 'Scheduled'),
(2, '2024-12-07', 'Filter replacement', 7, 'Completed'),
(3, '2024-12-08', 'Boundary repainting', 3, 'In Progress'),
(4, '2024-12-09', 'Updating gym equipment', 5, 'Completed'),
(5, '2024-12-10', 'Tennis net tightening', 6, 'Scheduled'),
(6, '2024-12-11', 'Window replacement', 7, 'In Progress'),
(7, '2024-12-12', 'Court lighting upgrade', 3, 'Completed'),
(8, '2024-12-13', 'Power backup installation', 5, 'Scheduled'),
(9, '2024-12-14', 'Washroom maintenance', 6, 'In Progress'),
(10, '2024-12-15', 'Ceiling repair', 7, 'Completed');

-- Insertions for Report
INSERT INTO Report (generated_by, report_type, content)
VALUES 
(1, 'Monthly Facility Usage', 'This report includes data about facility usage for November.'),
(2, 'Maintenance Summary', 'Detailed summary of maintenance tasks completed this month.'),
(3, 'Revenue Report', 'Summary of all revenues generated in November.'),
(4, 'Booking Report', 'Detailed analysis of booking patterns for November.'),
(5, 'Refund Report', 'Summary of all refunds processed this month.'),
(6, 'Staff Performance', 'Evaluation report of staff performance for November.'),
(7, 'Complaint Summary', 'Detailed summary of complaints received this month.'),
(8, 'Membership Statistics', 'Statistics on active and expired memberships.'),
(9, 'Event Participation', 'Report on participation in organized events.'),
(10, 'Annual Review', 'Comprehensive review of annual activities and revenue.'),
(1, 'Equipment Damage', 'Report on damaged equipment and replacement tasks.'),
(2, 'Electricity Usage', 'Details on electricity usage in facilities.'),
(3, 'Cleanliness Audit', 'Audit results of cleanliness in the facilities.'),
(4, 'Revenue Projection', 'Projected revenue for the next quarter.'),
(5, 'Facility Availability', 'Current status of all facilities.'),
(6, 'Pending Tasks', 'Summary of all pending maintenance tasks.'),
(7, 'Customer Feedback', 'Detailed report on customer feedback collected.'),
(8, 'Sports Statistics', 'Detailed analysis of sports facility usage.'),
(9, 'Health Audit', 'Results of health and safety inspections.'),
(10, 'Special Event Report', 'Summary of special events conducted this month.');

-- Insertions for Membership
INSERT INTO Membership (member_id, start_date, end_date, status)
VALUES 
(1, '2024-01-01 08:00:00', '2024-12-31 23:59:59', 'Active'),
(2, '2023-06-01 08:00:00', '2024-05-31 23:59:59', 'Active'),
(3, '2024-07-01 08:00:00', '2024-12-31 23:59:59', 'Inactive'),
(4, '2024-03-01 08:00:00', '2024-12-31 23:59:59', 'Active'),
(5, '2024-02-01 08:00:00', '2024-11-30 23:59:59', 'Active'),
(6, '2024-04-01 08:00:00', '2024-12-31 23:59:59', 'Active'),
(7, '2023-01-01 08:00:00', '2024-12-31 23:59:59', 'Expired'),
(8, '2024-09-01 08:00:00', '2025-08-31 23:59:59', 'Active'),
(9, '2024-10-01 08:00:00', '2024-12-31 23:59:59', 'Active'),
(10, '2023-12-01 08:00:00', '2024-11-30 23:59:59', 'Inactive'),
(11, '2023-07-01 08:00:00', '2024-06-30 23:59:59', 'Active'),
(12, '2024-01-01 08:00:00', '2024-12-31 23:59:59', 'Active'),
(13, '2023-04-01 08:00:00', '2024-03-31 23:59:59', 'Inactive'),
(14, '2024-05-01 08:00:00', '2024-12-31 23:59:59', 'Active'),
(15, '2024-06-01 08:00:00', '2025-05-31 23:59:59', 'Active'),
(16, '2023-11-01 08:00:00', '2024-10-31 23:59:59', 'Expired'),
(17, '2024-08-01 08:00:00', '2025-07-31 23:59:59', 'Active'),
(18, '2023-10-01 08:00:00', '2024-09-30 23:59:59', 'Active'),
(19, '2023-03-01 08:00:00', '2024-02-29 23:59:59', 'Inactive'),
(20, '2023-05-01 08:00:00', '2024-04-30 23:59:59', 'Active');
select facility_id from Facility where Facility.name = 'Sauna Room';
select * from Booking;
select * from facility;
select * from Member;
select * from staff;
select * from payment;
select * from refund;
select * from Facility_Maintenance;
select * from membership;
select * from member;




