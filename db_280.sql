CREATE OR REPLACE DATABASE db_280;

USE db_280;

DROP TABLE IF EXISTS discussion_comments;
DROP TABLE IF EXISTS discussions;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL
);


CREATE TABLE discussions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    context TEXT
);

CREATE TABLE discussion_comments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    discussion_id INT,
    comment_text TEXT,
    comment_date TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (discussion_id) REFERENCES discussions(id)
);

INSERT INTO users (username, password, role) VALUES 
('admin', 'admin', 'admin'),
('test_user', 'password', 'user'),
('comment_user', 'password', 'user'),
('fake_user', '1234', 'user');

INSERT INTO discussions (title, context) VALUES 
('Fake Title 1', 'Fake Context 1'), 
('Fake Title 2', 'Fake Context 2'), 
('Fake Title 3', NULL);

INSERT INTO discussion_comments (user_id, discussion_id, comment_text, comment_date) VALUES
(2, 1, 'This is a great discussion!', '2023-11-20 10:15:00'),
(3, 1, 'I agree with the points mentioned.', '2023-11-20 11:30:00'),
(4, 1, 'Interesting topic. Looking forward to more discussions.', '2023-11-20 12:45:00'),
(2, 1, 'I have a different perspective on this issue.', '2023-11-21 09:00:00'),
(3, 1, 'What are the potential solutions to the problem?', '2023-11-21 10:30:00');
