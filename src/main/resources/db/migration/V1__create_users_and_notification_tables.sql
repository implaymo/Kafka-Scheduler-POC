CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email VARCHAR(255) UNIQUE,
    name VARCHAR(255),
    password VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS notification (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    message VARCHAR(255),
    user_id UUID NOT NULL,
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(id)
    );

CREATE TABLE IF NOT EXISTS user_roles (
    user_id UUID REFERENCES users(id),
    role VARCHAR(255),
    PRIMARY KEY (user_id, role)
    );

CREATE TABLE IF NOT EXISTS user_areas (
    user_id UUID REFERENCES users(id),
    area VARCHAR(255),
    PRIMARY KEY (user_id, area)
    );
