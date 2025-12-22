-- Create users table
CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email VARCHAR(255),
    name VARCHAR(255),
    password VARCHAR(255)
);

-- Create notification table
CREATE TABLE notification (
    id BIGSERIAL PRIMARY KEY,
    notification_id VARCHAR(255),
    message TEXT
);

