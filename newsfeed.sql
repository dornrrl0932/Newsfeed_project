-- Table: user
CREATE TABLE user
(
    user_id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    email        VARCHAR(50) UNIQUE NOT NULL,
    password     VARCHAR(255)       NOT NULL,
    user_name    VARCHAR(10),
    introduction VARCHAR(20),
    status       BOOLEAN DEFAULT TRUE
);

-- Table: post
CREATE TABLE post
(
    post_id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    title         VARCHAR(20),
    contents      LONGTEXT,
    created_at    DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at    DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    like_count    BIGINT   DEFAULT 0,
    comment_count BIGINT   DEFAULT 0,
    user_id       BIGINT,
    FOREIGN KEY (user_id) REFERENCES user (user_id) ON DELETE CASCADE
);

-- Table: comment
CREATE TABLE comment
(
    comment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    post_id    BIGINT,
    user_id    BIGINT,
    comments   VARCHAR(250),
    like_count BIGINT   DEFAULT 0,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (post_id) REFERENCES post (post_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES user (user_id) ON DELETE CASCADE
);

-- Table: post_like
CREATE TABLE post_like
(
    post_like_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    post_id      BIGINT,
    user_id      BIGINT,
    like_status  BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (post_id) REFERENCES post (post_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES user (user_id) ON DELETE CASCADE
);

-- Table: comment_like
CREATE TABLE comment_like
(
    comment_like_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    comment_id      BIGINT,
    user_id         BIGINT,
    like_status     BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (comment_id) REFERENCES comment (comment_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES user (user_id) ON DELETE CASCADE
);

-- Table: follow
CREATE TABLE follow
(
    follow_id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    following_id BIGINT,
    follower_id  BIGINT,
    is_approve   BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (following_id) REFERENCES user (user_id) ON DELETE CASCADE,
    FOREIGN KEY (follower_id) REFERENCES user (user_id) ON DELETE CASCADE
);
