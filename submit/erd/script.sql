CREATE TABLE `users` (
                         `user_id` varchar(50) NOT NULL COMMENT '아이디',
                         `user_name` varchar(50) NOT NULL COMMENT '이름',
                         `user_password` varchar(200) NOT NULL COMMENT 'mysql password 사용',
                         `user_birth` varchar(8) NOT NULL COMMENT '생년월일 : 19840503',
                         `user_auth` varchar(10) NOT NULL COMMENT '권한: ROLE_ADMIN,ROLE_USER',
                         `user_point` int NOT NULL COMMENT 'default : 1000000',
                         `created_at` datetime NOT NULL COMMENT '가입 일자',
                         `latest_login_at` datetime DEFAULT NULL COMMENT '마지막 로그인 일자',
                         PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='회원';

CREATE TABLE addresses (
                           address_id INT PRIMARY KEY AUTO_INCREMENT,
                           user_id varchar(50),
                           address VARCHAR(255) NOT NULL,
                           FOREIGN KEY (user_id) REFERENCES users(user_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE Category (
                          category_id INT AUTO_INCREMENT PRIMARY KEY,
                          category_name VARCHAR(255) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE Product (
                         p_id INT AUTO_INCREMENT PRIMARY KEY,
                         p_name VARCHAR(255) NOT NULL,
                         p_price INT NOT NULL,
                         thumbnail_image VARCHAR(255),
                         detail_image VARCHAR(255)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE Product_Category (
                                  p_id INT NOT NULL,
                                  category_id INT NOT NULL,
                                  PRIMARY KEY (p_id, category_id),
                                  FOREIGN KEY (p_id) REFERENCES Product(p_id) ON DELETE CASCADE ON UPDATE CASCADE ,
                                  FOREIGN KEY (category_id) REFERENCES Category(category_id) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE Orders (
                        order_id INT AUTO_INCREMENT PRIMARY KEY,
                        user_id varchar(50),
                        p_id INT,
                        quantity INT NOT NULL,
                        order_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
                        FOREIGN KEY (p_id) REFERENCES Product(p_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE Inventory (
                           inventory_id INT AUTO_INCREMENT PRIMARY KEY,
                           p_id INT unique,
                           inventory_quantity INT NOT NULL,
                           FOREIGN KEY (p_id) REFERENCES Product(p_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE point_history (
                               id INT AUTO_INCREMENT PRIMARY KEY,
                               user_id varchar(50) NOT NULL,
                               points_used INT NOT NULL,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               FOREIGN KEY (user_id) REFERENCES users(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
