-- 사용자(User) 데이터 삽입
INSERT INTO users (id, name, email, avatar_url, created_at) VALUES
(1, '김지원', 'jiwon.kim@example.com', 'https://example.com/avatars/jiwon.jpg', '2025-01-10 09:15:00'),
(2, '이민준', 'minjun.lee@example.com', 'https://example.com/avatars/minjun.jpg', '2025-01-12 14:30:00'),
(3, '박서연', 'seoyeon.park@example.com', 'https://example.com/avatars/seoyeon.jpg', '2025-01-15 11:45:00'),
(4, '정도윤', 'doyoon.jung@example.com', 'https://example.com/avatars/doyoon.jpg', '2025-01-20 16:20:00'),
(5, '최하은', 'haeun.choi@example.com', 'https://example.com/avatars/haeun.jpg', '2025-01-25 10:05:00'),
(6, '강준호', 'junho.kang@example.com', 'https://example.com/avatars/junho.jpg', '2025-02-01 15:40:00'),
(7, '윤소율', 'soyul.yoon@example.com', 'https://example.com/avatars/soyul.jpg', '2025-02-05 12:50:00'),
(8, '황민서', 'minseo.hwang@example.com', 'https://example.com/avatars/minseo.jpg', '2025-02-10 09:30:00'),
(9, '송지훈', 'jihoon.song@example.com', 'https://example.com/avatars/jihoon.jpg', '2025-02-15 14:20:00'),
(10, '임세린', 'serin.lim@example.com', 'https://example.com/avatars/serin.jpg', '2025-02-20 11:10:00');

-- 시퀀스 재설정
SELECT setval('users_id_seq', (SELECT MAX(id) FROM users));